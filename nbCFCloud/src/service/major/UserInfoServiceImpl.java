package service.major;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.definitions.ParameterDefine;
import common.definitions.ReturnCode;
import common.helper.nbReturn;
import common.helper.nbStringUtil;
import service.common.ScheduledService;
import database.dao.TokenPublishDao;
import database.dao.UserExternalCallDao;
import database.dao.UserInfoDao;
import database.models.NbTokenPublisher;
import database.models.NbUser;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private ScheduledService scheduledService;
	@Autowired
	private TokenPublishDao tokenPublishDao;
	@Autowired
	private UserExternalCallDao userExternalCallDao;
	
	/**
	 * 用于验证用户名及密码
	 * 
	 * @param jsonMap 经过解析的json参数
	 * @return 返回nbReturn类型的返回值。object为NbTokenPublish（如果needToken为true）
	 * 
	 */
	public nbReturn verifyUser(Map<String, Object> jsonMap) throws Exception {
		nbReturn nbRet = new nbReturn();
		if( jsonMap == null ){
			nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
			return nbRet;
		}
		
		String username = (String) jsonMap.get(ParameterDefine.USERNAME);
		String password = (String) jsonMap.get(ParameterDefine.PASSWORD);
		String appID = (String) jsonMap.get(ParameterDefine.APPID);
		Long lifecycleSec = (Long) jsonMap.get(ParameterDefine.TOKENLIFECYCLE);
		String clientUuid = (String) jsonMap.get(ParameterDefine.CLIENTUUID);
		
		return this.verifyUser(username, password, appID, clientUuid, lifecycleSec, true);
	}

	/**
	 * 用于验证用户名及密码
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param appID    商家ID
	 * @param clientUuid 终端的UUID，如果是web调用可填写IP地址，可为空，但是请谨慎使用
	 * @param lifecycleSec token的生命周期，不可小于60秒，不可大于14400秒
	 * @param needToken 是否需要生成token，如果是web页面生成可以不用生成token
	 * @return 返回nbReturn类型的返回值。object为NbTokenPublish（如果needToken为true）
	 * 
	 */
	@Override
	public nbReturn verifyUser(String username, 
			                   String password, 
			                   String appID, 
			                   String clientUuid, 
			                   Long lifecycleSec, 
			                   Boolean needToken) throws Exception {
		
		nbReturn nbRet = new nbReturn();
		if( lifecycleSec == null ) lifecycleSec = 7200l;
		if( lifecycleSec < 60l) lifecycleSec = 60l;
		if( lifecycleSec > 14400l ) lifecycleSec = 14400l;
		if( appID == null ) {
			nbRet.setError(ReturnCode.MISSING_APPID);
			return nbRet;
		}
		if( clientUuid == null ) clientUuid = "none";
		
		
		NbUser nbUser = userInfoDao.verifyUser(username, nbStringUtil.encryptMD5(password), appID);
		NbTokenPublisher nbTokenPublish;
		
		if( nbUser == null ){ //用户认证失败
			nbRet.setError(ReturnCode.USERNAME_PASSWORD_ERROR);
		}else{ //用户认证成功
			
			nbRet.setObject(nbUser);
			
			if( needToken ){//需要返回token
				
				nbReturn nbTmpRet = tokenPublishDao.createNewToken(appID, nbUser, clientUuid, lifecycleSec);
				
				if( nbTmpRet.isSuccess() ){//创建成功
					
					nbTokenPublish = (NbTokenPublisher)nbTmpRet.getObject();
					nbRet.setObject(nbTokenPublish);
					
				}
				else{//创建失败了
					nbRet.setError(ReturnCode.CREATE_TOKEN_ERROR);
				}
			}

		}
		
		return nbRet;
	}

	/**
	 * 检查token是否有效
	 * 
	 * @param tokenString token的字符串
	 * @param appID 商家ID
	 * @param ifRefresh 是否要刷新生命周期
	 * @return 返回成功失败信息，以及成功时候的token信息
	 * 
	 */
	@Override
	public nbReturn checkToken(String tokenString, String appID, Boolean ifRefresh) {
		
		nbReturn nbRet = new nbReturn();
		
		NbTokenPublisher nbTokenPublish =  tokenPublishDao.checkToken(tokenString, appID);
		
		//错误处理开始
		if( nbTokenPublish == null ){//没有这个token
			nbRet.setError(ReturnCode.TOKEN_NOT_EXIST);
			
		}else{
			
			Date toBeExpired = nbTokenPublish.getTokenFreshed();
			Date currentDate = Calendar.getInstance().getTime();
			Long milSec = currentDate.getTime() - toBeExpired.getTime();
			
			if( milSec > (nbTokenPublish.getTokenLifecycleSec()*1000) ){ // expired
				nbRet.setError(ReturnCode.TOKEN_EXPIRED);
				
			}else{ // 有这个token，也没有过期
				
				if( ifRefresh ){ //需要刷新一下
					
					nbTokenPublish.setTokenFreshed(currentDate);
					nbTokenPublish = tokenPublishDao.update(nbTokenPublish);
				}
				
			}
			
			nbRet.setObject(nbTokenPublish);
		}

		return nbRet;
	}

	/**
	 * 用于注册新用户
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param mobile 电话号码 可为空
	 * @param email 邮件地址 可为空
	 * @param appID 商家ID
	 * @return 返回成功失败信息，以及成功时候的用户信息，并没有返回token
	 * 
	 */
	@Override
	public nbReturn RegisterUser(String username, String password,
			String mobile, String email, String appID) throws Exception {
		
		NbUser checkUser = userInfoDao.findByUsernameAndAppid(username, appID);
		if( checkUser != null ){ //用户名已经存在
			nbReturn nbRet = new nbReturn();
			nbRet.setError(ReturnCode.USERNAME_ALREADY_EXIST);
			return nbRet;
		}
		//用户名可以用，开始注册用户
		NbUser nbUser = new NbUser();
		nbUser.setEmail(email);
		nbUser.setMobilePhone(mobile);
		nbUser.setPassword(nbStringUtil.encryptMD5(password));
		nbUser.setUsername(username);
		nbUser.setApplicationId(appID);
		Calendar cal = Calendar.getInstance();
		nbUser.setUserOpenCode(nbStringUtil.encryptMD5(	appID
														+username
														+String.valueOf(cal.getTimeInMillis())
														));
		nbUser = userInfoDao.save(nbUser);
		nbReturn nbRet = new nbReturn();
		nbRet.setObject(nbUser);
		return nbRet;
	}
	

	/**
	 * 用于注册新用户
	 * 
	 * @param jsonMap 经过解析的json格式的参数
	 * @return 返回成功失败信息，以及成功时候的用户信息，并没有返回token
	 * 
	 */
	@Override
	public nbReturn RegisterUser(Map<String, Object> jsonMap) throws Exception {
		nbReturn nbRet = new nbReturn();
		
		if( jsonMap == null ){
			nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
			return nbRet;
		}
		
		String username = (String) jsonMap.get(ParameterDefine.USERNAME);
		String password = (String) jsonMap.get(ParameterDefine.PASSWORD);
		String passwordConfirm = (String) jsonMap.get(ParameterDefine.PASSWORDCONFIRM);
		String email = (String) jsonMap.get(ParameterDefine.EMAIL);
		String mobile = (String) jsonMap.get(ParameterDefine.MOBILE);
		String appID = (String) jsonMap.get(ParameterDefine.APPID);
		
		
		if( !password.equals(passwordConfirm) ){
			nbRet.setError(ReturnCode.CONFIRMPASSWORD_NOT_SAME_WITH_PASSWORD);
			return nbRet;
		}
		return RegisterUser(username, password, mobile, email, appID);
	}

	@Override
	public nbReturn exUrlGetToken(String providerCode) {
		// TODO:SSO:要到外部用户信息中心获取TOKEN以便得到外部用户信息中心的数据
//		nbReturn nbRet = userExternalCallDao.findByProviderCode(providerCode);
//		if( nbRet.isSuccess() ){
//			NbUserExternalCall nbUserExternalCall = (NbUserExternalCall)nbRet.getObject();
//			
//			ExecuteLongCall
//			
//			nbRet = userInfoDao.findByUserExternalCall(nbUserExternalCall);
//		}
		return null;
	}

	@Override
	public nbReturn exUrlVerifyUser(String username, String password,
			String providerCode) {
		// TODO：SSO:要到外部用户信息中心获取
		return null;
	}

	@Override
	public nbReturn exUrlGetUserInfo(String token, String providerCode) {
		// TODO:SSO:要到尾部用户信息中心获取用户信息
		return null;
	}

	/**
	 * 用于更新用户密码
	 * 
	 * @param jsonMap 经过解析的json格式的参数
	 * @return 返回成功失败信息
	 * 
	 */
	@Override
	public nbReturn resetPassword(Map<String, Object> jsonMap) throws Exception{
		nbReturn nbRet = new nbReturn();
		String username = (String) jsonMap.get(ParameterDefine.USERNAME);
		String password = (String) jsonMap.get(ParameterDefine.PASSWORD);
		String passwordConfirm = (String) jsonMap.get(ParameterDefine.PASSWORDCONFIRM);
		String appID = (String) jsonMap.get(ParameterDefine.APPID);
		
		if( !password.equals(passwordConfirm) ){
			nbRet.setError(ReturnCode.CONFIRMPASSWORD_NOT_SAME_WITH_PASSWORD);
			return nbRet;
		}
		return resetPassword(appID, username, password);
	}

	/**
	 * 用于更新用户密码
	 * 
	 * @param appID 
	 * @param username 
	 * @param password 
	 * @return 返回成功失败信息
	 * 
	 */
	@Override
	public nbReturn resetPassword(String appID, String username, String password) throws Exception {
		
		nbReturn nbRet = new nbReturn();
		NbUser checkUser = userInfoDao.findByUsernameAndAppid(username, appID);
		if( checkUser == null ){ //用户不存在
			nbRet.setError(ReturnCode.NO_SUCH_USER);
			return nbRet;
		}
		checkUser.setPassword(nbStringUtil.encryptMD5(password));
		userInfoDao.update(checkUser);
		
		return nbRet;
	}

}
