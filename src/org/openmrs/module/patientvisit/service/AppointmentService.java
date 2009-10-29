package org.openmrs.module.patientvisit.service;

import java.util.Date;
import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientvisit.model.Appointment;

public interface AppointmentService extends OpenmrsService {
	
	
	/**
	 * @should return appointment with the given id
	 * @param id
	 * @return
	 */
	public Appointment getAppointment(Integer id);
	
	/**
	 * @should save the appointment to the database
	 * @param appt
	 */
	public Appointment scheduleAppointment(Appointment appt);

	/**
	 * @should remove the appointment from the database
	 * @param appt
	 */
	public void removeAppointment(Appointment appt);
	
	
	/**
	 * @should get all appointment for the given date
	 * @param date
	 * @return
	 */
	public List<Appointment> getAppointments(Date date);

}
