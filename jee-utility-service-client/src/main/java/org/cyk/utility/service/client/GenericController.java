package org.cyk.utility.service.client;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.rest.ResponseHelper;

public interface GenericController extends Controller {

	<T> Collection<T> get(Class<T> klass,GetArguments arguments);
	<T> Collection<T> get(Class<T> klass);
	<T> Collection<T> getByParentIdentifier(Class<T> klass,String name,String value);
	
	<T> T getOne(Class<T> klass,GetArguments arguments);
	<T> T getByIdentifier(Class<T> klass,String identifier,GetArguments arguments);
	<T> T getByIdentifier(Class<T> klass,String identifier);
	
	public static abstract class AbstractImpl extends Controller.AbstractImpl implements GenericController,Serializable{
		
		@Override
		public <T> Collection<T> get(Class<T> klass, GetArguments arguments) {
			if(klass == null)
				throw new RuntimeException("Class is required");
			return ResponseHelper.getEntityAsListFromJson(klass,specificServiceGetter.get(klass).get(
					arguments == null ? null : arguments.buildFilterAsString()
					,arguments == null ? null : arguments.buildFilterFormat()
					,arguments == null ? null : arguments.getProjections()
					,arguments == null ? null : arguments.getCountable()
					,arguments == null ? null : arguments.getPageable()
					,arguments == null ? null : arguments.getFirstTupleIndex()
					,arguments == null ? null : arguments.getNumberOfTuples()));
		}
		
		@Override
		public <T> Collection<T> get(Class<T> klass) {
			return get(klass, null);
		}
		
		@Override
		public <T> Collection<T> getByParentIdentifier(Class<T> klass, String name, String value) {
			if(StringHelper.isBlank(value))
				return null;
			if(StringHelper.isBlank(name))
				throw new RuntimeException("Parent identifier parameter name is required");
			return get(klass, new GetArguments().setFilter(new Filter.Dto().addField(name,value)));
		}
		
		@Override
		public <T> T getOne(Class<T> klass, GetArguments arguments) {
			if(klass == null)
				throw new RuntimeException("Class is required");
			return CollectionHelper.getFirst(get(klass, arguments));
		}
		
		@Override
		public <T> T getByIdentifier(Class<T> klass, String identifier, GetArguments arguments) {
			if(klass == null)
				throw new RuntimeException("Class is required");
			if(StringHelper.isBlank(identifier))
				return null;
			return ResponseHelper.getEntityFromJson(klass,specificServiceGetter.get(klass).getByIdentifier(identifier , arguments == null ? null : arguments.getProjections()));
		}
		
		@Override
		public <T> T getByIdentifier(Class<T> klass, String identifier) {
			return getByIdentifier(klass, identifier, null);
		}
	}
}