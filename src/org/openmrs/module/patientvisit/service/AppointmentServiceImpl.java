package org.openmrs.module.patientvisit.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientvisit.dao.AppointmentDAO;
import org.openmrs.module.patientvisit.model.Appointment;

public class AppointmentServiceImpl extends BaseOpenmrsService implements AppointmentService {


	DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
	AppointmentDAO dao = null;

	public void setAppointmentDAO(AppointmentDAO dao) { 
		this.dao = dao;
	}
	
	HashMap<Integer, Appointment> appointments = 
		new HashMap<Integer, Appointment>();
	
	public Appointment getAppointment(Integer id) {
		//return appointments.get(id);
		return dao.getAppointment(id);
	}

	public List<Appointment> getAppointments(Date date) {

		return dao.getAppointments(date);
		/*
		List<Appointment> appts = new LinkedList<Appointment>();
		for (Appointment appt : appointments.values()) { 			
			String startDate = dateFormat.format(appt.getStartDatetime());
			String endDate = dateFormat.format(appt.getEndDatetime());
			String inputDate = dateFormat.format(date);
			if (inputDate.equals(startDate) || inputDate.equals(endDate)) { 
				appts.add(appt);
			}
		}
		return appts;
		*/
	}

	
	
	
	public void removeAppointment(Appointment appt) {
		dao.purgeappointment(appt);
		//appointments.remove(appt.getId());

	}

	public Appointment scheduleAppointment(Appointment appt) {
		
		if (appt.getUuid()==null || "".equals(appt.getUuid())) { 
			appt.setUuid(UUID.randomUUID().toString());
		}
		
		//appointments.put(appt.getId(), appt);
		appt = dao.saveAppointment(appt);
		
		return appt;
	}

}
