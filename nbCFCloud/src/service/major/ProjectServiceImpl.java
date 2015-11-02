package service.major;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import common.definitions.ParameterDefine;
import common.helper.nbReturn;
import common.helper.nbStringUtil;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService{

	ProjectUtils projectUtils = new ProjectUtils();
	
	/**
	 * 按照搜索条件获取项目的列表
	 * @param APPID
	 * @param SearchCondition {
     * @param   startTime :
     * @param   endTime :
     * @param   itemsPerPage :
     * @param   startPage :
     * @param   endPage :
     * @param   keyWords :
     * @param   statusCodeWhiteFilter[] {
     * @param      1;
     * @param      3;
     * @param   }
     * @param   statusCodeBlackFilter[] {
     * @param      2;
     * @param      4;
     * @param   }
     * @param } 
	 * @throws ParseException 
	 */
	@Override
	public nbReturn searchAndFetchProjectList(Map<String, Object> jsonMap) throws ParseException {
		
		nbReturn nbRet = new nbReturn();
		
		String appId = (String)jsonMap.get(ParameterDefine.APPID);
		Date startTime = nbStringUtil.String2DateTime((String)jsonMap.get(ParameterDefine.STARTTIME));
		Date endTime = nbStringUtil.String2DateTime((String)jsonMap.get(ParameterDefine.ENDTIME));
		Integer itemsPerPage = Integer.valueOf((String)jsonMap.get(ParameterDefine.ITEMSPERPAGE));
		Integer startPage = Integer.valueOf((String)jsonMap.get(ParameterDefine.STARTPAGE));
		Integer endPage = Integer.valueOf((String)jsonMap.get(ParameterDefine.ENDPAGE));
		String keywords = (String)jsonMap.get(ParameterDefine.KEYWORDS);
		@SuppressWarnings("unchecked")
		List<String> searchOrderBy = (List<String>)jsonMap.get(ParameterDefine.SEARCHORDERBY);
		String searchOrder = (String)jsonMap.get(ParameterDefine.SEARCHORDER);
		@SuppressWarnings("unchecked")
		List<Integer> statusCodeWhiteFilter = (List<Integer>)jsonMap.get(ParameterDefine.STATUSCODEWHITELIST);
		@SuppressWarnings("unchecked")
		List<Integer> statusCodeBlackFilter = (List<Integer>)jsonMap.get(ParameterDefine.STATUSCODEBLACKLIST);
		
		
		nbRet = 
			projectUtils.doSearch(appId,
								  startTime,
					              endTime,
					              searchOrderBy,
					              searchOrder,
					              itemsPerPage,
					              startPage,
					              endPage,
					              keywords,
					              statusCodeWhiteFilter,
					              statusCodeBlackFilter);
		
		return nbRet;
	}

}
