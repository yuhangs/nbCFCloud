package database.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the nb_user_external_calls database table.
 * 
 */
@Entity
@Table(name="nb_user_external_calls")
@NamedQuery(name="NbUserExternalCall.findAll", query="SELECT n FROM NbUserExternalCall n")
public class NbUserExternalCall implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=128)
	private String exMyAppId;

	@Column(nullable=false, length=128)
	private String exMySecretKey;

	@Column(nullable=false, unique=true, length=64)
	private String externalProvider;

	@Column(nullable=false, length=128)
	private String exToken;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date exTokenExpireTime;

	@Lob
	@Column(nullable=false)
	private String exURL_GetToken;

	@Lob
	@Column(nullable=false)
	private String exURL_GetUserInfo;

	@Lob
	@Column(nullable=false)
	private String exURL_Verify;

	public NbUserExternalCall() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExMyAppId() {
		return this.exMyAppId;
	}

	public void setExMyAppId(String exMyAppId) {
		this.exMyAppId = exMyAppId;
	}

	public String getExMySecretKey() {
		return this.exMySecretKey;
	}

	public void setExMySecretKey(String exMySecretKey) {
		this.exMySecretKey = exMySecretKey;
	}

	public String getExternalProvider() {
		return this.externalProvider;
	}

	public void setExternalProvider(String externalProvider) {
		this.externalProvider = externalProvider;
	}

	public String getExToken() {
		return this.exToken;
	}

	public void setExToken(String exToken) {
		this.exToken = exToken;
	}

	public Date getExTokenExpireTime() {
		return this.exTokenExpireTime;
	}

	public void setExTokenExpireTime(Date exTokenExpireTime) {
		this.exTokenExpireTime = exTokenExpireTime;
	}

	public String getExURL_GetToken() {
		return this.exURL_GetToken;
	}

	public void setExURL_GetToken(String exURL_GetToken) {
		this.exURL_GetToken = exURL_GetToken;
	}

	public String getExURL_GetUserInfo() {
		return this.exURL_GetUserInfo;
	}

	public void setExURL_GetUserInfo(String exURL_GetUserInfo) {
		this.exURL_GetUserInfo = exURL_GetUserInfo;
	}

	public String getExURL_Verify() {
		return this.exURL_Verify;
	}

	public void setExURL_Verify(String exURL_Verify) {
		this.exURL_Verify = exURL_Verify;
	}

}