package org.openmrs.module.patientvisit.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientvisit.model.Appointment;
import org.openmrs.test.BaseModuleContextSensitiveTest;


public class AppointmentServiceTest extends BaseModuleContextSensitiveTest {

	@Before
	public void before() throws Exception { 		
		authenticate();
		initializeInMemoryDatabase();		
	}
		
	@Test
	public void shouldHaveDefaultLocation() { 	
		Location defaultLocation = 
			Context.getLocationService().getDefaultLocation();		
		Assert.assertNotNull(defaultLocation);
	}
	
	/**
	 * 
	 */
	@Test
	public void shouldScheduleAppointment() { 		
		Context.getService(AppointmentService.class).getAppointment(1);
		
		Appointment appt = new Appointment();
		appt.setStartDatetime(new Date());
		appt.setEndDatetime(new Date());
		appt.setLocation(Context.getLocationService().getDefaultLocation());
		appt.setPatient(Context.getPatientService().getPatient(2));
		appt.setProvider(Context.getUserService().getUserByUsername("admin"));
		
		appt = Context.getService(AppointmentService.class).scheduleAppointment(appt);		
		
		Assert.assertNotNull("the appt should not be null", appt);
		Assert.assertNotNull("the appt id should not be null", appt.getId());
		
		Appointment sameAppt = Context.getService(AppointmentService.class).getAppointment(appt.getId());
		Assert.assertEquals("these appts should be the same", appt, sameAppt);		
	}
	
	
	@Test 
	public void shouldSaveAppointment() { 		
		Appointment appt = new Appointment();
		appt.setEndDatetime(new Date());
		appt.setStartDatetime(new Date());
		appt.setLocation(Context.getLocationService().getDefaultLocation());
		appt.setPatient(Context.getPatientService().getPatient(2));
		appt.setProvider(Context.getUserService().getUserByUsername("admin"));

		Appointment appt2 = 
			Context.getService(AppointmentService.class).scheduleAppointment(appt);
		Assert.assertNotNull("service should have given appt an id", appt2.getId());
		
		Appointment appt3 = Context.getService(AppointmentService.class).getAppointment(appt2.getId());
		Assert.assertEquals("appts should match", appt2, appt3);
	
	}
	
	@Test
	public void shouldGetAllAppointmentsForToday() { 
		
		Appointment appt = new Appointment();
		appt.setEndDatetime(new Date());
		appt.setStartDatetime(new Date());
		appt.setLocation(Context.getLocationService().getDefaultLocation());
		appt.setPatient(Context.getPatientService().getPatient(2));
		appt.setProvider(Context.getUserService().getUserByUsername("admin"));

		Appointment appt2 = 
			Context.getService(AppointmentService.class).scheduleAppointment(appt);
		Assert.assertNotNull("service should have given appt an id", appt2.getId());
		
		List<Appointment> appts = Context.getService(AppointmentService.class).getAppointments(new Date());
		Assert.assertNotNull("appts is null", appts);
		Assert.assertTrue("appts is empty", !appts.isEmpty());
		
		
	}
	
	
	
}
