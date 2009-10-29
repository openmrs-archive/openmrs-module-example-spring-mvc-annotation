package org.openmrs.module.patientvisit.service;

import java.util.Date;
import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientvisit.model.Appointment;

public interface AppointmentService extends OpenmrsService {
	
	public void scheduleAppointment(Appointment appt);
	public void removeAppointment(Appointment appt);
	public List<Appointment> getAppointments(Date date);

}
