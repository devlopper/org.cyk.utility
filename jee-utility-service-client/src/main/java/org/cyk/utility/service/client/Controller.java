package org.cyk.utility.service.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.rest.ResponseHelper;
import org.cyk.utility.service.FilterFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Controller {

	public static class AbstractImpl extends AbstractObject implements Controller,Serializable {
	
		@Inject
		protected SpecificServiceGetter specificServiceGetter;
		
		protected Response serve(Service service) {
			try {
				return service.execute();
			} catch (WebApplicationException exception) {
				throw new RuntimeException(ResponseHelper.getEntity(String.class, exception.getResponse()));
			}
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class GetArguments extends AbstractObject implements Serializable {
		private Filter.Dto filter;
		private String filterAsString;
		private FilterFormat filterFormat;
		private Boolean defaultable;
		private List<String> projections;
		private Boolean countable;
		private Boolean pageable;
		private Integer firstTupleIndex;
		private Integer numberOfTuples;
		private Boolean postable;
		private Boolean identifierBlankable;
		//private Boolean defaultIfIdentifierIsBlank;
		//private Listener listener;
		
		public String buildFilterAsString() {
			if(StringHelper.isBlank(filterAsString) && filter != null)
				filterAsString = JsonbBuilder.create().toJson(filter);
			return filterAsString;
		}
		
		public FilterFormat buildFilterFormat() {
			if(filter != null)
				return FilterFormat.JSON;
			if(StringHelper.isNotBlank(filterAsString))
				return FilterFormat.PLAIN;
			return null;
		}
		
		public List<String> getProjections(Boolean instantiateIfNull) {
			if(projections == null && Boolean.TRUE.equals(instantiateIfNull))
				projections = new ArrayList<>();
			return projections;
		}
		
		public GetArguments projections(Collection<String> projections) {
			if(CollectionHelper.isEmpty(projections))
				return this;
			getProjections(Boolean.TRUE).addAll(projections);
			return this;
		}
		
		public GetArguments projections(String...projections) {
			if(ArrayHelper.isEmpty(projections))
				return this;
			return projections(CollectionHelper.listOf(Boolean.TRUE,projections));
		}
		
		public void listenIdentifierIsBlank() {
			/*if(filter == null)
				filter = new Filter.Dto();
			filter.addField(SpecificPersistence.PARAMETER_NAME_DEFAULT_VALUE, Boolean.TRUE);*/
			throw new RuntimeException("Listen identifier is blank must be implemented");
		}
		
		/**/
		
		public static GetArguments process(Class<?> klass,GetArguments arguments) {
			if(klass == null)
				return arguments;
			if(Boolean.TRUE.equals(isProcessableByUser(arguments, klass)))
				arguments = addFilterField(arguments,PARAMETER_NAME_USER_NAME, PARAMETER_VALUE_USER_NAME == null ? SessionHelper.getUserName() : PARAMETER_VALUE_USER_NAME);
			return arguments;
		}
		
		public static GetArguments addFilterField(GetArguments arguments,String path,Object value) {
			if(StringHelper.isBlank(path) || ValueHelper.isBlank(value))
				return null;
			if(arguments == null)
				arguments = new GetArguments();
			if(arguments.getFilter() == null)
				arguments.setFilter(new Filter.Dto());
			arguments.getFilter().addField(path, value);
			return arguments;
		}
		
		protected static Boolean isProcessableByUser(GetArguments arguments,Class<?> klass) {
			if(LISTENER == null)
				return PROCESSABLE_BY_USER.contains(klass);
			return LISTENER.isProcessableByUser(arguments, klass);
		}
		
		/**/
		
		public static String PARAMETER_NAME_USER_NAME = "username";
		public static String PARAMETER_VALUE_USER_NAME;
		public static final Set<Class<?>> PROCESSABLE_BY_USER = new HashSet<>();
		
		/**/
		
		public static interface Listener {
			Boolean isProcessableByUser(GetArguments arguments,Class<?> klass);
			
			public abstract class AbstractImpl implements Listener {
				@Override
				public Boolean isProcessableByUser(GetArguments arguments, Class<?> klass) {
					return PROCESSABLE_BY_USER.contains(klass);
				}
			}
		}
		
		public static Listener LISTENER;
	}

	public static interface Service {
		Response execute();
	}
}