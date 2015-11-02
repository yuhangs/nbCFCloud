package database.dao;

import java.util.Date;
import java.util.List;

import common.helper.nbReturn;
import database.common.BaseDao;
import database.models.NbProject;

public interface ProjectDao extends BaseDao<NbProject>{

	/**
	 * 按照条件从数据库找出附和条件记录
	 * @param appId
	 * @param startTime
	 * @param endTime
	 * @param itemsPerPage
	 * @param startPage
	 * @param endPage
	 * @param keywords
	 * @param statusWhiteList
	 * @param statusBlackList
	 * @return
	 */
	nbReturn doSearch(String appId, 
			          Date startTime, 
			          Date endTime,
			          String[] orderBy,
			          String order,
			          Integer itemsPerPage, 
			          Integer startPage, 
			          Integer endPage,
			          String[] keywords, 
			          List<Integer> statusWhiteList, 
			          List<Integer> statusBlackList);

}
