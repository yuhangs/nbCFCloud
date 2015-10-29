package service.major;

import java.util.Map;

import common.helper.nbReturn;

public interface ProjectService {

	public nbReturn searchAndFetchProjectList(Map<String, Object> jsonMap);

}
