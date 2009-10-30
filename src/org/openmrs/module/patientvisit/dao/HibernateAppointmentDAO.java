package org.openmrs.module.patientvisit.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.patientvisit.model.Appointment;
import org.openmrs.util.OpenmrsConstants;

public class HibernateAppointmentDAO implements AppointmentDAO {

protected final Log log = LogFactory.getLog(getClass());
	
	private SessionFactory sessionFactory;	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Appointment getAppointment(Integer id) throws DAOException { 
		return (Appointment) sessionFactory.getCurrentSession().get(Appointment.class, id);		
	}
		
	public Appointment saveAppointment(Appointment appointment) throws DAOException { 	
		sessionFactory.getCurrentSession().saveOrUpdate(appointment);
		return appointment;
	}

	public void purgeappointment(Appointment appointment) throws DAOException { 
		sessionFactory.getCurrentSession().delete(appointment);
	}
	
	public List<Appointment> getAppointments(Date date) throws DAOException { 
		//sessionFactory.getCurrentSession().
		return new ArrayList<Appointment>();
		
	}
	
	
	
}
