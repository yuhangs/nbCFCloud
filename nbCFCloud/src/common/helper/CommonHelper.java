package common.helper;

import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import common.SMSUtils.SMS189Utils;


public class CommonHelper {

	/**
	 * obj里边包含jpa对象会报错，要过滤一次。
	 * 
	 * @return
	 */
	public static String getStringOfObj(Object obj) {
		//SimplePropertyFilter spf = new SimplePropertyFilter();
		//暂时不考虑持JPA对象报错的问题。以后可能需要加上
		
		SerializeWriter sw = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(sw);
		//serializer.getPropertyFilters().add(spf);
		serializer.write(obj);
		return sw.toString();
	}
	
	
	/**
	 * 把访问记录到数据中
	 * @param appID 授权的appID
	 * @param clientUUID 顾客客户端的唯一标识，可以为设备UUID，也可以是IP地址，也可以为空
	 * @param timeStamp 时间戳
	 * @param signature 以上内容的MD5签名
	 * @return 记录进数据库是否成功，如果签名不对或其他错误会返回相应的错误信息
	 */
	public static nbReturn setAccessLog(String appID, String clientUUID, Date timeStamp, String signature){
		nbReturn nbRet = new nbReturn();
		//TODO:把访问记录记录到数据库中
		return nbRet;
	}
	
	/**
	 * 把访问记录到数据中
	 * @param appID 授权的appID
	 * @param token 已经获取的Token
	 * @param accessResult 此次访问的结果信息
	 * @return
	 */
	public static nbReturn setAccessLog(String appID, String token, nbReturn accessResult){
		nbReturn nbRet = new nbReturn();
		//TODO:把访问记录记录到数据库中
		return nbRet;
	}
	
	/**
	 * 生成一个length长度的随机数
	 * @param length 最长8位，最短4位
	 * @return 生成的随机数
	 */
	public static String generateRandomDigit(int length){
		Random random = new Random();
		char[] rand = new char[length];
		for(int i = 0 ; i < length ; i++){
			Random randoom = new Random(random.nextInt());
			rand[i] = (char)(randoom.nextInt() % 9 + '0');
		}
		return String.valueOf(rand);
	}

	/**
	 * 发送一条短信出去
	 * @param phoneNumber
	 * @param theCodeToBeSend
	 * @return
	 */
	public static nbReturn sendSMSNotification(String phoneNumber,String theCodeToBeSend) {
		
		nbReturn nbRet = new nbReturn();
		
		SMS189Utils sms189Utils = new SMS189Utils();
		nbRet = sms189Utils.sendTheSMS(phoneNumber, theCodeToBeSend);
		
		return nbRet;
	}
	
}
