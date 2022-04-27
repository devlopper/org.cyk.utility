package org.cyk.utility.service.client;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

public interface SpecificController<T> extends Controller {

	Collection<T> get(GetArguments arguments);
	Collection<T> get();
	Collection<T> getByParentIdentifier(String name,String value,Integer firstTupleIndex,Integer numberOfTuples);
	Collection<T> getByParentIdentifier(String name,String value,Boolean pageable);
	Collection<T> getByParentIdentifier(String name,String value);
	
	Collection<T> getDefaults(GetArguments arguments);
	Collection<T> getDefaults();
	
	T getOne(GetArguments arguments);
	T getByIdentifier(String identifier,GetArguments arguments);
	T getByIdentifier(String identifier);
	
	T getDefault(GetArguments arguments);
	T getDefault();
	
	T getByIdentifierOrDefaultIfIdentifierIsBlank(String identifier,GetArguments arguments);
	T getByIdentifierOrDefaultIfIdentifierIsBlank(String identifier);
	
	public static abstract class AbstractImpl<T> extends Controller.AbstractImpl implements SpecificController<T>,Serializable {
	
		@Inject protected GenericController genericController;
		
		@Override
		public Collection<T> get(GetArguments arguments) {
			return genericController.get(getEntityClass(),arguments);
		}
		
		@Override
		public Collection<T> get() {
			return genericController.get(getEntityClass());
		}
		
		@Override
		public T getOne(GetArguments arguments) {
			return genericController.getOne(getEntityClass(), arguments);
		}
		
		@Override
		public T getByIdentifier(String identifier, GetArguments arguments) {
			return genericController.getByIdentifier(getEntityClass(), identifier, arguments);
		}
		
		@Override
		public T getByIdentifier(String identifier) {
			return genericController.getByIdentifier(getEntityClass(), identifier);
		}
		
		@Override
		public T getDefault(GetArguments arguments) {
			return genericController.getDefault(getEntityClass(), arguments);
		}
		
		@Override
		public T getDefault() {
			return genericController.getDefault(getEntityClass());
		}
		
		@Override
		public Collection<T> getByParentIdentifier(String name,String value) {
			return genericController.getByParentIdentifier(getEntityClass(), name, value);
		}
		
		@Override
		public Collection<T> getByParentIdentifier(String name, String value, Integer firstTupleIndex,Integer numberOfTuples) {
			return genericController.getByParentIdentifier(getEntityClass(),name, value, firstTupleIndex, numberOfTuples);
		}
		
		@Override
		public Collection<T> getByParentIdentifier(String name, String value, Boolean pageable) {
			return genericController.getByParentIdentifier(getEntityClass(),name, value, pageable);
		}
		
		@Override
		public Collection<T> getDefaults(GetArguments arguments) {
			return genericController.getDefaults(getEntityClass(), arguments);
		}
		
		@Override
		public Collection<T> getDefaults() {
			return genericController.getDefaults(getEntityClass());
		}
	
		@Override
		public T getByIdentifierOrDefaultIfIdentifierIsBlank(String identifier, GetArguments arguments) {
			return genericController.getByIdentifierOrDefaultIfIdentifierIsBlank(getEntityClass(), identifier, arguments);
		}
		
		@Override
		public T getByIdentifierOrDefaultIfIdentifierIsBlank(String identifier) {
			return genericController.getByIdentifierOrDefaultIfIdentifierIsBlank(getEntityClass(), identifier);
		}
		
		protected abstract Class<T> getEntityClass();
	}
}