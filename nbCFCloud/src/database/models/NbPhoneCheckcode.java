package database.models;

import java.io.Serializable;

import javax.persistence.*;

import database.common.nbBaseModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * The persistent class for the nb_phone_checkcode database table.
 * 
 */
@Entity
@Table(name="nb_phone_checkcode")
@NamedQuery(name="NbPhoneCheckcode.findAll", query="SELECT n FROM NbPhoneCheckcode n")
public class NbPhoneCheckcode implements Serializable , nbBaseModel{
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String applicationid;

	@Column(name="continouse_try_cycle")
	private int continouseTryCycle;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="latest_event_time")
	private Date latestEventTime;

	private int lifecycle;

	@Column(name="phone_code")
	private String phoneCode;

	@Column(name="request_reason_code")
	private String requestReasonCode;

	@Lob
	@Column(name="request_reason_comment")
	private String requestReasonComment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="requested_time")
	private Date requestedTime;

	@Column(name="send_status")
	private int sendStatus;

	@Column(name="target_phone_number")
	private String targetPhoneNumber;

	public NbPhoneCheckcode() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplicationid() {
		return this.applicationid;
	}

	public void setApplicationid(String applicationid) {
		this.applicationid = applicationid;
	}

	public int getContinouseTryCycle() {
		return this.continouseTryCycle;
	}

	public void setContinouseTryCycle(int continouseTryCycle) {
		this.continouseTryCycle = continouseTryCycle;
	}

	public Date getLatestEventTime() {
		return this.latestEventTime;
	}

	public void setLatestEventTime(Date latestEventTime) {
		this.latestEventTime = latestEventTime;
	}

	public int getLifecycle() {
		return this.lifecycle;
	}

	public void setLifecycle(int lifecycle) {
		this.lifecycle = lifecycle;
	}

	public String getPhoneCode() {
		return this.phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getRequestReasonCode() {
		return this.requestReasonCode;
	}

	public void setRequestReasonCode(String requestReasonCode) {
		this.requestReasonCode = requestReasonCode;
	}

	public String getRequestReasonComment() {
		return this.requestReasonComment;
	}

	public void setRequestReasonComment(String requestReasonComment) {
		this.requestReasonComment = requestReasonComment;
	}

	public Date getRequestedTime() {
		return this.requestedTime;
	}

	public void setRequestedTime(Date requestedTime) {
		this.requestedTime = requestedTime;
	}

	public int getSendStatus() {
		return this.sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getTargetPhoneNumber() {
		return this.targetPhoneNumber;
	}

	public void setTargetPhoneNumber(String targetPhoneNumber) {
		this.targetPhoneNumber = targetPhoneNumber;
	}

	@Override
	public Map<String, Object> modelToMap() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		data.put("applicationid", applicationid);
		data.put("continouseTryCycle", continouseTryCycle);
		data.put("latestEventTime", latestEventTime);
		data.put("lifecycle", lifecycle);
		data.put("phoneCode", phoneCode);
		data.put("requestReasonCode", requestReasonCode);
		data.put("requestReasonComment", requestReasonComment);
		data.put("requestedTime", requestedTime);
		data.put("sendStatus", sendStatus);
		data.put("targetPhoneNumber", targetPhoneNumber);
		return data;
	}

}