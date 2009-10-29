package org.openmrs.module.patientvisit.service;

import java.util.Date;
import java.util.List;
import com.google.gdata.client.*;
import com.google.gdata.client.calendar.*;
import com.google.gdata.data.*;
import com.google.gdata.data.acl.*;
import com.google.gdata.data.calendar.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;
import java.net.URL;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientvisit.model.Appointment;



public class GoogleAppointmentServiceImpl extends BaseOpenmrsService implements AppointmentService {

	
	
	
	public Appointment getAppointment(Integer id) {
		return null;
	}

	public List<Appointment> getAppointments(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeAppointment(Appointment appt) {
		// TODO Auto-generated method stub

	}

	public Appointment scheduleAppointment(Appointment appt) {
		// TODO Auto-generated method stub
		return null;
	}

}
