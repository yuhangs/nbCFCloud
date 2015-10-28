package database.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import database.common.BaseDaoImpl;
import database.models.NbPhoneCheckcode;


@Repository("phoneCheckCodeDao")
public class PhoneCheckCodeDaoImpl extends BaseDaoImpl<NbPhoneCheckcode> implements PhoneCheckCodeDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<NbPhoneCheckcode> findByPhonenumberAndAppid(String phoneNumber, String appId) {
		
		String hql = "select a from NbPhoneCheckcode a where a.targetPhoneNumber=:phoneNumber and a.applicationid=:appId";
		Query query = em.createQuery(hql);
		query.setParameter("phoneNumber", phoneNumber);
		query.setParameter("appId", appId);
		
		List<NbPhoneCheckcode> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NbPhoneCheckcode> findByIdAndCheckCode(int id, String phoneCheckCode) {
		
		String hql = "select a from NbPhoneCheckcode a where a.id=:id and a.phoneCode=:phoneCheckCode";
		Query query = em.createQuery(hql);
		query.setParameter("phoneCheckCode", phoneCheckCode);
		query.setParameter("id", id);
		
		List<NbPhoneCheckcode> resultList = query.getResultList();
		return resultList;
	}

}
