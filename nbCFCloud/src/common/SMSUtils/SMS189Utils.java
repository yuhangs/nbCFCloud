package common.SMSUtils;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import common.NetworkUtils.HttpAPICaller;
import common.definitions.ParameterDefine;
import common.helper.nbReturn;

public class SMS189Utils {

	private static final String smsApiURL = "http://api.189.cn/v2/dm/randcode/sendSms";
	private static final String appId = "228137510000247568";
	private static final String appSecret = "fd15cc0339d90fcf4d79c5272937c31b";
	private static final String tokenAppId = "https://oauth.api.189.cn/emp/oauth2/v3/access_token";
	
	HttpAPICaller httpApiCaller =  new HttpAPICaller();
	
	private Map<String, Object> prepareDataForGetToken(){
		Map<String, Object> postData = new HashMap<String, Object>();
		postData.put("grant_type", "client_credentials");
		postData.put("app_id", appId);
		postData.put("app_secret", appSecret);
		postData.put("app_secret", appSecret);
		postData.put("state", "getToken");
		postData.put("scope", "");
		return postData;
	}
	
	/**
	 * 从189短信通道服务获取服务器的Token
	 * @return
	 */
	public String getTokenFromServer(){
		Map<String, Object> data = prepareDataForGetToken();
		nbReturn nbRet = httpApiCaller.CallAPI(tokenAppId, null, data, ParameterDefine.LONG_CALL_METHOD_GET, ParameterDefine.LONG_CALL_PARA_FORMAT_XFORM);
		
		@SuppressWarnings("unchecked")
		Map<String,Object> replyContent = (Map<String,Object>)nbRet.getObject();
		if( replyContent == null )
			return null;
		
		byte[] contentBytes = (byte[]) replyContent.get(ParameterDefine.HTTP_CONTENT_PART);
		if( contentBytes == null )
			return null;
		
		Map<String, Object> retMessageMap = (Map<String, Object>)JSONObject.parseObject(String.valueOf(contentBytes));
		if( retMessageMap != null){
			if( Integer.valueOf((String) retMessageMap.get(ParameterDefine.SMS_189_RESPONSE_CODE)).intValue() == 0 ){// success
				return (String) retMessageMap.get(ParameterDefine.SMS_189_ACCESS_TOKEN);
			}else{
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 具体发送短信
	 * @param targetPhoneNumber
	 * @param text
	 * @return
	 */
	public nbReturn sendTheSMS(String targetPhoneNumber, String text){
		
		//TODO:这里要实现短信发送的具体功能
		
		return null;
	}
}
