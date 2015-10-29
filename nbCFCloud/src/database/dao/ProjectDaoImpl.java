package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDaoImpl;
import database.models.NbProject;

@Repository("projectDao")
public class ProjectDaoImpl extends BaseDaoImpl<NbProject> implements ProjectDao{

}
