package database.models;

import java.io.Serializable;

import javax.persistence.*;

import database.common.nbBaseModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The persistent class for the nb_project database table.
 * 
 */
@Entity
@Table(name="nb_project")
@NamedQuery(name="NbProject.findAll", query="SELECT n FROM NbProject n")
public class NbProject implements Serializable , nbBaseModel{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date endDate;

	@Lob
	@Column(nullable=false)
	private String projectName;
	
	@Lob
	@Column(nullable=false)
	private String projectDescription;
	

	@Column(nullable=false, length=64)
	private String projectTag;

	@Column(nullable=false)
	private int projectType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date startDate;
	
	@Column(nullable=false, length=64)
	private String applicationId;

	//bi-directional many-to-one association to NbProjectInvestRule
	@OneToMany(mappedBy="nbProject")
	private List<NbProjectInvestRule> nbProjectInvestRules;

	//bi-directional many-to-one association to NbProjectMaterial
	@OneToMany(mappedBy="nbProject")
	private List<NbProjectMaterial> nbProjectMaterials;

	//bi-directional many-to-one association to NbProjectRightRule
	@OneToMany(mappedBy="nbProject")
	private List<NbProjectRightRule> nbProjectRightRules;

	public NbProject() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectTag() {
		return this.projectTag;
	}

	public void setProjectTag(String projectTag) {
		this.projectTag = projectTag;
	}

	public int getProjectType() {
		return this.projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<NbProjectInvestRule> getNbProjectInvestRules() {
		return this.nbProjectInvestRules;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setNbProjectInvestRules(List<NbProjectInvestRule> nbProjectInvestRules) {
		this.nbProjectInvestRules = nbProjectInvestRules;
	}

	public NbProjectInvestRule addNbProjectInvestRule(NbProjectInvestRule nbProjectInvestRule) {
		getNbProjectInvestRules().add(nbProjectInvestRule);
		nbProjectInvestRule.setNbProject(this);

		return nbProjectInvestRule;
	}

	public NbProjectInvestRule removeNbProjectInvestRule(NbProjectInvestRule nbProjectInvestRule) {
		getNbProjectInvestRules().remove(nbProjectInvestRule);
		nbProjectInvestRule.setNbProject(null);

		return nbProjectInvestRule;
	}

	public List<NbProjectMaterial> getNbProjectMaterials() {
		return this.nbProjectMaterials;
	}

	public void setNbProjectMaterials(List<NbProjectMaterial> nbProjectMaterials) {
		this.nbProjectMaterials = nbProjectMaterials;
	}

	public NbProjectMaterial addNbProjectMaterial(NbProjectMaterial nbProjectMaterial) {
		getNbProjectMaterials().add(nbProjectMaterial);
		nbProjectMaterial.setNbProject(this);

		return nbProjectMaterial;
	}

	public NbProjectMaterial removeNbProjectMaterial(NbProjectMaterial nbProjectMaterial) {
		getNbProjectMaterials().remove(nbProjectMaterial);
		nbProjectMaterial.setNbProject(null);

		return nbProjectMaterial;
	}

	public List<NbProjectRightRule> getNbProjectRightRules() {
		return this.nbProjectRightRules;
	}

	public void setNbProjectRightRules(List<NbProjectRightRule> nbProjectRightRules) {
		this.nbProjectRightRules = nbProjectRightRules;
	}

	public NbProjectRightRule addNbProjectRightRule(NbProjectRightRule nbProjectRightRule) {
		getNbProjectRightRules().add(nbProjectRightRule);
		nbProjectRightRule.setNbProject(this);

		return nbProjectRightRule;
	}

	public NbProjectRightRule removeNbProjectRightRule(NbProjectRightRule nbProjectRightRule) {
		getNbProjectRightRules().remove(nbProjectRightRule);
		nbProjectRightRule.setNbProject(null);

		return nbProjectRightRule;
	}

	@Override
	public Map<String, Object> modelToMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		//TODO:完善输出的结构方法，不然没法自动转化成json
		return data;
	}

}