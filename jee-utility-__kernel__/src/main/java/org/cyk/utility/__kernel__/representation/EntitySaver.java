package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.__kernel__.string.StringHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
public interface EntitySaver<T> {

	@POST
	@Path(PATH_SAVE)
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML })
	@ApiOperation(value = "save",tags = {"save"})
	Response save(Collection<T> creatables,Collection<T> updatables,Collection<T> deletables,Arguments arguments);
	
	/**/
	
	public abstract static class AbstractImpl<T> extends AbstractObject implements EntitySaver<T>,Serializable {
		
		protected abstract Class<T> getRepresentationEntityClass();
		
		protected org.cyk.utility.__kernel__.business.EntitySaver.Arguments<Object> instantiateBusinessEntitySaverArguments() {
			return new org.cyk.utility.__kernel__.business.EntitySaver.Arguments<Object>();
		}
		
		@Override
		public Response save(Collection<T> creatables,Collection<T> updatables,Collection<T> deletables,Arguments arguments) {
			if(CollectionHelper.isEmpty(creatables) && CollectionHelper.isEmpty(updatables) && CollectionHelper.isEmpty(deletables))
				return ResponseBuilder.getInstance().buildRuntimeException(null,"at least one object to create or update or delete is required");
			if(arguments != null) {
				if(StringHelper.isBlank(arguments.getRepresentationEntityClassName()))
					arguments.setRepresentationEntityClass(getRepresentationEntityClass());
			}
			Arguments.Internal internal;
			try {
				internal = new Arguments.Internal(arguments, EntitySaver.class);
				Collection<?> __creatables__ = CollectionHelper.isEmpty(creatables) ? null : MappingHelper.getDestinations(creatables, internal.persistenceEntityClass);
				Collection<?> __updatables__ = CollectionHelper.isEmpty(updatables) ? null : MappingHelper.getDestinations(updatables, internal.persistenceEntityClass);
				Collection<?> __deletables__ = CollectionHelper.isEmpty(deletables) ? null : MappingHelper.getDestinations(deletables, internal.persistenceEntityClass);		
				org.cyk.utility.__kernel__.business.EntitySaver.Arguments<Object> businessEntitySaverArguments = instantiateBusinessEntitySaverArguments();
				businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setCreatables((Collection<Object>) __creatables__);
				businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setUpdatables((Collection<Object>) __updatables__);
				businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setDeletables((Collection<Object>) __deletables__);
				org.cyk.utility.__kernel__.business.EntitySaver.getInstance().save((Class<Object>)internal.persistenceEntityClass, businessEntitySaverArguments);
				return ResponseBuilder.getInstance().build(new ResponseBuilder.Arguments());
			} catch (Exception exception) {
				LogHelper.log(exception, getClass());
				return ResponseBuilder.getInstance().build(exception);
			}		
		}
	}
	
	/**/
	
	String PATH_SAVE = "save";
}