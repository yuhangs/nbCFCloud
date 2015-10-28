package database.dao;

import org.springframework.stereotype.Repository;

import common.definitions.ReturnCode;
import common.helper.nbReturn;
import common.helper.nbStringUtil;
import database.common.BaseDaoImpl;
import database.common.QueryParam;
import database.models.NbApplications;

@Repository("applicationsDao")
public class ApplicationsDaoImpl extends BaseDaoImpl<NbApplications> implements ApplicationsDao {

	@Override
	public nbReturn generateSignature(String appID, String timeStamp, String uuid) throws Exception {
		nbReturn nbRet = new nbReturn();
		
		QueryParam param = new QueryParam();
		param.addParam("applicationID", appID);
		NbApplications nbApplication = findByCriteriaForUnique(param);
		
		
		if( nbApplication == null ){//没有找到这个applicationID
			
			nbRet.setError(ReturnCode.APPLICATION_ID_NOT_FOUND);
			return nbRet;
			
		}else{//找到了这个ApplicationID，开始生成signature
			
			String secretKey = nbApplication.getSecretKey();
			String assembled = appID +
					           secretKey +
					           timeStamp +
					           uuid ;
			String signature = nbStringUtil.encryptMD5(assembled);
			nbRet.setObject(signature);
			return nbRet;
		
		}
	}

}
