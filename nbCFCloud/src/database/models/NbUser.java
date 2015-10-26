package database.models;

import java.io.Serializable;

import javax.persistence.*;

import database.common.nbBaseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The persistent class for the nb_user database table.
 * 
 */
@Entity
@Table(name="nb_user")
@NamedQuery(name="NbUser.findAll", query="SELECT n FROM NbUser n")
public class NbUser implements Serializable, nbBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(length=32)
	private String email;

	private Boolean emailVerified;

	@Column(length=32)
	private String mobilePhone;

	private Boolean mobilePhoneVerified;

	@Column(nullable=false, length=64)
	private String password;

	@Column(nullable=false, length=32)
	private String username;

	@Column(nullable=false, length=64)
	private String userOpenCode;
	
	@Column(nullable=false, length=64)
	private String applicationId;

	//bi-directional many-to-one association to NbTokenPublisher
	@OneToMany(mappedBy="nbUser")
	private List<NbTokenPublisher> nbTokenPublishers;
	
	@ManyToOne
	@JoinColumn(name="externalUserCallId", nullable=true)
	private NbUserExternalCall nbUserExternalCall;

	public NbUser() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailVerified() {
		return this.emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Boolean getMobilePhoneVerified() {
		return this.mobilePhoneVerified;
	}

	public void setMobilePhoneVerified(Boolean mobilePhoneVerified) {
		this.mobilePhoneVerified = mobilePhoneVerified;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserOpenCode() {
		return this.userOpenCode;
	}

	public void setUserOpenCode(String userOpenCode) {
		this.userOpenCode = userOpenCode;
	}

	public List<NbTokenPublisher> getNbTokenPublishers() {
		return this.nbTokenPublishers;
	}

	public void setNbTokenPublishers(List<NbTokenPublisher> nbTokenPublishers) {
		this.nbTokenPublishers = nbTokenPublishers;
	}

	public NbTokenPublisher addNbTokenPublisher(NbTokenPublisher nbTokenPublisher) {
		getNbTokenPublishers().add(nbTokenPublisher);
		nbTokenPublisher.setNbUser(this);

		return nbTokenPublisher;
	}

	public NbTokenPublisher removeNbTokenPublisher(NbTokenPublisher nbTokenPublisher) {
		getNbTokenPublishers().remove(nbTokenPublisher);
		nbTokenPublisher.setNbUser(null);

		return nbTokenPublisher;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public NbUserExternalCall getNbUserExternalCall() {
		return nbUserExternalCall;
	}

	public void setNbUserExternalCall(NbUserExternalCall nbUserExternalCall) {
		this.nbUserExternalCall = nbUserExternalCall;
	}

	@Override
	public Map<String, Object> modelToMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("username", username);
		data.put("password", password);
		data.put("mobile", mobilePhone);
		data.put("mobilePhoneVerified", mobilePhoneVerified);
		data.put("email", email);
		data.put("emailVerified", emailVerified);
		data.put("applicationId", applicationId);
		data.put("userOpenCode", userOpenCode);
		data.put("externalUserCall", nbUserExternalCall == null? null : nbUserExternalCall.getId());
		return data;
	}

}