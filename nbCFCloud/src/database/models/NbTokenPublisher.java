package database.models;

import java.io.Serializable;

import javax.persistence.*;

import database.common.nbBaseModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * The persistent class for the nb_token_publisher database table.
 * 
 */
@Entity
@Table(name="nb_token_publisher")
@NamedQuery(name="NbTokenPublisher.findAll", query="SELECT n FROM NbTokenPublisher n")
public class NbTokenPublisher implements Serializable, nbBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, length=64)
	private String applicationId;

	@Column(length=64)
	private String clientUuid;

	@Column(nullable=false, length=64)
	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date tokenCreated;

	@Temporal(TemporalType.TIMESTAMP)
	private Date tokenFreshed;

	@Column(nullable=false)
	private Long tokenLifecycleSec;

	//bi-directional many-to-one association to NbUser
	@ManyToOne
	@JoinColumn(name="userId", nullable=false)
	private NbUser nbUser;

	public NbTokenPublisher() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getClientUuid() {
		return this.clientUuid;
	}

	public void setClientUuid(String clientUuid) {
		this.clientUuid = clientUuid;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenCreated() {
		return this.tokenCreated;
	}

	public void setTokenCreated(Date tokenCreated) {
		this.tokenCreated = tokenCreated;
	}

	public Date getTokenFreshed() {
		return this.tokenFreshed;
	}

	public void setTokenFreshed(Date tokenFreshed) {
		this.tokenFreshed = tokenFreshed;
	}

	public Long getTokenLifecycleSec() {
		return this.tokenLifecycleSec;
	}

	public void setTokenLifecycleSec(Long tokenLifecycleSec) {
		this.tokenLifecycleSec = tokenLifecycleSec;
	}

	public NbUser getNbUser() {
		return this.nbUser;
	}

	public void setNbUser(NbUser nbUser) {
		this.nbUser = nbUser;
	}

	@Override
	public Map<String, Object> modelToMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userId", nbUser.getId());
		data.put("applicationId", applicationId);
		data.put("clientUuid", clientUuid);
		data.put("token", token);
		data.put("tokenCreated", tokenCreated);
		data.put("tokenFreshed", tokenFreshed);
		data.put("tokenLifecycleSec", tokenLifecycleSec);
		return data;
	}

}