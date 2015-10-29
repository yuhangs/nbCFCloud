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
import database.dao.ApplicationsDao;

@Service("applicationsService")
public class ApplicationsServiceImpl implements ApplicationsService {
	
	@Autowired
	ApplicationsDao applicationsDao;

	/**
     * 检查签名是否正确。签名只针对以下内容进行签名，不对业务内容进行签名
     * @param appID 合伙人的APPID
     * @param timeStamp 时间戳
     * @param uuid 客户UUID
     * @param Signature 以上内容的含APPSecretKey的签名
     * @return nbReturn 是否成功，以及错误信息
     */
	@Override
	public nbReturn checkSignature(String appID, String timeStamp, String uuid,
			String Signature) throws Exception {
		nbReturn nbRet = new nbReturn();
		
		//检查需要验证的签名是否5秒之内的新鲜有效的签名
		Date parameterDate = nbStringUtil.String2DateTime(timeStamp);
		Calendar cal = Calendar.getInstance();
		if( ( cal.getTime().getTime() - parameterDate.getTime() ) > 5000  ){
			// 如果传进来的timestamp是5秒之前的话，系统将视此次signature为无效的
			nbRet.setError(ReturnCode.SIGNATURE_WRONG);
			return nbRet;
		}
		
		nbRet = applicationsDao.generateSignature(appID, timeStamp, uuid);
		
		if( !nbRet.isSuccess() )
			return nbRet;
		
		String correctSignature = (String)nbRet.getObject();
		
		if( correctSignature.equals(Signature) ){
			nbRet.setObject(Boolean.valueOf(true));
		}else{
			nbRet.setError(ReturnCode.SIGNATURE_WRONG);
			nbRet.setObject(Boolean.valueOf(false));
		}
		
		return nbRet;
	}
	
	/**
     * 检查签名是否正确。签名只针对以下内容进行签名，不对业务内容进行签名
     * appID 合伙人的APPID
     * timeStamp 时间戳
     * uuid 客户UUID
     * Signature 以上内容的含APPSecretKey的签名
     * @param jsonMap json解析出来的Map格式内容
     * @return nbReturn 是否成功，以及错误信息
     */
	@Override
	public nbReturn checkSignature(Map<String, Object> jsonMap) throws Exception {
		nbReturn nbRet = new nbReturn();
		if( jsonMap == null ){
			nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
			return nbRet;
		}
		
		String appID = (String) jsonMap.get(ParameterDefine.APPID);
		String applyDateTime = (String) jsonMap.get(ParameterDefine.TIMESTAMP);
		String clientUuid = (String) jsonMap.get(ParameterDefine.CLIENTUUID);
		String appSignature = (String) jsonMap.get(ParameterDefine.SIGNATURE);
		
		return checkSignature(appID, applyDateTime, clientUuid, appSignature);
	}

	/**
     * 生成图片验证码流
     * @param reasonCode 申请图片验证码的理由代码，可以为空
     * @param reasonComments 申请图片验证码的理由说明，可以为空
     * @return retCode
     * @return retMessage
     * @return retContent{
     * @return PictureInBase64:图片流
     * @return PictureCodeAffairId:
     * @return PictureCodeLifecycle:
     * @return PictureCodeExpireTime:
     * @return }
     */
	@Override
	public nbReturn generatePictureCode(String reasonCode, String reasonComments) {
		
		//TODO: 生成图片验证码的流，以及图片验证码事件ID，以及图片验证码的有效时间和过期时间
//		retCode
//		retMessage
//		retContent{
//		  PictureInBase64:图片流
//		  PictureCodeAffairId:
//		  PictureCodeLifecycle:
//		  PictureCodeExpireTime:
//		}
		
		return null;
	}

	/**
     * 检验图片验证码是否正确
     * @param jsonMap 从json解析出来的map，从里面获取三个元素进行验证，时间（检验是否过期），图片验证码发行的id，以及客户填写的图片验证码
     * @return retCode
     * @return retMessage
     * @return retContent{
     * @return PictureInBase64:图片流
     * @return PictureCodeAffairId:
     * @return PictureCodeLifecycle:
     * @return PictureCodeExpireTime:
     * @return }
     */
	@SuppressWarnings("unused")
	@Override
	public nbReturn checkPictureCode(Map<String, Object> jsonMap, boolean parameterNullAsOK) {

		nbReturn nbRet = new nbReturn();
		String pictureCode = (String) jsonMap.get(ParameterDefine.PICTURECODE);
		String pictureCodeAffairId = (String) jsonMap.get(ParameterDefine.PICUTRECODEAFFAIRID);
		String timeStamp = (String)jsonMap.get(ParameterDefine.TIMESTAMP);
		
		if( (pictureCode==null || pictureCode.length() == 0 ) ||
			(pictureCodeAffairId == null || pictureCodeAffairId.length() == 0) ){
			if( parameterNullAsOK){
				nbRet.setError(ReturnCode._SUCCESS);
				return nbRet;
			}else{
				nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
				return nbRet;
			}
		}
		
		// TODO:这里要验证传进来的图片验证码code是否正确，以及在时间上是否还有效
		
		return null;
	}

	/**
     * 发行一个手机验证码
     * @param jsonMap 从json解析出来的map
     * @return retCode
     * @return retMessage
     * @return retContent{
     * @return   PhoneCheckAffairId:
     * @return   PhoneCheckCodeInterval:
     * @return   PhoneCheckCodeLifecycle:
     * @return   PhoneCheckCodeExpireTime:
     * @return }
     */
	@Override
	public nbReturn sendPhoneCode(Map<String, Object> jsonMap) {
		
		nbReturn nbRet = new nbReturn();
		if( jsonMap == null ){
			nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
			return nbRet;
		}
		
		String appId = (String) jsonMap.get(ParameterDefine.APPID);
		String phoneNumber = (String) jsonMap.get(ParameterDefine.PHONENUMBER);
		String reasonCode = (String) jsonMap.get(ParameterDefine.SENDREASONCODE);
		String reasonComments = (String) jsonMap.get(ParameterDefine.SENDREASONCOMMENTS);
		
		return sendPhoneCode(appId, phoneNumber, reasonCode, reasonComments);
	}

	/**
     * 发行一个手机验证码
     * @param PhoneNumber 对象电话号码
     * @param SendReasonCode 发送理由代码
     * @param SendReasonCommnets 发送原因说明
     * @return retCode
     * @return retMessage
     * @return retContent{
     * @return   PhoneCheckAffairId:
     * @return   PhoneCheckCodeInterval:
     * @return   PhoneCheckCodeLifecycle:
     * @return   PhoneCheckCodeExpireTime:
     * @return }
     */
	@Override
	public nbReturn sendPhoneCode(String appId, String PhoneNumber, String SendReasonCode,
			String SendReasonCommnets) {
		
		ApplicationServiceUtils applicationServiceUtils = new ApplicationServiceUtils();
		nbReturn nbRet = applicationServiceUtils.checkPhoneCodeSendAndResend(appId, PhoneNumber, SendReasonCode, SendReasonCommnets);
		if( nbRet == null )
			nbRet = applicationServiceUtils.createNewRecordAndSendPhoneCheckCode(appId, PhoneNumber, SendReasonCode, SendReasonCommnets, null);
		
		return nbRet;
		
		
	}
	
	/**
     * 发行一个手机验证码
     * @param phoneCheckAffairid 验证码的记录id
     * @param phoneCheckCode 验证码
     * @return retCode
     * @return retMessage
     * @return retContent{
     * @return }
     */
	@Override
	public nbReturn checkPhoneCode(String phoneCheckAffairid, String phoneCheckCode) {
		
		ApplicationServiceUtils applicationServiceUtils = new ApplicationServiceUtils();
		
		return applicationServiceUtils.checkPhoneCode(phoneCheckAffairid, phoneCheckCode);
	}

	@Override
	public nbReturn checkPhoneCode(Map<String, Object> jsonMap, boolean parameterNullAsOK) {
		
		nbReturn nbRet = new nbReturn();
		if( jsonMap == null ){
			nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
			return nbRet;
		}
		
		String phoneCheckAffairid = (String) jsonMap.get(ParameterDefine.PHONECHECKAFFAIRID);
		String phoneCheckCode = (String) jsonMap.get(ParameterDefine.PHONECHECKCODE);
		
		if( (phoneCheckAffairid==null || phoneCheckAffairid.length() == 0 ) ||
			(phoneCheckCode == null || phoneCheckCode.length() == 0) ){
			
			if( parameterNullAsOK){
				nbRet.setError(ReturnCode._SUCCESS);
				return nbRet;
			}else{
				nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
				return nbRet;
			}
			
		}
	
		return checkPhoneCode(phoneCheckAffairid, phoneCheckCode);
	}

	/**
     * 检查签名是否正确。签名只针对以下内容进行签名，不对业务内容进行签名
     * @param jsonMap json解析出来的Map格式内容
     * @param parameterNullAsOK 空的话是否OK
     * @return nbReturn 是否成功，以及错误信息
	 * @throws Exception 
     */
	@Override
	public nbReturn checkSignature(Map<String, Object> jsonMap,boolean parameterNullAsOK) throws Exception {
		nbReturn nbRet = new nbReturn();
		if( jsonMap == null ){
			if( !parameterNullAsOK ){
				nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
			}
			return nbRet;
		}
		
		String appID = (String) jsonMap.get(ParameterDefine.APPID);
		String applyDateTime = (String) jsonMap.get(ParameterDefine.TIMESTAMP);
		String clientUuid = (String) jsonMap.get(ParameterDefine.CLIENTUUID);
		String appSignature = (String) jsonMap.get(ParameterDefine.SIGNATURE);
		
		//只有一个必要参数是空的，并且【参数为空也OK】的话就直接返回success
		if( appID == null || applyDateTime == null || clientUuid ==null || appSignature == null){
			if( parameterNullAsOK )
				return nbRet;
			else{
				nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
				return nbRet;
			}
		}
			
		return checkSignature(appID, applyDateTime, clientUuid, appSignature);
	}

	

}
