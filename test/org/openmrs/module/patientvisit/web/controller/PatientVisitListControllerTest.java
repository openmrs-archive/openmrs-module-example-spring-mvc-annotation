package org.openmrs.module.patientvisit.web.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.web.servlet.ModelAndView;

public class PatientVisitListControllerTest extends BaseModuleContextSensitiveTest {

	@Test 
	public void shouldGetNonEmptyPatientList() { 		

		ModelAndView mav = new PatientVisitListController().getPatientVisitList();
		List<Patient> patients = 
			(List<Patient>) mav.getModel().get("patientVisitList");		

		// Assert that patient list is not null
		Assert.assertNotNull("patient list is not null", patients);
		
		// Assert that the returned list of patients is empty
		Assert.assertTrue("patient list is not empty", !patients.isEmpty());
		Assert.assertEquals("patient list has 1 patients", 1, patients.size());
	}
	
	
	

	@Test 
	public void shouldForwardToPatientVisitListJsp() { 
		ModelAndView mav = new PatientVisitListController().getPatientVisitList();
		Assert.assertNotNull(mav.getView());		
	}

	
	@Test 
	public void shouldMatchClasses() { 

		Class clazz = new PatientVisitListController().getClass();
		
		Assert.assertEquals("Controller class is PatientVisitListController", 
				PatientVisitListController.class, clazz);
		
		
	}
	
	
}
