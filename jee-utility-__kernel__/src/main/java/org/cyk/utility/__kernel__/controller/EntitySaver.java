package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface EntitySaver {

	<T> void save(Class<T> controllerEntityClass,Arguments<T> arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements EntitySaver,Serializable {
		
		@Override
		public <T> void save(Class<T> controllerEntityClass, Arguments<T> arguments) {
			if(controllerEntityClass == null)
				throw new RuntimeException("controller entity class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			arguments.prepare(controllerEntityClass, org.cyk.utility.__kernel__.representation.EntitySaver.class);			
			Collection<?> creatables = CollectionHelper.isEmpty(arguments.getCreatables()) ? null : MappingHelper.getDestinations(arguments.getCreatables(), arguments.__representationEntityClass__);
			Collection<?> updatables = CollectionHelper.isEmpty(arguments.getUpdatables()) ? null : MappingHelper.getDestinations(arguments.getUpdatables(), arguments.__representationEntityClass__);
			Collection<?> deletables = CollectionHelper.isEmpty(arguments.getDeletables()) ? null : MappingHelper.getDestinations(arguments.getDeletables(), arguments.__representationEntityClass__);				
			Response response = ((org.cyk.utility.__kernel__.representation.EntitySaver<Object>)arguments.__representation__)
					.save((Collection<Object>)creatables,(Collection<Object>)updatables,(Collection<Object>)deletables,arguments.__representationArguments__);
			arguments.finalise(response);
		}
	}
	
	/**/
	
	/**/
	
	static EntitySaver getInstance() {
		return Helper.getInstance(EntitySaver.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}