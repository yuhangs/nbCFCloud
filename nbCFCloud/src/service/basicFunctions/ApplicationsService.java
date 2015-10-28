package service.basicFunctions;

import java.util.Map;

import common.helper.nbReturn;

public interface ApplicationsService {

	public nbReturn checkSignature(String appID, String timeStamp, String uuid, String Signature) throws Exception;
	public nbReturn checkSignature(Map<String, Object> jsonMap) throws Exception;
	public nbReturn generatePictureCode(String reasonCode, String reasonComments);
	public nbReturn checkPictureCode(Map<String, Object> jsonMap, boolean parameterNullAsOK);
	public nbReturn sendPhoneCode(Map<String, Object> jsonMap);
	public nbReturn sendPhoneCode(String appId, String PhoneNumber,	String SendReasonCode, String SendReasonCommnets);
	public nbReturn checkPhoneCode(Map<String, Object> jsonMap, boolean parameterNullAsOK);
	public nbReturn checkPhoneCode(String phoneCheckAffairid, String phoneCheckCode);
}
