package org.cyk.utility.server.representation;

import java.util.Collection;

import org.cyk.utility.string.Strings;
import org.cyk.utility.value.ValueUsageType;

/**
 * 
 * @author Christian
 *
 */
public interface Representation extends RepresentationServiceProvider<Object,Object> {

	/* Create */
	
	Representation create(Object object);
	
	Representation createMany(Collection<Object> objects);
	
	/* Read */ 
	/*
	Object getMany();
	*/
	Object getOne(Class<?> aClass,Object identifier,ValueUsageType valueUsageType,Strings fieldNames);
	
	/* Update */
	/*
	Representation updateOne(Object object);
	
	Representation updateMany(Collection<Object> objects);
	*/
	/* Delete */
	
	Representation delete(Object object);
	
	Representation deleteMany(Collection<Object> objects);
	
	Representation deleteAll();
	
	/* Count */
	
	//Long count();
}
