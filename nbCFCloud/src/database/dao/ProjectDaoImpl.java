package database.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import common.definitions.ParameterDefine;
import common.helper.nbReturn;
import database.common.BaseDaoImpl;
import database.models.NbProject;
import database.models.NbTokenPublisher;

@Repository("projectDao")
public class ProjectDaoImpl extends BaseDaoImpl<NbProject> implements ProjectDao{


	@Override
	public nbReturn doSearch(String appId, 
			                 Date startTime, 
			                 Date endTime,
			                 String[] orderBy, 
			                 String order, 
			                 Integer itemsPerPage,
			                 Integer startPage, 
			                 Integer endPage, 
			                 String[] keywords,
			                 List<Integer> statusWhiteList, 
			                 List<Integer> statusBlackList) {
		
		nbReturn nbRet = new nbReturn();
		
		//检查白名单和黑名单是否有重叠造成悖论，如果产生悖论就从黑名单中去掉 -1 表示无效
		for(Integer i:statusWhiteList){
			for(Integer j :statusBlackList){
				if(i.intValue() == j.intValue() ){
					statusBlackList.remove(j);
				}
			}
		}
		
		
		//拼接HQL语句
		String hql = "select a from NbProject a left join a.nbProjectInvestRules b where a.applicationId=:applicationId ";//applicationID不可以为空
		if( startTime != null ){
			hql += "and a.startDate >= :startTime ";
		}
		if( endTime != null){
			hql += "and a.endDate < :endTime ";
		}
		if( keywords != null){
			for(int i = 0 ; i < keywords.length; i++){
				String parameterIndex = ":keywords"+i;
				hql +="and a.projectDescription like "+parameterIndex+" "
					+ "or a.projectName like "+parameterIndex+" "
					+ "or a.projectTag like "+parameterIndex+" ";
			}
		}
		if( statusWhiteList != null && statusWhiteList.size() > 0){
				hql +="and b.status in (:statusWhiteList) ";
		}
		if( statusBlackList != null && statusBlackList.size() > 0){
			hql +="and b.status not in (:statusBlackList) ";
		}
		
		//添加排序
		if( orderBy != null && orderBy.length > 0){
			hql += "order by ";
			boolean hasFirst = false;
			for( int i = 0; i < orderBy.length; i++){
				if( orderBy.equals(ParameterDefine.SEARCHORDERBY_TIME) ){
					hql += "a.startDate ";
					hasFirst = true;
				}
				if( orderBy.equals(ParameterDefine.SEARCHORDERBY_ID) ){
					if( hasFirst )
						hql += ", ";
					hql += "a.id ";
				}
			}
			
			if( order != null){
				hql += order;
			}
		}
		
		//组织参数
		Query query = em.createQuery(hql);
		query.setParameter("applicationId", appId);
		
		if( startTime != null ){
			query.setParameter("startTime", startTime);
		}
		if( endTime != null){
			query.setParameter("endTime", endTime);
		}
		if( keywords != null){
			for(int i = 0 ; i < keywords.length; i++){
				String parameterIndex = ":keywords"+i;
				query.setParameter(parameterIndex, keywords[i]);
			}
		}
		if( statusWhiteList != null && statusWhiteList.size() > 0){
			query.setParameter("statusWhiteList", statusWhiteList);
		}
		if( statusBlackList != null && statusBlackList.size() > 0){
			query.setParameter("statusBlackList", statusBlackList);
		}
		//分页显示
		if( itemsPerPage != null){
			query.setMaxResults(itemsPerPage.intValue());
		}
		if( startPage != null ){
			query.setFirstResult((startPage.intValue()-1)*itemsPerPage.intValue());
		}
		
		//执行
		@SuppressWarnings("unchecked")
		List<NbTokenPublisher> resultList = query.getResultList();
		nbRet.setObject(resultList);
		return nbRet;
	}
	
}
