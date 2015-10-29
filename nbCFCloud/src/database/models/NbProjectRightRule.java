package database.models;

import java.io.Serializable;

import javax.persistence.*;

import database.common.nbBaseModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The persistent class for the nb_project_right_rules database table.
 * 
 */
@Entity
@Table(name="nb_project_right_rules")
@NamedQuery(name="NbProjectRightRule.findAll", query="SELECT n FROM NbProjectRightRule n")
public class NbProjectRightRule implements Serializable, nbBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String decription;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	private int endDateOffset;

	private int endDateType;

	private double fixedProfit;

	private double floatProfit;

	@Lob
	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private int rightType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	private int startDateOffset;

	private int startDateType;

	//bi-directional many-to-one association to NbProject
	@ManyToOne
	@JoinColumn(name="projectId", nullable=false)
	private NbProject nbProject;

	//bi-directional many-to-many association to NbProjectInvestRule
	@ManyToMany(mappedBy="nbProjectRightRules")
	private List<NbProjectInvestRule> nbProjectInvestRules;

	public NbProjectRightRule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDecription() {
		return this.decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getEndDateOffset() {
		return this.endDateOffset;
	}

	public void setEndDateOffset(int endDateOffset) {
		this.endDateOffset = endDateOffset;
	}

	public int getEndDateType() {
		return this.endDateType;
	}

	public void setEndDateType(int endDateType) {
		this.endDateType = endDateType;
	}

	public double getFixedProfit() {
		return this.fixedProfit;
	}

	public void setFixedProfit(double fixedProfit) {
		this.fixedProfit = fixedProfit;
	}

	public double getFloatProfit() {
		return this.floatProfit;
	}

	public void setFloatProfit(double floatProfit) {
		this.floatProfit = floatProfit;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRightType() {
		return this.rightType;
	}

	public void setRightType(int rightType) {
		this.rightType = rightType;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getStartDateOffset() {
		return this.startDateOffset;
	}

	public void setStartDateOffset(int startDateOffset) {
		this.startDateOffset = startDateOffset;
	}

	public int getStartDateType() {
		return this.startDateType;
	}

	public void setStartDateType(int startDateType) {
		this.startDateType = startDateType;
	}

	public NbProject getNbProject() {
		return this.nbProject;
	}

	public void setNbProject(NbProject nbProject) {
		this.nbProject = nbProject;
	}

	public List<NbProjectInvestRule> getNbProjectInvestRules() {
		return this.nbProjectInvestRules;
	}

	public void setNbProjectInvestRules(List<NbProjectInvestRule> nbProjectInvestRules) {
		this.nbProjectInvestRules = nbProjectInvestRules;
	}

	@Override
	public Map<String, Object> modelToMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		//TODO:完善输出的结构方法，不然没法自动转化成json
		return data;
	}
}