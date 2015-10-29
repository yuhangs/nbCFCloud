package service.major;

import java.util.Map;

import common.helper.nbReturn;


public interface UserInfoService {//extends ScheduledTaskInterface{

	public nbReturn checkToken(String tokenString, String appID, Boolean ifRefresh);
	public nbReturn verifyUser(String username, String password, String appID, String clinetUuid, Long lifecycleSec, Boolean needToken) throws Exception;
	public nbReturn verifyUser(Map<String, Object> jsonMap) throws Exception;
	public nbReturn RegisterUser(String username, String password, String mobile, String email, String AppID)  throws Exception;
	
	public nbReturn exUrlGetToken(String providerCode);
	public nbReturn exUrlVerifyUser(String username, String password, String providerCode);
	public nbReturn exUrlGetUserInfo(String token, String providerCode);
	public nbReturn RegisterUser(Map<String, Object> jsonMap) throws Exception;
	public nbReturn resetPassword(Map<String, Object> jsonMap) throws Exception;
	public nbReturn resetPassword(String appID, String username, String password) throws Exception;
}
