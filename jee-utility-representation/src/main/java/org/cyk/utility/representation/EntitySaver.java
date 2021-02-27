package org.cyk.utility.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarImpl;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.__kernel__.string.StringHelper;

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
		
		@Override
		public Response save(Collection<T> representations,Arguments arguments) {
			if(CollectionHelper.isEmpty(representations))
				return ResponseBuilder.getInstance().buildRuntimeException(null,"at least one object to save");
			if(arguments == null)
				return ResponseBuilder.getInstance().buildRuntimeException(null,"arguments are required");			
			if(StringHelper.isBlank(arguments.getRepresentationEntityClassName()))
				arguments.setRepresentationEntityClass(getRepresentationEntityClass());			
			Arguments.Internal internal;
			try {
				internal = new Arguments.Internal(arguments, EntitySaver.class);
				final Collection<Object> creatables = new ArrayList<>();
				final Collection<Object> updatables = new ArrayList<>();
				final Collection<Object> deletables = new ArrayList<>();		
				for(T representation : representations) {
					Object persistable = MappingHelper.getDestination(representation, internal.persistenceEntityClass);
					Object identifier = FieldHelper.readSystemIdentifier(persistable);
					if(identifier == null || StringHelper.isBlank(identifier.toString()))
						creatables.add(persistable);
					else if(Boolean.TRUE.equals(((AbstractIdentifiableSystemScalarImpl<?>)representation).get__deletable__()))
						deletables.add(persistable);
					else
						updatables.add(persistable);
				}				
				org.cyk.utility.__kernel__.business.EntitySaver.Arguments<Object> businessEntitySaverArguments = instantiateBusinessEntitySaverArguments();
				businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setCreatables(creatables);
				businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setUpdatables(updatables);
				businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setDeletables(deletables);
				org.cyk.utility.__kernel__.business.EntitySaver.getInstance().save((Class<Object>)internal.persistenceEntityClass, businessEntitySaverArguments);
				return ResponseBuilder.getInstance().build(new ResponseBuilder.Arguments());
			} catch (Exception exception) {
				LogHelper.log(exception, getClass());
				return ResponseBuilder.getInstance().build(exception);
			}
		}
	}
	
	/**/
	
	String PATH_SAVE_CREATABLES_UPDATABLES_DELETABLES = "save/v1";
	String PATH_SAVE_REPRESENTATIONS = "save/v2";
}