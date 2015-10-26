package database.basicFunctions.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDaoImpl;
import database.models.NbUserExternalCall;

@Repository("userExternalCallDao")
public class UserExternalCallDaoImpl extends BaseDaoImpl<NbUserExternalCall> implements UserExternalCallDao{

}
