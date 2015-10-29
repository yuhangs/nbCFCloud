package service.major;

import java.util.Map;

import org.springframework.stereotype.Service;

import common.helper.nbReturn;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService{

	ProjectUtils projectUtils = new ProjectUtils();
	
	/**
	 * 按照搜索条件获取项目的列表
	 */
	@Override
	public nbReturn searchAndFetchProjectList(Map<String, Object> jsonMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
