package database.basicFunctions.dao;

import database.common.BaseDao;
import database.models.NbUser;

public interface UserInfoDao extends BaseDao<NbUser>{
	
	public NbUser verifyUser(String username, String password, String AppId);
	public NbUser findByUsernameAndAppid(String username, String appID);
	
}
