package service.major;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import common.definitions.ParameterDefine;
import common.definitions.ReturnCode;
import common.helper.nbReturn;
import database.dao.ProjectDao;


public class ProjectUtils {

	@Autowired
	ProjectDao projectDao;

	/**
	 * 根据条件对项目进行搜索过滤
	 * @param startTime
	 * @param endTime
	 * @param itemsPerPage
	 * @param startPage
	 * @param endPage
	 * @param keywords
	 * @param statusCodeWhiteFilter
	 * @param statusCodeBlackFilter
	 * @return
	 */
	public nbReturn doSearch(String appId, 
			                 Date startTime, 
			                 Date endTime,
			                 List<String> searchOrderBy,
			                 String searchOrder,
			                 Integer itemsPerPage, 
			                 Integer startPage,
			                 Integer endPage, 
			                 String keywords,
			                 List<Integer> statusCodeWhiteFilter,
			                 List<Integer> statusCodeBlackFilter) {
		if( appId == null ){
			nbReturn nbRet = new nbReturn();
			nbRet.setError(ReturnCode.NECESSARY_PARAMETER_IS_NULL);
			return nbRet;
		}
		
		String[] orderBy = new String[searchOrderBy.size()];
		
		int count = 0;
		for( String it:searchOrderBy){
			orderBy[count++] = it;
		}
		
		String order = null;
		if( searchOrder.equals(ParameterDefine.SEARCHORDER_ASC) )
			order = "ASC";
		if( searchOrder.equals(ParameterDefine.SEARCHORDER_DESC) )
			order = "DESC";
		
		String[] keyWords = null;
		if(keywords != null){
			keyWords = keywords.split(" ");
			for( int i = 0 ; i < keyWords.length ; i++){
				keyWords[i] = keyWords[i].trim();
			}
		}
		return	projectDao.doSearch(appId, 
						            startTime, 
						            endTime, 
						            orderBy,
						            order,
						            itemsPerPage, 
						            startPage,
						            endPage, 
						            keyWords,
						            statusCodeWhiteFilter,
						            statusCodeBlackFilter);
		
	}
}
