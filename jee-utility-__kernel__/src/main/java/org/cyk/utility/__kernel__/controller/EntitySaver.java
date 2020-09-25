package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.persistence.EntityLifeCycleListener;
import org.cyk.utility.__kernel__.session.SessionHelper;
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
			prepare(controllerEntityClass, arguments);
			Collection<?> creatables = CollectionHelper.isEmpty(arguments.getCreatables()) ? null : MappingHelper.getDestinations(arguments.getCreatables(), arguments.__representationEntityClass__);
			Collection<?> updatables = CollectionHelper.isEmpty(arguments.getUpdatables()) ? null : MappingHelper.getDestinations(arguments.getUpdatables(), arguments.__representationEntityClass__);
			Collection<?> deletables = CollectionHelper.isEmpty(arguments.getDeletables()) ? null : MappingHelper.getDestinations(arguments.getDeletables(), arguments.__representationEntityClass__);				
			
			String actionIdentifier = arguments.getRepresentationArguments() == null ? null : arguments.getRepresentationArguments().getActionIdentifier();
			setAuditableWhoDoneWhatWhen(creatables, actionIdentifier);
			setAuditableWhoDoneWhatWhen(updatables, actionIdentifier);
			setAuditableWhoDoneWhatWhen(deletables, actionIdentifier);
			
			Response response = save(arguments.__representation__, creatables,updatables,deletables,arguments.__representationArguments__);
			finalise(controllerEntityClass, arguments,response);
		}
		
		protected void setAuditableWhoDoneWhatWhen(Collection<?> collection,String functionality) {
			if(CollectionHelper.isEmpty(collection))
				return;
			String username = SessionHelper.getUserName();
			for(Object object : collection) {
				if(object == null)
					continue;
				EntityLifeCycleListener.AbstractImpl.setAuditableWhoDoneWhatWhenIfBlank(object, username, functionality, null, null);
			}
		}
		
		protected <T> void prepare(Class<T> controllerEntityClass, Arguments<T> arguments) {
			arguments.prepare(controllerEntityClass, org.cyk.utility.__kernel__.representation.EntitySaver.class);
		}
		
		protected <T> Response save(Object representation,Collection<?> creatables,Collection<?> updatables,Collection<?> deletables,org.cyk.utility.__kernel__.representation.Arguments arguments) {
			return ((org.cyk.utility.__kernel__.representation.EntitySaver<Object>)representation)
					.save((Collection<Object>)creatables,(Collection<Object>)updatables,(Collection<Object>)deletables,arguments);
		}
		
		protected <T> void finalise(Class<T> controllerEntityClass, Arguments<T> arguments,Response response) {
			arguments.finalise(response);
		}
	}
	
	/**/
	
	/**/
	
	static EntitySaver getInstance() {
		return Helper.getInstance(EntitySaver.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}