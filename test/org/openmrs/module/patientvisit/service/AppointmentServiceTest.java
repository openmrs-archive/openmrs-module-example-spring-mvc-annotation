package org.openmrs.module.patientvisit.service;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.patientvisit.model.Appointment;
import org.openmrs.test.BaseModuleContextSensitiveTest;


public class AppointmentServiceTest extends BaseModuleContextSensitiveTest {

	/**
	 * 
	 */
	@Test
	public void shouldScheduleAppointment() { 
		Appointment appt = new Appointment();
		appt = Context.getService(AppointmentService.class).scheduleAppointment(appt);		
		
		Assert.assertNotNull("the appt should not be null", appt);
		Assert.assertNotNull("the appt id should not be null", appt.getId());
		
		Appointment sameAppt = Context.getService(AppointmentService.class).getAppointment(appt.getId());
		Assert.assertEquals("these appts should be the same", appt, sameAppt);		
	}
	
	
}
