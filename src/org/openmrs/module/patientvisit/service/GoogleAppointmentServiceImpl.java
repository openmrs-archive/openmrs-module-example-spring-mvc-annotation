package org.openmrs.module.patientvisit.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.google.gdata.client.*;
import com.google.gdata.client.calendar.*;
import com.google.gdata.data.*;
import com.google.gdata.data.acl.*;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientvisit.model.Appointment;
import org.openmrs.module.patientvisit.util.DateUtil;

public class GoogleAppointmentServiceImpl extends BaseOpenmrsService implements
		AppointmentService {

	private CalendarService service = null;

	private String username = "";
	private String password = "";
	private String privateFeedUrl = "";

	public GoogleAppointmentServiceImpl() { 		
		service = new CalendarService("openmrs-AppointmentModule-0.1");
	    try {	    	
	    	
	    	// TODO Read from classpath 
	    	Properties properties = new Properties();	    	
	    	properties.load(new FileInputStream(new File("metadata/gdata.properties")));
	    	username = properties.getProperty("gdata.username");
	    	password = properties.getProperty("gdata.password");
	    	privateFeedUrl = properties.getProperty("gdata.privateFeedUrl");
	    	service.setUserCredentials(username, password);
	    	
	    } catch (AuthenticationException e) {
	    	e.printStackTrace();
	    } catch (IOException e) { 
	    	e.printStackTrace();
	    }
	}
	 	
	public Appointment getAppointment(Integer id) {
		throw new APIException("Method not implemented for Google Appointment Service");		
	}

	public Appointment getAppointmentByUuid(String uuid) {
		try { 
			//URL calendarUrl = new URL(publicFeedUrl);
			URL calendarUrl = new URL(privateFeedUrl);
			CalendarQuery calendarQuery = new CalendarQuery(calendarUrl);	
			CalendarEventFeed resultFeed = service.query(calendarQuery, CalendarEventFeed.class);			
			return toAppointment(resultFeed.getEntries().get(0));
			
		} catch (MalformedURLException e) { 
			e.printStackTrace();			
		} catch (ServiceException e) { 
			e.printStackTrace();			
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return new Appointment();
	}
	
	
	public List<Patient> getPatientsWithAppointmentsOnDate(Date date) { 
		throw new APIException("Not implemented yet");
	}
	
	public List<Appointment> getAppointments(Date date) {

		List<Appointment> appointments = new LinkedList<Appointment>();
		try { 
			//URL calendarUrl = new URL(publicFeedUrl);
			URL calendarUrl = new URL(privateFeedUrl);
			CalendarQuery calendarQuery = new CalendarQuery(calendarUrl);
			
			calendarQuery.setMinimumStartTime(new DateTime(DateUtil.getStartOfDay(date)));
			calendarQuery.setMaximumStartTime(new DateTime(DateUtil.getEndOfDay(date)));
		
			// Send the request and receive the response:
			CalendarEventFeed resultFeed = service.query(calendarQuery, CalendarEventFeed.class);
			for(CalendarEventEntry entry : resultFeed.getEntries()) { 
				appointments.add(toAppointment(entry));
			}
			
		} catch (MalformedURLException e) { 
			e.printStackTrace();			
		} catch (ServiceException e) { 
			e.printStackTrace();			
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return appointments;

	}

	public void removeAppointment(Appointment appt) {


	}

	public Appointment scheduleAppointment(Appointment appointment) {	
		try {
			URL postUrl = new URL(privateFeedUrl);	
			CalendarEventEntry myEntry = toCalendarEventEntry(appointment);
			CalendarEventEntry insertedEntry = service.insert(postUrl, myEntry);			
			return toAppointment(insertedEntry);
		} catch (MalformedURLException e) { 
			e.printStackTrace();
		} catch (ServiceException e) { 
			e.printStackTrace();			
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return new Appointment();
	}

	
	private CalendarEventEntry toCalendarEventEntry(Appointment appointment) { 
		
		CalendarEventEntry newCalendarEntry = new CalendarEventEntry();
		newCalendarEntry.setTitle(new PlainTextConstruct(
				appointment.getPatient().getGivenName() + " " + appointment.getPatient().getFamilyName()));		
		newCalendarEntry.setContent(new PlainTextConstruct("Appointment with" +
				appointment.getPatient().getGivenName() + " " + appointment.getPatient().getFamilyName() + " " + 
				appointment.getLocation().getName() + " with Dr. " + 
				appointment.getProvider().getFamilyName()));

		// Time
		When eventTimes = new When();
		eventTimes.setStartTime(new DateTime(appointment.getStartDatetime()));
		eventTimes.setEndTime(new DateTime(appointment.getEndDatetime()));
		newCalendarEntry.addTime(eventTimes);

		// Set patient, location, and provider
		newCalendarEntry.addExtension(toExtendedProperty("patient", appointment.getPatient().getUuid()));		
		newCalendarEntry.addExtension(toExtendedProperty("location", appointment.getLocation().getUuid()));
		newCalendarEntry.addExtension(toExtendedProperty("provider", appointment.getProvider().getUuid()));
		
		return newCalendarEntry;
	}
	
	private Appointment toAppointment(CalendarEventEntry calendarEventEntry) { 
		Appointment appointment = new Appointment();
		
		appointment.setUuid(calendarEventEntry.getId());
		
		if (calendarEventEntry.getTimes() != null) {			
			When when = calendarEventEntry.getTimes().get(0);
			appointment.setStartDatetime(new Date(when.getStartTime().getValue()));
			appointment.setEndDatetime(new Date(when.getEndTime().getValue()));
		}
		appointment.setLocation(
				Context.getLocationService().getLocationByUuid(getExtendedProperty(calendarEventEntry, "location").getValue()));
		appointment.setPatient(
				Context.getPatientService().getPatientByUuid(getExtendedProperty(calendarEventEntry, "patient").getValue()));
		appointment.setProvider(
				Context.getUserService().getUserByUuid(getExtendedProperty(calendarEventEntry, "provider").getValue()));
		return appointment;
	}
	
	
	public CalendarFeed getCalendarFeed(String calendarFeedUrl) { 
		CalendarFeed calendarFeed = null;
	    try {
	    	calendarFeed = service.getFeed(new URL(calendarFeedUrl), CalendarFeed.class);
	    } catch(ServiceException e) { 
	    	e.printStackTrace();
	    } catch(IOException e) { 
	    	e.printStackTrace();
	    }
	    return calendarFeed;
	}
	
	
	
	
	private boolean allowWriteAccess(String feedUrl) { 		
		try { 
	    	Properties properties = new Properties();
	    	properties.load(new FileInputStream(new File("gdata.properties")));
	    	String username = properties.getProperty("gdata.username");
	    	String password = properties.getProperty("gdata.password");
	    	CalendarService service = new CalendarService("openmrs-AppointmentModule-0.1");
	    	if (service != null) { 
		    	service.setUserCredentials(username, password);		
		    	service.getFeed(new URL(feedUrl), CalendarFeed.class);
		    	return true;
	    	}
		} catch (AuthenticationException e) { 
			/* returns false below */
		} catch (IOException e) { 
			/* returns false below */			
		} catch (Exception e) { 
			/* returns false below */			
		}
		return false;
	}
	
	
	private boolean allowReadAccess(String feedUrl) { 
		try { 
			CalendarService service = new CalendarService("openmrs-AppointmentModule-0.1");
			if (service != null) { 
		    	service.getFeed(new URL(feedUrl), CalendarFeed.class);
		    	return true;
			}
		} catch (AuthenticationException e) { 
			/* returns false below */
		} catch (IOException e) { 
			/* returns false below */			
		} catch (Exception e) { 
			/* returns false below */			
		}
		return false;
	}	

	
	public void printCalendarEvents() { 

		//CalendarFeed calendarFeed = getCalendarFeed(publicFeedUrl);
		CalendarFeed calendarFeed = getCalendarFeed(privateFeedUrl);
		// Print the title of each calendar
	    for (int i = 0; i < calendarFeed.getEntries().size(); i++) {
	    	CalendarEntry entry = calendarFeed.getEntries().get(i);
	    	System.out.println("\t" + 
	    			entry.getId() + " = \n" +
	    			entry.getTitle().getPlainText() + ", " + 
	    			entry.getLocations() + ", " +
	    			entry.getCanEdit() + ", " 	    			
	    	);
	    }
	}
	
	
	
	private ExtendedProperty toExtendedProperty(String name, String value) { 
		ExtendedProperty property = new ExtendedProperty();
		property.setName(name);
		property.setValue(value);
		return property;
	}
	
	
	private ExtendedProperty getExtendedProperty(CalendarEventEntry calendarEventEntry, String name) { 
		List<ExtendedProperty> properties = calendarEventEntry.getExtendedProperty();
		for(ExtendedProperty property : properties) { 
			if (property.getName().equals(name)) { 
				return property;
			}
		}
		return new ExtendedProperty();
	}
	
	
}
