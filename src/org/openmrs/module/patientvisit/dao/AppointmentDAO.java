package org.openmrs.module.patientvisit.dao;

import java.util.Date;
import java.util.List;

import org.openmrs.Patient;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.patientvisit.model.Appointment;

public interface AppointmentDAO {
	public Appointment saveAppointment(Appointment appointment) throws DAOException;	
	public void purgeappointment(Appointment appointment) throws DAOException;
	public Appointment getAppointment(Integer id) throws DAOException;
	public List<Appointment> getAppointments(Date date) throws DAOException;
	public List<Patient> getPatientsWithAppointmentsOnDate(Date date) throws DAOException;	
}
