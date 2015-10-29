package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDaoImpl;
import database.models.NbProjectMaterial;

@Repository("projectMaterialDao")
public class ProjectMaterialDaoImpl extends BaseDaoImpl<NbProjectMaterial> implements ProjectMaterialDao{

}
