package database.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import database.common.nbBaseModel;


/**
 * The persistent class for the nb_project_material database table.
 * 
 */
@Entity
@Table(name="nb_project_material")
@NamedQuery(name="NbProjectMaterial.findAll", query="SELECT n FROM NbProjectMaterial n")
public class NbProjectMaterial implements Serializable, nbBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String projectName;

	@Column(nullable=false, length=64)
	private String projectTag;

	//bi-directional many-to-one association to NbProject
	@ManyToOne
	@JoinColumn(name="projectId", nullable=false)
	private NbProject nbProject;

	public NbProjectMaterial() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public NbProject getNbProject() {
		return this.nbProject;
	}

	public void setNbProject(NbProject nbProject) {
		this.nbProject = nbProject;
	}

	@Override
	public Map<String, Object> modelToMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		//TODO:完善输出的结构方法，不然没法自动转化成json
		return data;
	}
}