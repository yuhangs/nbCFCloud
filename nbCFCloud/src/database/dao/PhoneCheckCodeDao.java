package database.dao;

import java.util.List;

import database.common.BaseDao;
import database.models.NbPhoneCheckcode;

public interface PhoneCheckCodeDao extends BaseDao<NbPhoneCheckcode>{

	List<NbPhoneCheckcode> findByPhonenumberAndAppid(String phoneNumber,String appId);

	List<NbPhoneCheckcode> findByIdAndCheckCode(int id, String phoneCheckCode);
	

	
}
