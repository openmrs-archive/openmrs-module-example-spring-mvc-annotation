package org.openmrs.module.patientvisit.extension.html;

import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.PatientDashboardTabExt;


public class AppointmentPatientDashboardTabExt extends PatientDashboardTabExt{

	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	@Override
	public String getTabName() {
		return "patientvisit.patientDashboard.forms";
	}
	
	@Override
	public String getRequiredPrivilege() {
		return "Patient Dashboard - View Appointments";
	}
	
	@Override
	public String getTabId() {
		return "patientAppointmentSelect";
	}
	
	@Override
	public String getPortletUrl() {
		return "patientAppointmentSelect";
	}
}
