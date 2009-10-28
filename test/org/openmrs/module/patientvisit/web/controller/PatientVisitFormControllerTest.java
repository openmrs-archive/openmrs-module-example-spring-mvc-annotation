package org.openmrs.module.patientvisit.web.controller;


import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.TestUtil;
import org.openmrs.test.Verifies;
import org.springframework.web.servlet.ModelAndView;

public class PatientVisitFormControllerTest extends BaseModuleContextSensitiveTest {
	
	/**
	 * @see {@link PatientVisitFormController#formBackingObject()}
	 */
	@Test
	@Verifies(value = "should set gender to male", method = "formBackingObject()")
	public void formBackingObject_shouldSetGenderToMale() throws Exception {
		Patient patient = new PatientVisitFormController().formBackingObject(0);
		Assert.assertEquals("should be male, dummy", "male", patient.getGender());
	}

	/**
	 * @see {@link PatientVisitFormController#formBackingObject()}
	 * 
	 */
	@Test
	@Verifies(value = "should return non null patient", method = "formBackingObject()")
	public void formBackingObject_shouldReturnNonNullPatient() throws Exception {
		Patient patient = new PatientVisitFormController().formBackingObject(0);
		Assert.assertNotNull("should not be null, turkey", patient);
	}

	/**
	 * @see {@link PatientVisitFormController#processForm(Patient,Date)}
	 * 
	 */
	@Test
	@Verifies(value = "should save observation for visit date", method = "processForm(Patient,Date)")
	public void processForm_shouldSaveObservationForVisitDate()
			throws Exception {
		PatientVisitFormController pvfc = new PatientVisitFormController();
		Patient patient = pvfc.formBackingObject(2);
		Assert.assertEquals("patient should be 2", 2, patient.getPatientId().intValue());
		
		ModelAndView model = pvfc.processForm(patient, new Date(), null);

		
		//Patient patient2 = Context.getPatientService().getPatient(15875);
		//List<Obs> obs = Context.getObsService().getObs
		Obs obs = (Obs) model.getModelMap().get("obs");
		Assert.assertNotNull("obs should not be null", obs);
		Assert.assertNotNull("obs id should not be null", obs.getObsId());
		Assert.assertNotNull("obs date time should not be null",obs.getObsDatetime());
		
	}

	/**
	 * @see {@link PatientVisitFormController#formBackingObject(Integer)}
	 * 
	 */
	@Test
	@Verifies(value = "should return patient with patient id", method = "formBackingObject(Integer)")
	public void formBackingObject_shouldReturnPatientWithPatientId()
			throws Exception {

		TestUtil.printOutTableContents(getConnection(), "patient");
		TestUtil.printOutTableContents(getConnection(), "concept");
		
		Patient patient = new PatientVisitFormController().formBackingObject(2);
		
		Assert.assertEquals("patient id should equal 2", new Integer(2), patient.getPatientId());
	}
}