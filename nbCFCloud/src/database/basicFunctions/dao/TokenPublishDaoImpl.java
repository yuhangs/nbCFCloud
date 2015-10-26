package database.basicFunctions.dao;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import common.helper.nbReturn;
import common.helper.nbStringUtil;
import database.common.BaseDaoImpl;
import database.models.NbTokenPublisher;
import database.models.NbUser;

@Repository("tokenPublishDao")
public class TokenPublishDaoImpl  extends BaseDaoImpl<NbTokenPublisher> implements TokenPublishDao{

	@SuppressWarnings("unchecked")
	@Override
	public NbTokenPublisher checkToken(String token, String appID) {
		
		String hql = "select a from NbTokenPublisher a where a.token=:token and a.applicationId=:appID";
		Query query = em.createQuery(hql);
		query.setParameter("token", token);
		query.setParameter("appID", appID);
		
		List<NbTokenPublisher> resultList = query.getResultList();
		if( resultList != null ){
			if( resultList.size() > 0 )
				return resultList.get(0);
		}
		
		return null;
		
	}

	@Override
	public nbReturn refreshToken(String token, String appID) {
		
		NbTokenPublisher theTokenPublish = checkToken(token,  appID);
		Calendar cal = Calendar.getInstance();
		Date currentDate = cal.getTime();
		theTokenPublish.setTokenFreshed(currentDate);
		
		update(theTokenPublish);
		
		nbReturn nbRet = new nbReturn();
		nbRet.setObject(theTokenPublish);
		
		return nbRet;
	}
	
	@SuppressWarnings("unchecked")
	private NbTokenPublisher findByUserAppidUuid(String appId, NbUser nbUser, String uuid) {
	
		String hql = "select a from NbTokenPublisher a where a.clientUuid=:uuid and a.nbUser=:nbUser and a.applicationId=:appID";
		Query query = em.createQuery(hql);
		query.setParameter("uuid", uuid);
		query.setParameter("appID", appId);
		query.setParameter("nbUser", nbUser);
		
		List<NbTokenPublisher> resultList = query.getResultList();
		if( resultList != null ){
			if( resultList.size() > 0 )
				return resultList.get(0);
		}
		
		return null;
		
	}

	@Override
	public nbReturn createNewToken(String appID, NbUser nbUser, String uuid, Long lifecycleSec) throws Exception {
		Calendar cal = Calendar.getInstance();
		Date currentDate = cal.getTime();
		
		NbTokenPublisher theTokenPublish = findByUserAppidUuid(appID, nbUser, uuid);
		
		if( theTokenPublish != null ){ // 已经存在了
		
			theTokenPublish.setTokenCreated(currentDate);
			theTokenPublish.setTokenFreshed(currentDate);
			if( lifecycleSec != null){
				theTokenPublish.setTokenLifecycleSec(lifecycleSec);
			}
			update(theTokenPublish);
			
		}
		else{// 不存在，需要重新建
			
			theTokenPublish = new NbTokenPublisher();
			if( lifecycleSec == null )
				lifecycleSec = 7200l;
			if( uuid == null )
				uuid = "none";
			
			
			SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatedDate=dateformat1.format(new Date());
			
			theTokenPublish.setApplicationId(appID);
			theTokenPublish.setClientUuid(uuid);
			theTokenPublish.setTokenLifecycleSec(lifecycleSec);
			theTokenPublish.setNbUser(nbUser);
	
			theTokenPublish.setTokenFreshed(currentDate);
			theTokenPublish.setTokenCreated(currentDate);
			theTokenPublish.setToken(nbStringUtil.encryptMD5(  appID+
															   uuid+
															   String.valueOf(nbUser.getId())+
															   formatedDate
															  ));
			
			theTokenPublish = save(theTokenPublish);
		}
		nbReturn nbRet = new nbReturn();
		nbRet.setObject(theTokenPublish);
		return nbRet;
	}

}
