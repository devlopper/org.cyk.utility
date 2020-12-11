package org.cyk.utility.server.representation;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueUsageType;

/**
 * 
 * @author Christian
 *
 */
@Path(Representation.PATH)
//@Tag
public interface Representation extends RepresentationServiceProvider {

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
	
	@DELETE
	@Path(PATH_DELETE_ALL)
	//@Operation(description = TAG_DELETE,hidden = true)
	Response deleteAll();
	
	/**/
	
	@POST
	@Path("data/load")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	//@Operation(description = TAG_LOAD,hidden = true)
	Response loadData();
	
	/**/
	
	String PATH = "__service__";
}
