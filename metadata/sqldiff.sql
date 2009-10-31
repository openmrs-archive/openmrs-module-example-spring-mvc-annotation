<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE sqldiff PUBLIC "-//OpenMRS//DTD OpenMRS SQL Diff Config 1.0//EN" "http://resources.openmrs.org/doctype/sqldiff-1.0.dtd">

<sqldiff version="1.0">
	<help></help>
	
	<diff>
		<version>0.1</version>
		<author>OpenMRS</author>
		<date>Oct 30th 2009</date>
		<description>
			Create new table for our patient appointment scheduler.
		</description>
		<sql>
			DROP TABLE IF EXISTS appointment;
			CREATE TABLE appointment (
				id int(11) NOT NULL,
				start_datetime datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
				end_datetime datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
				patient_id int(11) NOT NULL,
				location_id int(11) NOT NULL DEFAULT '0',
				provider_id int(11) NOT NULL
			) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
		</sql>
	</diff>

	<diff>
		<version>0.2</version>
		<author>OpenMRS</author>
		<date>Oct 31th 2009</date>
		<description>
			Add a uuid column 
		</description>
		<sql>
			ALTER TABLE appointment ADD COLUMN uuid CHAR(38) NOT NULL;
		</sql>
	</diff>
		
	<diff>
		<version>0.3</version>
		<author>OpenMRS</author>
		<date>Oct 31th 2009</date>
		<description>
			Add a uuid column 
		</description>
		<sql>		
			ALTER TABLE appointment MODIFY COLUMN id INTEGER  NOT NULL AUTO_INCREMENT,
			ADD PRIMARY KEY (id); 
		</sql>
	</diff>
	
	
	
	
	
</sqldiff>
