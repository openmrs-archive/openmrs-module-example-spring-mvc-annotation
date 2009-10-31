package org.openmrs.module.patientvisit.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientvisit.model.Appointment;
import org.openmrs.test.BaseModuleContextSensitiveTest;


public class GoogleAppointmentServiceTest extends BaseModuleContextSensitiveTest {

	@Before
	public void before() throws Exception { 		
		authenticate();
		initializeInMemoryDatabase();		
	}
		
	@Test
	public void shouldConnectToGoogleCalendar() { 			
		new GoogleAppointmentServiceImpl().printCalendarEvents();
	}
	
	/**
	 * 
	 */
	@Test
	public void shouldScheduleAppointment() { 		
		Date now = new Date();
		Appointment appt = new Appointment();
		appt.setStartDatetime(now);
		appt.setEndDatetime(now);
		appt.setLocation(Context.getLocationService().getDefaultLocation());
		appt.setPatient(Context.getPatientService().getPatient(2));
		appt.setProvider(Context.getUserService().getUserByUsername("admin"));
		
		appt = 
			new GoogleAppointmentServiceImpl().scheduleAppointment(appt);		
		
		Assert.assertNotNull("should not allow a null appointment", appt);
		Assert.assertNotNull("should not allow a null uuid", appt.getUuid());
		
		Appointment sameAppt = 
			new GoogleAppointmentServiceImpl().getAppointmentByUuid(appt.getUuid());

		Assert.assertNotNull("should be able to retrieve the appointment by uuid", sameAppt);		
	}
	
	
		
	
	@Test
	public void shouldGetAppointmentsForToday() { 				
		List<Appointment> appts = 
			new GoogleAppointmentServiceImpl().getAppointments(new Date());
		Assert.assertNotNull("appts is null", appts);
		Assert.assertTrue("appts is empty", !appts.isEmpty());
		
		
	}
	
	
	
}
