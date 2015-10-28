package database.dao;

import common.helper.nbReturn;
import database.common.BaseDao;
import database.models.NbTokenPublisher;
import database.models.NbUser;

public interface TokenPublishDao extends BaseDao<NbTokenPublisher>{

	public NbTokenPublisher checkToken(String token, String appID);
	public nbReturn refreshToken(String token, String appID);
	public nbReturn createNewToken(String appID, NbUser nbUser,String uuid, Long lifecycleSec) throws Exception;
}
