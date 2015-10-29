package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDaoImpl;
import database.models.NbProjectRightRule;

@Repository("projectRightRuleDao")
public class ProjectRightRuleDaoImpl extends BaseDaoImpl<NbProjectRightRule> implements ProjectRightRuleDao{

}
