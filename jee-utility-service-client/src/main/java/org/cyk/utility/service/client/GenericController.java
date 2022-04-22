package org.cyk.utility.service.client;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.rest.ResponseHelper;
import org.cyk.utility.service.SpecificService;

public interface GenericController extends Controller {

	<T> Collection<T> get(Class<T> klass,GetArguments arguments);
	<T> Collection<T> get(Class<T> klass);
	<T> Collection<T> getByParentIdentifier(Class<T> klass,String name,String value,Integer firstTupleIndex,Integer numberOfTuples);
	<T> Collection<T> getByParentIdentifier(Class<T> klass,String name,String value,Boolean pageable);
	<T> Collection<T> getByParentIdentifier(Class<T> klass,String name,String value);
	
	<T> T getOne(Class<T> klass,GetArguments arguments);
	<T> T getByIdentifier(Class<T> klass,String identifier,GetArguments arguments);
	<T> T getByIdentifier(Class<T> klass,String identifier);
	
	<T> T getDefault(Class<T> klass,GetArguments arguments);
	<T> T getDefault(Class<T> klass);
	
	public static abstract class AbstractImpl extends Controller.AbstractImpl implements GenericController,Serializable{
		
		@Override
		public <T> Collection<T> get(Class<T> klass, GetArguments arguments) {
			if(klass == null)
				throw new RuntimeException("Class is required");
			SpecificService<?> service = specificServiceGetter.get(klass);
			Response response;
			Boolean postable = arguments != null && Boolean.TRUE.equals(arguments.getPostable());
			if(Boolean.TRUE.equals(postable))
				response = service.getUsingPost(
						arguments == null ? null : arguments.buildFilterAsString()
								,arguments == null ? null : arguments.buildFilterFormat()
								,arguments == null ? null : arguments.getDefaultable()
								,arguments == null ? null : arguments.getProjections()
								,arguments == null ? null : arguments.getCountable()
								,arguments == null ? null : arguments.getPageable()
								,arguments == null ? null : arguments.getFirstTupleIndex()
								,arguments == null ? null : arguments.getNumberOfTuples());
			else
				response = service.get(
						arguments == null ? null : arguments.buildFilterAsString()
								,arguments == null ? null : arguments.buildFilterFormat()
								,arguments == null ? null : arguments.getDefaultable()
								,arguments == null ? null : arguments.getProjections()
								,arguments == null ? null : arguments.getCountable()
								,arguments == null ? null : arguments.getPageable()
								,arguments == null ? null : arguments.getFirstTupleIndex()
								,arguments == null ? null : arguments.getNumberOfTuples());
			return ResponseHelper.getEntityAsListFromJson(klass,response);
		}
		
		@Override
		public <T> Collection<T> get(Class<T> klass) {
			return get(klass, new GetArguments().setPageable(Boolean.FALSE));
		}
		
		private <T> Collection<T> __getByParentIdentifier__(Class<T> klass, String name, String value,Boolean pageable,Integer firstTupleIndex,Integer numberOfTuples) {
			if(StringHelper.isBlank(value))
				return null;
			if(StringHelper.isBlank(name))
				throw new RuntimeException("Parent identifier parameter name is required");
			return get(klass, new GetArguments().setFilter(new Filter.Dto().addField(name,value)).setPageable(pageable).setFirstTupleIndex(firstTupleIndex).setNumberOfTuples(numberOfTuples));
		}
		
		@Override
		public <T> Collection<T> getByParentIdentifier(Class<T> klass, String name, String value,Integer firstTupleIndex,Integer numberOfTuples) {
			return __getByParentIdentifier__(klass, name, value, Boolean.TRUE, firstTupleIndex, numberOfTuples);
		}
		
		public <T> Collection<T> getByParentIdentifier(Class<T> klass, String name, String value,Boolean pageable) {
			return __getByParentIdentifier__(klass, name, value, pageable, null, null);
		}
		
		@Override
		public <T> Collection<T> getByParentIdentifier(Class<T> klass, String name, String value) {
			return getByParentIdentifier(klass, name, value, Boolean.FALSE);
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
			if(StringHelper.isNotBlank(identifier))
				return ResponseHelper.getEntityFromJson(klass,specificServiceGetter.get(klass).getByIdentifier(identifier , arguments == null ? null : arguments.getProjections()));
			if(arguments != null && Boolean.TRUE.equals(arguments.getIdentifierBlankable())) {
				arguments.listenIdentifierIsBlank();
				return getOne(klass, arguments);
			}
			return null;
		}
		
		@Override
		public <T> T getDefault(Class<T> klass, GetArguments arguments) {
			if(klass == null)
				throw new RuntimeException("Class is required");
			List<T> list = ResponseHelper.getEntityAsListFromJson(klass, specificServiceGetter.get(klass).get(null, null, Boolean.TRUE, arguments == null ? null : arguments.getProjections(), Boolean.FALSE, Boolean.TRUE, 0, 1));
			return CollectionHelper.getFirst(list);
		}
		
		@Override
		public <T> T getDefault(Class<T> klass) {
			return getDefault(klass, null);
		}
		
		@Override
		public <T> T getByIdentifier(Class<T> klass, String identifier) {
			return getByIdentifier(klass, identifier, null);
		}
	}
}