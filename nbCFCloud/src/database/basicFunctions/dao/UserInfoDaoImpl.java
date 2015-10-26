package database.basicFunctions.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDaoImpl;
import database.models.NbUser;

@Repository("userInfoDao")
public class UserInfoDaoImpl extends BaseDaoImpl<NbUser> implements UserInfoDao{


	@SuppressWarnings("unchecked")
	@Override
	public NbUser verifyUser(String username, String password, String appId) {
		
//		Transaction trans = session.beginTransaction();
//	    String sql = "update User user set user.age=20 where user.name='admin'";
//	    Query query = session.createQuery(hql);
		
		String hql = "select a from NbUser a where a.username=:username and a.password=:password and a.applicationId=:appId";
		Query query = em.createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("password", password);
		query.setParameter("appId", appId);
		
		List<NbUser> resultList = query.getResultList();
		if( resultList != null ){
			if( resultList.size() > 0 )
				return resultList.get(0);
		}
		
		return null;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public NbUser findByUsernameAndAppid(String username, String appID) {
		
		String hql = "select a from NbUser a where a.username=:username and a.applicationId=:appId";
		Query query = em.createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("appId", appID);
		
		
		List<NbUser> resultList = query.getResultList();
		if( resultList != null ){
			if( resultList.size() > 0 )
				return resultList.get(0);
		}
		return null;
	}

}
