package org.cyk.utility.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.object.AbstractObject;

//@Tag
public interface EntitySaver<T> {

	@POST
	@Path(PATH_SAVE_CREATABLES_UPDATABLES_DELETABLES)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	//@Operation(description = "save")
	Response save(Collection<T> creatables,Collection<T> updatables,Collection<T> deletables,Arguments arguments);
	
	@POST
	@Path(PATH_SAVE_REPRESENTATIONS)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	//@Operation(description = "save")
	Response save(Collection<T> representations,Arguments arguments);
	
	/**/
	
	public abstract static class AbstractImpl<T> extends AbstractObject implements EntitySaver<T>,Serializable {
		
	}
	
	/**/
	
	String PATH_SAVE_CREATABLES_UPDATABLES_DELETABLES = "save/v1";
	String PATH_SAVE_REPRESENTATIONS = "save/v2";
}