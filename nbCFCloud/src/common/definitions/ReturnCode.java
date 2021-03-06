package common.definitions;

public class ReturnCode {
		
		public static final Long _SUCCESS = 								0l;
		public static final Long TOKEN_EXPIRED = 							1l;
		public static final Long TOKEN_NOT_EXIST = 							2l;
		public static final Long USERNAME_PASSWORD_ERROR = 					3l;
		public static final Long CREATE_TOKEN_ERROR = 						4l;
		public static final Long MISSING_APPID = 							5l;
		public static final Long USERNAME_ALREADY_EXIST = 					6l;
		public static final Long PARAMETER_PHARSE_ERROR = 					7l;
		public static final Long AUTHORIZE_FAILED = 						8l;
		public static final Long NEED_TOKEN_APPID_FOR_AUTH = 				9l;
		public static final Long APPLICATION_ID_NOT_FOUND = 				10l;
		public static final Long SIGNATURE_WRONG = 							11l;
		public static final Long ATTIBUTE_CODE_ALREADY_EXIST = 				12l;
		public static final Long ATTIBUTE_CODE_NOT_EXIST = 					13l;
		public static final Long INSUFFICIENT_PARAMTERS = 					14l;
		public static final Long NECESSARY_PARAMETER_IS_NULL = 				15l;
		public static final Long CONFIRMPASSWORD_NOT_SAME_WITH_PASSWORD = 	16l;
		public static final Long REQUESTED_PHONE_CODE_EXPIRED = 			17l;
		public static final Long REQUESTED_PHONE_CODE_NOT_FOUND = 			18l;
		public static final Long NO_SUCH_USER =					 			19l;
		
	
	public static String[] ReturnString = {
		"Success"													//SUCCESS = 0000l
	   ,"Token has expired!"										//TOKEN_EXPIRED = 0001l;
	   ,"Token or appID does not exist!"							//TOKEN_NOT_EXIST = 0002l;
	   ,"Username or Password Error!"								//USERNAME_PASSWORD_ERROR = 0003l;
	   ,"Create token error!"										//CREATE_TOKEN_ERROR = 0004l;
	   ,"APP ID is missing!"										//MISSING_APPID = 0005l;
	   ,"Username already exist!"									//USERNAME_ALREADY_EXIST = 0006l;
	   ,"Can not pharse paramters!"									//PARAMETER_PHARSE_ERROR = 0007l;
	   ,"Failed to Authorize!"										//AUTHORIZE_FAILED = 8l;
	   ,"Please use ‘token’ and ‘appID’ for authorizing"			//NEED_TOKEN_APPID_FOR_AUTH = 9l;
	   ,"ApplicationID not found!"									//APPLICATION_ID_NOT_FOUND = 10l;
	   ,"Signature is not correct!"									//SIGNATURE_WRONG = 11l;
	   ,"Attribute code to be added already exist!"					//ATTIBUTE_CODE_ALREADY_EXIST = 12l;
	   ,"Attribute code does not exist!"							//ATTIBUTE_CODE_NOT_EXIST = 13l;
	   ,"Paramter is not enough!"									//INSUFFICIENT_PARAMTERS = 14l;
	   ,"Necessary parameter is NULL!"								//NECESSARY_PARAMETER_IS_NULL = 15l;
	   ,"Confirm password is not the same with password!" 			//CONFIRMPASSWORD_NOT_SAME_WITH_PASSWORD = 	16l;
	   ,"Requested phone checkcode has expired!"		 			//REQUESTED_PHONE_CODE_EXPIRED = 	17l;
	   ,"Requested phone checkcode not found in database!" 			//REQUESTED_PHONE_CODE_NOT_FOUND = 	18l;
	   ,"No such user!"									 			//NO_SUCH_USER = 19l;
	};
	
}
