package common.helper;

import common.definitions.ReturnCode;

public class nbReturn {
	
	public void setError(Long errorCode){
		setRetCode(errorCode);
		setRetString(ReturnCode.ReturnString[errorCode.intValue()]);
	}
	public void setError(Long errorCode, Object retData){
		setError(errorCode);
		setObject(retData);
	}
	
	public boolean isSuccess(){
		if( retCode == ReturnCode._SUCCESS ) 
			return true;
		else
			return false;
	}
	
	
	private String 	retString;
	private Long 	retCode;
	private Object  object;
	
	public nbReturn(){
		retString = ReturnCode.ReturnString[ReturnCode._SUCCESS.intValue()];
		retCode = ReturnCode._SUCCESS;
		object = null;
	}
	
	public String getRetString() {
		return retString;
	}
	
	private void setRetString(String retString) {
		this.retString = retString;
	}
	
	public Long getRetCode() {
		return retCode;
	}
	
	private void setRetCode(Long retCode) {
		this.retCode = retCode;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
