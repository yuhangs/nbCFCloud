package database.models;

import java.io.Serializable;

import javax.persistence.*;

import database.common.nbBaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The persistent class for the nb_project_invest_rules database table.
 * 
 */
@Entity
@Table(name="nb_project_invest_rules")
@NamedQuery(name="NbProjectInvestRule.findAll", query="SELECT n FROM NbProjectInvestRule n")
public class NbProjectInvestRule implements Serializable, nbBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private double entryInvest;

	private double maxInvest;

	@Lob
	private String rightsRelations;

	@Column(nullable=false)
	private int status;

	//bi-directional many-to-one association to NbProject
	@ManyToOne
	@JoinColumn(name="projectId", nullable=false)
	private NbProject nbProject;

	//bi-directional many-to-many association to NbProjectRightRule
	@ManyToMany
	@JoinTable(
		name="nb_project_joint_invest_rights"
		, joinColumns={
			@JoinColumn(name="investRuleId", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="rightRuleId", nullable=false)
			}
		)
	private List<NbProjectRightRule> nbProjectRightRules;

	public NbProjectInvestRule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getEntryInvest() {
		return this.entryInvest;
	}

	public void setEntryInvest(double entryInvest) {
		this.entryInvest = entryInvest;
	}

	public double getMaxInvest() {
		return this.maxInvest;
	}

	public void setMaxInvest(double maxInvest) {
		this.maxInvest = maxInvest;
	}

	public String getRightsRelations() {
		return this.rightsRelations;
	}

	public void setRightsRelations(String rightsRelations) {
		this.rightsRelations = rightsRelations;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public NbProject getNbProject() {
		return this.nbProject;
	}

	public void setNbProject(NbProject nbProject) {
		this.nbProject = nbProject;
	}

	public List<NbProjectRightRule> getNbProjectRightRules() {
		return this.nbProjectRightRules;
	}

	public void setNbProjectRightRules(List<NbProjectRightRule> nbProjectRightRules) {
		this.nbProjectRightRules = nbProjectRightRules;
	}
	
	@Override
	public Map<String, Object> modelToMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		//TODO:完善输出的结构方法，不然没法自动转化成json
		return data;
	}

}