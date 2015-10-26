package database.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import database.common.nbBaseModel;


/**
 * The persistent class for the nb_applications database table.
 * 
 */
@Entity
@Table(name="nb_applications")
@NamedQuery(name="NbApplications.findAll", query="SELECT n FROM NbApplications n")
public class NbApplications implements Serializable, nbBaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Lob
	@Column(nullable=false)
	private String applicationDes;

	@Column(nullable=false, length=64, unique=true)
	private String applicationID;

	@Column(nullable=false, length=64)
	private String secretKey;

	public NbApplications() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationDes() {
		return this.applicationDes;
	}

	public void setApplicationDes(String applicationDes) {
		this.applicationDes = applicationDes;
	}

	public String getApplicationID() {
		return this.applicationID;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}

	public String getSecretKey() {
		return this.secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	public Map<String, Object> modelToMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		data.put("applicationID", applicationID);
		data.put("secretKey", secretKey);
		data.put("applicationDes", applicationDes);
		return data;
	}

}