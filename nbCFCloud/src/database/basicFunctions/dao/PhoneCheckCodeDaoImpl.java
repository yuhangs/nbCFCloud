package database.basicFunctions.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDaoImpl;
import database.models.NbPhoneCheckcode;


@Repository("phoneCheckCodeDao")
public class PhoneCheckCodeDaoImpl extends BaseDaoImpl<NbPhoneCheckcode> implements PhoneCheckCodeDao{

}
