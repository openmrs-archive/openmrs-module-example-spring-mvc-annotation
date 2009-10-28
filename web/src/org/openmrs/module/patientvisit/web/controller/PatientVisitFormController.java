package org.openmrs.module.patientvisit.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/module/patientvisit/patientVisit")
public class PatientVisitFormController {

	/* Logger */
	private static Log log = LogFactory.getLog(PatientVisitFormController.class);
		
    @InitBinder
    public void initBinder(WebDataBinder binder) { 
    	binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), false)); 
    }    
	

	@RequestMapping(method=RequestMethod.GET)
	public void populateForm(ModelMap map) { 
		List<Date> dates = new ArrayList<Date>();
		List<Location> locations = Context.getLocationService().getAllLocations();
		map.put("dates", dates);
		map.put("locations", locations);
	}

	
	/**
	 * @should set gender to male
	 * @should return non null patient
	 * @should return patient with patient id 
	 * @param map
	 */
	@ModelAttribute("patient")
	public Patient formBackingObject(
			@RequestParam(value = "patientId", required = false) Integer patientId) { 
		Patient patient = Context.getPatientService().getPatient(patientId);
		log.error("Patient: " + patient);

		/*
		if (patient == null) { 
			patient = new Patient();
			patient.setGender("male");
		}*/
		
		
		return patient;
	}

	
	/**
	 * @should save observation for visit date
	 * @param patient
	 * @param visitDate
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processForm(
			@ModelAttribute("patient") Patient patient,
			@RequestParam("visitDate") Date visitDate, 
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		//Integer patientId = Integer.parseInt(request.getParameter("patientId"));		
		//Patient patient = 
		
		
		Obs obs = new Obs(patient, new Concept(5096), new Date(), new Location(1));
		obs.setPerson(patient);
		obs.setValueDatetime(visitDate);		
		obs = Context.getObsService().saveObs(obs, "because");
		model.addObject("obs", obs);
		
		return model;
	}

	
	
}
