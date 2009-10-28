package org.openmrs.module.patientvisit.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Cohort;
import org.openmrs.Patient;
import org.openmrs.api.PatientSetService.TimeModifier;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jmiranda
 *
 */
@Controller
@RequestMapping("/module/patientvisit/patientVisitList")
public class PatientVisitListController {

	
	/**
	 * @should non empty get patient list 
	 * @should match class name
	 * @should forward to patientVisitList jsp
	 * 
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getPatientVisitList() { 		
		ModelAndView modelAndView = new ModelAndView();
		//modelAndView.setViewName("/module/patientvisit/patientVisitList");
	
		String value = 
			Context.getAdministrationService().getGlobalProperty("patientvisit.returnvisitdate.conceptId");		
		Integer questionId = Integer.parseInt(value);
		
		// Give me all patients who have ANY observation for question 5096 (RETURN VISIT DATE)
		Cohort cohort = 
			Context.getPatientSetService().getPatientsHavingObs(questionId, TimeModifier.ANY, null, null, null, null);
		
		// 
		List<Patient> patients = 
			Context.getPatientSetService().getPatients(cohort.getMemberIds());
		
		modelAndView.addObject("patientVisitList", patients);
		return modelAndView;
	}
	
}
