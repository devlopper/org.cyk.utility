package org.cyk.utility.representation.server;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiableSystemScalarImpl;
import org.cyk.utility.__kernel__.rest.ResponseBuilder;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.representation.Arguments;
import org.cyk.utility.representation.EntitySaver;

public abstract class AbstractEntitySaverImpl<T> extends EntitySaver.AbstractImpl<T> {

	protected abstract Class<T> getRepresentationEntityClass();
	
	protected org.cyk.utility.business.server.EntitySaver.Arguments<Object> instantiateBusinessEntitySaverArguments() {
		return new org.cyk.utility.business.server.EntitySaver.Arguments<Object>();
	}
	
	@SuppressWarnings("unchecked")
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
			org.cyk.utility.business.server.EntitySaver.Arguments<Object> businessEntitySaverArguments = instantiateBusinessEntitySaverArguments();
			businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setCreatables((Collection<Object>) __creatables__);
			businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setUpdatables((Collection<Object>) __updatables__);
			businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setDeletables((Collection<Object>) __deletables__);
			org.cyk.utility.business.server.EntitySaver.getInstance().save((Class<Object>)internal.persistenceEntityClass, businessEntitySaverArguments);
			return ResponseBuilder.getInstance().build(new ResponseBuilder.Arguments());
		} catch (Exception exception) {
			LogHelper.log(exception, getClass());
			return ResponseBuilder.getInstance().build(exception);
		}		
	}
	
	@SuppressWarnings("unchecked")
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
			org.cyk.utility.business.server.EntitySaver.Arguments<Object> businessEntitySaverArguments = instantiateBusinessEntitySaverArguments();
			businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setCreatables(creatables);
			businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setUpdatables(updatables);
			businessEntitySaverArguments.getPersistenceArguments(Boolean.TRUE).setDeletables(deletables);
			org.cyk.utility.business.server.EntitySaver.getInstance().save((Class<Object>)internal.persistenceEntityClass, businessEntitySaverArguments);
			return ResponseBuilder.getInstance().build(new ResponseBuilder.Arguments());
		} catch (Exception exception) {
			LogHelper.log(exception, getClass());
			return ResponseBuilder.getInstance().build(exception);
		}
	}
	
}
