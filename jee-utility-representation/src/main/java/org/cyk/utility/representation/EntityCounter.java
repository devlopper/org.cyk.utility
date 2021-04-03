package org.cyk.utility.representation;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

@Path(EntityCounter.PATH)
//@Tag(name = EntityCounter.TAG)
public interface EntityCounter {

	@POST
	@Path(PATH_READ)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	//@Operation(description = "count")
	Response count(Arguments arguments);
	
	/**/
	
	public abstract static class AbstractImpl extends AbstractExecutionImpl implements EntityCounter,Serializable {
		
	}
	
	/**/
	
	static EntityCounter getInstance() {
		return Helper.getInstance(EntityCounter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	String TAG = "Generic Count Interface";
	
	String PATH = "/cyk/entity/counter";
	String PATH_READ = "count";
}