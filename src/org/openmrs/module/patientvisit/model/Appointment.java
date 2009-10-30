package org.openmrs.module.patientvisit.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.Query;
import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.User;

public class Appointment extends BaseOpenmrsObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1063445703365994315L;
	
	private Integer id;
	private User provider;
	private Date startDatetime;
	private Date endDatetime;
	private Location location;
	private Patient patient; 
	
	
	public User getProvider() {
		return provider;
	}
	public void setProvider(User provider) {
		this.provider = provider;
	}
	public Date getStartDatetime() {
		return startDatetime;
	}
	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}
	public Date getEndDatetime() {
		return endDatetime;
	}
	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;		
	}
	
	
	
}
