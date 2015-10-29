package main.entry.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.major.ApplicationsService;
import service.major.ProjectService;
import service.major.UserInfoService;
import common.definitions.ParameterDefine;
import common.helper.HttpWebIOHelper;
import common.helper.nbReturn;


@Controller
public class OpenAPIs {

	
	@Autowired  
	private UserInfoService userInfoService;
	@Autowired
	private ApplicationsService applicationsService;
	@Autowired
	private ProjectService projectService;

	
	 /**
     * 注册新用户
     * @param APPID 合伙人的APPID
     * @param ClientUUID 客户UUID
     * @param TimeStamp 时间戳
     * @param Signature 以上内容的含APPSecretKey的签名
     * @param Username 用户名
     * @param Password 密码
     * @param PasswordConfirm 重复密码
     * @param email 邮件
     * @param mobile 电话,一般用户名就是电话，这个不需要
     * @param PhoneCheckCode 不可为空
     * @param PhoneCheckAffairId 不可为空
     * @param PictureCode 一般都用短信验证码来防止机器人攻击，可以为空
     * @param PictureCodeAffairId 一般都用短信验证码来防止机器人攻击，可以为空
     */
	@RequestMapping(value = "/openAPI/user/register") 
    public void user_register(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = applicationsService.checkSignature(jsonMap);
		
		if( !nbRet.isSuccess() ){//验证signature 失败了
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		//检查手机验证码
		nbRet = applicationsService.checkPhoneCode(jsonMap, false);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		//检查图片验证码如果参数为空，也算验证通过
		nbRet = applicationsService.checkPictureCode(jsonMap, true);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = userInfoService.RegisterUser(jsonMap);
		HttpWebIOHelper.printReturnJson(nbRet, response);
    }
	
	
	/**
     * 用户登录
     * @param APPID 合伙人的APPID
     * @param ClientUUID 客户UUID
     * @param TimeStamp 时间戳
     * @param Signature 以上内容的含APPSecretKey的签名
     * @param Username 用户名
     * @param Password 密码
     * @param PhoneCheckCode 一般不可为空
     * @param PhoneCheckAffairId 一般不可为空
     * @param PictureCode 一般都用短信验证码来防止机器人攻击，可以为空
     * @param PictureCodeAffairId 一般都用短信验证码来防止机器人攻击，可以为空
     */
    @RequestMapping(value = "/openAPI/user/login") 
    public void user_login(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    	Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = applicationsService.checkSignature(jsonMap);
		if( !nbRet.isSuccess() ){//验证signature 失败了
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		//检查手机验证码
		nbRet = applicationsService.checkPhoneCode(jsonMap, false);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		//验证图片验证码
		nbRet = applicationsService.checkPictureCode(jsonMap, false);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = userInfoService.verifyUser(jsonMap);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
    }
    
    /**
     * 发行一个手机验证码
     * @param APPID 合伙人的APPID
     * @param ClientUUID 客户UUID
     * @param TimeStamp 时间戳
     * @param Signature 以上内容的含APPSecretKey的签名
     * @param Username 用户名
     * @param Password 密码
     * @param PasswordConfirm 重复密码
     * @param PhoneNumber
     * @param PhoneCheckCode
     * @param PhoneCheckAffairId
     * @param PictureCode 为防止机器人可以使用图片验证码，也可以为空
     * @param PictureCodeAffairId 为防止机器人可以使用图片验证码，也可以为空
     */
    @RequestMapping(value = "/openAPI/user/resetPasswordViaPhone") 
    public void user_resetPasswordViaPhone(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    	Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = applicationsService.checkSignature(jsonMap);
		if( !nbRet.isSuccess() ){//验证signature 失败了
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		//检查手机验证码
		nbRet = applicationsService.checkPhoneCode(jsonMap, false);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		//验证图片验证码
		nbRet = applicationsService.checkPictureCode(jsonMap, true);
		if( !nbRet.isSuccess() ){
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = userInfoService.resetPassword(jsonMap);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
    }
    
    /**
     * 发行一个手机验证码
     * @param APPID 合伙人的APPID
     * @param ClientUUID 客户UUID
     * @param TimeStamp 时间戳
     * @param Signature 以上内容的含APPSecretKey的签名
     * @param PhoneNumber
     * @param SendReasonCode
     * @param SendReasonCommnets
     * @param PictureCode 为防止机器人可以使用图片验证码，也可以为空
     * @param PictureCodeAffairId 为防止机器人可以使用图片验证码，也可以为空
     * @return retCode<br/>
     * retMessage<br/>
     * retContent{<br/>
     * &nbsp&nbsp&nbsp PhoneCheckAffairId:<br/>
     * &nbsp&nbsp&nbsp PhoneCheckCodeLifecycle:<br/>
     * &nbsp&nbsp&nbsp PhoneCheckCodeExpireTime:<br/>
     * }
     */
    @RequestMapping(value = "/openAPI/utils/sendUserPhoneCheckCode") 
    public void utils_sendUserPhoneCheckCode(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = applicationsService.checkSignature(jsonMap);
		if( !nbRet.isSuccess() ){//验证signature 失败了
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = applicationsService.sendPhoneCode(jsonMap);
		HttpWebIOHelper.printReturnJson(nbRet, response);
    }

    /**
     * 获取验证码图片
     * @param APPID 合伙人的APPID
     * @param ClientUUID 客户UUID
     * @param TimeStamp 时间戳
     * @param Signature 以上内容的含APPSecretKey的签名
     * @param GetPictureCodeReasonCode	获取图片的理由
     * @param GetPictureCodeReasonCommnets 获取图片的理由说明
     */
    @RequestMapping(value = "/openAPI/utils/getPictureCode") 
    public void utils_getPictureCode(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    	Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		//这里需要通过applyDateTime, appSignature 以及 appID 验证APPID的有效性
		nbRet = applicationsService.checkSignature(jsonMap);
		if( !nbRet.isSuccess() ){//验证signature 失败了
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		String reasonCode = (String) jsonMap.get(ParameterDefine.GETPICTURECODEREASONCODE);
		String reasonComments = (String) jsonMap.get(ParameterDefine.GETPICTURECODEREASONCOMMENTS);
		nbRet = applicationsService.generatePictureCode(reasonCode, reasonComments);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
    	
    }
    
    /**
     * 检查手机验证码
     * @param APPID 合伙人的APPID
     * @param ClientUUID 客户UUID
     * @param TimeStamp 时间戳
     * @param Signature 以上内容的含APPSecretKey的签名
     * @param PhoneCheckCode 
     * @param PhoneCheckAffairId 
     */
    @RequestMapping(value = "/openAPI/utils/checkPhoneCode") 
    public void utils_checkPhoneCode(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    	Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		//这里需要通过applyDateTime, appSignature 以及 appID 验证APPID的有效性
		nbRet = applicationsService.checkSignature(jsonMap);
		if( !nbRet.isSuccess() ){//验证signature 失败了
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		nbRet =  applicationsService.checkPhoneCode(jsonMap, false);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
    	
    }
    
    /**
     * 检查手机验证码
     * @param APPID 合伙人的APPID
     * @param ClientUUID 客户UUID
     * @param TimeStamp 时间戳
     * @param Signature 以上内容的含APPSecretKey的签名
     * @param PictureCode 
     * @param PictureCodeAffairId 
     */
    @RequestMapping(value = "/openAPI/utils/checkPictureCode") 
    public void utils_checkPictureCode(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    	Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
		nbReturn nbRet = new nbReturn();
		//这里需要通过applyDateTime, appSignature 以及 appID 验证APPID的有效性
		nbRet = applicationsService.checkSignature(jsonMap);
		if( !nbRet.isSuccess() ){//验证signature 失败了
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet =  applicationsService.checkPictureCode(jsonMap, false);
		
		HttpWebIOHelper.printReturnJson(nbRet, response);
    	
    }
    
    
    /**
     * 获取产品列表
     * @param APPID 合伙人的APPID
     * @param ClientUUID 客户UUID
     * @param TimeStamp 时间戳
     * @param Signature 以上内容的含APPSecretKey的签名<br/>//以上内容可以为空<br/>
     * @param SearchCondition {
     * @param   startTime :
     * @param   endTime :
     * @param   itemsPerPage :
     * @param   startPage :
     * @param   endPage :
     * @param   keyWords :
     * @param   statusCodeWhiteFilter[] {
     * @param      1;
     * @param      3;
     * @param   }
     * @param   statusCodeBlackFilter[] {
     * @param      2;
     * @param      4;
     * @param   }
     * @param } 
     */
    @RequestMapping(value = "/openAPI/product/getProjectList") 
    public void product_getProjectList(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    	Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
    	
		nbReturn nbRet = new nbReturn();
		nbRet = applicationsService.checkSignature(jsonMap, false);
		if( !nbRet.isSuccess() ){//验证signature 失败了
			HttpWebIOHelper.printReturnJson(nbRet, response);
			return;
		}
		
		nbRet = projectService.searchAndFetchProjectList(jsonMap);
		
    	
    }
    @RequestMapping(value = "/openAPI/product/getProjectPresentMaterials") 
    public void product_getProjectPresentMaterials(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    }
    @RequestMapping(value = "/openAPI/product/getProjectInvestRules") 
    public void product_getProjectInvestRules(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    }
    
    @RequestMapping(value = "/openAPI/product/getProjectStatus") 
    public void product_getProjectStatus(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    }
    @RequestMapping(value = "/openAPI/product/getProjectInvestHistory") 
    public void product_getProjectInvestHistory(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    }
    @RequestMapping(value = "/openAPI/product/getMaterialsForShare") 
    public void product_getMaterialsForShare(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    }
    @RequestMapping(value = "/openAPI/product/getProjectComments") 
    public void product_getProjectComments(HttpServletResponse response,HttpServletRequest request) throws Exception{
    	
    }
    
}
