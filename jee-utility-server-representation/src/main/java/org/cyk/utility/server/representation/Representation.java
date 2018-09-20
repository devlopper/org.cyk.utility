package org.cyk.utility.server.representation;

import java.util.Collection;

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
	
	Object getOne(@PathParam(PARAMETER_IDENTIFIER) String identifier,@QueryParam(PARAMETER_TYPE) String type);
	*/
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
