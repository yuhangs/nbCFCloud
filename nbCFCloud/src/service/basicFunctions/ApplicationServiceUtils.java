package service.basicFunctions;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import main.entry.api.ParameterDefine;
import common.helper.CommonHelper;
import common.helper.nbReturn;
import common.helper.nbStringUtil;
import database.basicFunctions.dao.PhoneCheckCodeDao;
import database.models.NbPhoneCheckcode;

public class ApplicationServiceUtils {

	@Autowired
	PhoneCheckCodeDao phoneCheckCodeDao;
	
	/**
	 * 创建一个新的发送
	 * @param appId
	 * @param PhoneNumber
	 * @param SendReasonCode
	 * @param SendReasonCommnets
	 * @param phoneCheckCode 如果为空的话，就表示新建一个发送记录
	 * @return
	 */
	public nbReturn createNewRecordAndSendPhoneCheckCode(
			String appId, 
			String PhoneNumber, 
			String SendReasonCode,
			String SendReasonCommnets,
			NbPhoneCheckcode phoneCheckCode){
		
		String theCodeToBeSend = CommonHelper.generateRandomDigit(6);//生成6位随机数;
		Calendar cal = Calendar.getInstance();
		
		if( phoneCheckCode == null ){
			phoneCheckCode = new NbPhoneCheckcode();
		
			phoneCheckCode.setContinouseTryCycle(60);//60秒内不可再发送
			phoneCheckCode.setLifecycle(300); //300秒内有效
			phoneCheckCode.setRequestReasonCode(SendReasonCode);
			phoneCheckCode.setRequestReasonComment(SendReasonCommnets);
			phoneCheckCode.setSendStatus(0);//创建发送
			phoneCheckCode.setTargetPhoneNumber(PhoneNumber);
			phoneCheckCode.setApplicationid(appId);//商户的ID
			phoneCheckCode.setRequestedTime(cal.getTime());//申请发送的时间
			phoneCheckCode.setPhoneCode(theCodeToBeSend);
			phoneCheckCode.setLatestEventTime(cal.getTime());
			
			phoneCheckCode = phoneCheckCodeDao.save(phoneCheckCode);
			//以上创建发送请求成功，先保存到数据库
		}
		
		nbReturn nbRet = CommonHelper.sendNotification(PhoneNumber, theCodeToBeSend);
		//实际发送出去
		
		cal = Calendar.getInstance();
		phoneCheckCode.setLatestEventTime(cal.getTime());
		if( !nbRet.isSuccess() ){
			phoneCheckCode.setSendStatus(2); //发送失败
		}
		else{
			phoneCheckCode.setSendStatus(1); //发送成功
		}
		phoneCheckCode = phoneCheckCodeDao.update(phoneCheckCode);
		//以上把发送结果更新到数据库中
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put(ParameterDefine.PHONECHECKAFFAIRID, phoneCheckCode.getId());
		retMap.put(ParameterDefine.PHONECHECKCODELIFECYCLE, phoneCheckCode.getLifecycle());
		retMap.put(ParameterDefine.PHONECHECKCODEINTERVAL, phoneCheckCode.getContinouseTryCycle());
		cal.add(Calendar.SECOND, phoneCheckCode.getLifecycle());
		retMap.put(ParameterDefine.PHONECHECKCODEEXPIRETIME, 
				nbStringUtil.DateTime2String(cal.getTime()) );
		
		nbRet.setObject(retMap);
		
		return nbRet;
	}
	/**
	 * 当用户要求发送验证码的时候，先看数据库里面是否有可用的记录条来使用，尽量减少数据库爆炸式增长
	 * @param appId
	 * @param PhoneNumber
	 * @param SendReasonCode
	 * @param SendReasonCommnets
	 * @return 为空表示没有可用的记录条
	 */
	public nbReturn checkPhoneCodeSendAndResend(
			String appId, 
			String PhoneNumber, 
			String SendReasonCode,
			String SendReasonCommnets){
		
		int sendReasonCode = 0; //0是不明原因
		if( SendReasonCode != null )
			sendReasonCode = Integer.valueOf(sendReasonCode);
		
		List<NbPhoneCheckcode> phoneCheckCodeList = phoneCheckCodeDao.findByPhonenumberAndAppid(PhoneNumber,  appId);

		for( NbPhoneCheckcode pcc: phoneCheckCodeList){
			if( pcc.getSendStatus() == 3 ||//已经用掉了，没作用的记录
				pcc.getSendStatus() == 4 ){//已经过期了，没作用的记录
				continue;
			}
			
			if( pcc.getSendStatus() <= 2 ){//发送失败了,未发送，发送成功，看看是否过期，如果过期了就更新状态，如果没有过期，且发送用途sendReasonCode和参数里是一致的话就再发送一次
				
				int reasonCodeStored = 0;
				if( pcc.getRequestReasonCode() != null){
					reasonCodeStored = Integer.valueOf(pcc.getRequestReasonCode());
				}
				
				if( reasonCodeStored == sendReasonCode ){//如果发送原因是一直的，哪怕都是空也算一致的
					
					Calendar cal = Calendar.getInstance();
					long interval = pcc.getContinouseTryCycle();
					long dateToExpire = pcc.getLatestEventTime().getTime()+interval*1000;
					
					if( cal.getTime().getTime() > dateToExpire ){//已经过期了
						pcc.setSendStatus(4);//设置成过期了
						phoneCheckCodeDao.update(pcc);
						
					}else{//如果没有过期
						return createNewRecordAndSendPhoneCheckCode(appId,  PhoneNumber, SendReasonCode, SendReasonCommnets, pcc);
					}
				}
			}
		}
		
		return null;
		
	}
	
	/**
	 * 检查短信验证码
	 * @param phoneCheckAffairid
	 * @param phoneCheckCode
	 * @return
	 */
	public nbReturn checkPhoneCode(String phoneCheckAffairid, String phoneCheckCode) {
		
		List<NbPhoneCheckcode> pccList = phoneCheckCodeDao.findByIdAndCheckCode(Integer.valueOf(phoneCheckAffairid).intValue(), phoneCheckCode);
		
		nbReturn nbRet = new nbReturn();
		
		if( pccList != null || pccList.size() == 0){
			nbRet.setError(nbReturn.ReturnCode.REQUESTED_PHONE_CODE_NOT_FOUND);
			return nbRet;
		}
		for( NbPhoneCheckcode pcc : pccList){
			
			if( pcc.getSendStatus() == 3 || pcc.getSendStatus() == 4){
				nbRet.setError(nbReturn.ReturnCode.REQUESTED_PHONE_CODE_EXPIRED);
			}
			
			if( pcc.getSendStatus() == 1 ){//发送成功
				
				Calendar cal = Calendar.getInstance();
				int lifecycle = pcc.getLifecycle();
				Date date = pcc.getLatestEventTime();
				
				if( cal.getTime().getTime() > ( date.getTime()+lifecycle*1000 ) ){ //过期了
					pcc.setSendStatus(4);
					phoneCheckCodeDao.update(pcc);
				}else{ // 没过期
					nbRet.setError(nbReturn.ReturnCode._SUCCESS);
					pcc.setSendStatus(3);
					phoneCheckCodeDao.update(pcc);
				}
			}
		}
		
		return nbRet;
	}
}
