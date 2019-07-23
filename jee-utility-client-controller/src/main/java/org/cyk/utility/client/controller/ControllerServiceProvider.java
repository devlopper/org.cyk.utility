package org.cyk.utility.client.controller;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.SystemServiceProvider;

public interface ControllerServiceProvider<OBJECT> extends SystemServiceProvider {

	/* Create */
	ControllerServiceProvider<OBJECT> create(OBJECT object,Properties properties);
	ControllerServiceProvider<OBJECT> create(OBJECT object);
	/*
	ControllerServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties);
	ControllerServiceProvider<OBJECT> createMany(Collection<OBJECT> objects);
	*/
	/* Read */ 
	
	/* Update */
	
	ControllerServiceProvider<OBJECT> update(OBJECT object,Properties properties);
	ControllerServiceProvider<OBJECT> update(OBJECT object);
	/*
	ControllerServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects,Properties properties);
	ControllerServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects);
	*/
	/* Delete */
	
	ControllerServiceProvider<OBJECT> delete(OBJECT object,Properties properties);
	ControllerServiceProvider<OBJECT> delete(OBJECT object);
	/*
	ControllerServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects,Properties properties);
	ControllerServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects);
	*/
	ControllerServiceProvider<OBJECT> deleteAll(Properties properties);
	ControllerServiceProvider<OBJECT> deleteAll();
	
	/* Count */
	
	/* Process */
	ControllerServiceProvider<OBJECT> process(OBJECT object,Properties properties);
	ControllerServiceProvider<OBJECT> process(OBJECT object);
	
	
	
	/**/

}
