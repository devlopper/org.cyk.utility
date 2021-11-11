package org.cyk.utility.service.client;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

public interface SpecificController<T> extends Controller {

	Collection<T> get(GetArguments arguments);
	Collection<T> get();
	Collection<T> getByParentIdentifier(String name,String value);
	
	T getByIdentifier(String identifier,GetArguments arguments);
	
	public static abstract class AbstractImpl<T> extends Controller.AbstractImpl implements SpecificController<T>,Serializable {
	
		@Inject
		protected GenericController genericController;
		
		@Override
		public Collection<T> get(GetArguments arguments) {
			return genericController.get(getEntityClass(),arguments);
		}
		
		@Override
		public Collection<T> get() {
			return genericController.get(getEntityClass(),null);
		}
		
		@Override
		public T getByIdentifier(String identifier, GetArguments arguments) {
			return genericController.getByIdentifier(getEntityClass(), identifier, arguments);
		}
		
		@Override
		public Collection<T> getByParentIdentifier(String name,String value) {
			return genericController.getByParentIdentifier(getEntityClass(), name, value);
		}
	
		protected abstract Class<T> getEntityClass();
	}
}