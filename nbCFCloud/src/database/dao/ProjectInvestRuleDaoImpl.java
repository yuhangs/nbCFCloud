package database.dao;

import org.springframework.stereotype.Repository;

import database.common.BaseDaoImpl;
import database.models.NbProjectInvestRule;

@Repository("projectInvestRuleDao")
public class ProjectInvestRuleDaoImpl extends BaseDaoImpl<NbProjectInvestRule> implements ProjectInvestRuleDao{

}
