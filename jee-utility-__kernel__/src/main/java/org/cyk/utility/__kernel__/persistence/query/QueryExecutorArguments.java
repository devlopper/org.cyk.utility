package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class QueryExecutorArguments extends AbstractObject implements Serializable {
	private Query query;
	private Map<Object,Object> parameters;
	private PropertiesArguments properties;
	private Map<String,Object> hints;
	private Boolean isResultCachable;
	private Boolean isResultProcessable;
	private Filter filter;
	private Integer firstTupleIndex;
	private Integer numberOfTuples;
	private Collection<Object> objects;
	private Collection<Object> systemIdentifiers;
	private Boolean isTransactional;
	private EntityManager entityManager;
	private Boolean isEntityManagerClearable;
	private Boolean isEntityManagerClosable;
	
	private EntityManager __entityManager__;
	private Map<String,Object> __hints__;
	private Boolean __isEntityManagerClearable__;
	private Boolean __isEntityManagerClosable__;
	private Collection<?> __objects__;
	
	public QueryExecutorArguments prepare() {
		__isEntityManagerClearable__ = isEntityManagerClearable;
		__isEntityManagerClosable__ = isEntityManagerClosable;		
		__entityManager__ = entityManager;
		if(__entityManager__ == null) {
			__entityManager__ = EntityManagerGetter.getInstance().get();
			if(__isEntityManagerClearable__ == null)
				__isEntityManagerClearable__ = Boolean.TRUE;
			if(__isEntityManagerClosable__ == null)
				__isEntityManagerClosable__ = Boolean.TRUE;		
		}
		
		__hints__ = new HashMap<>();
		if(MapHelper.isNotEmpty(hints))
			__hints__.putAll(hints);
		
		
		if(Boolean.TRUE.equals(__isEntityManagerClearable__) || Boolean.TRUE.equals(__isEntityManagerClosable__)) {
			__hints__.put("org.hibernate.readOnly",Boolean.TRUE);
		}
		
		if(Boolean.TRUE.equals(isResultCachable)) {
			__hints__.put("org.hibernate.cacheable", Boolean.TRUE);
		}
		return this;
	}
	
	public QueryExecutorArguments finalise() {
		if(__entityManager__ == null)
			return this;
		if(Boolean.TRUE.equals(__isEntityManagerClearable__))
			__entityManager__.clear();
		if(Boolean.TRUE.equals(__isEntityManagerClosable__))
			__entityManager__.close();
		if(Boolean.TRUE.equals(isResultProcessable) && CollectionHelper.isNotEmpty(__objects__))
			QueryResultProcessor.getInstance().process((Class<Object>)__objects__.iterator().next().getClass(), (Collection<Object>)__objects__, this);
		return this;
	}
	
	public Map<Object,Object> getParameters(Boolean injectIfNull) {
		if(parameters == null && Boolean.TRUE.equals(injectIfNull))
			parameters = new HashMap<>();
		return parameters;
	}
	
	public QueryExecutorArguments setParameters(Object...objects) {
		MapHelper.set(getParameters(Boolean.TRUE), objects);
		return this;
	}
	
	public QueryExecutorArguments addParameters(Object...objects) {
		MapHelper.set(getParameters(Boolean.TRUE), objects);
		return this;
	}
	
	public Filter getFilter(Boolean injectIfNull) {
		if(filter == null && Boolean.TRUE.equals(injectIfNull))
			filter = new Filter();
		return filter;
	}
	
	public QueryExecutorArguments addFilterField(String fieldName, Object fieldValue) {
		getFilter(Boolean.TRUE).addField(fieldName, fieldValue);
		return this;
	}
	
	public Collection<Object> getObjects(Boolean injectIfNull) {
		if(objects == null && Boolean.TRUE.equals(injectIfNull))
			objects = new ArrayList<>();
		return objects;
	}
	
	public QueryExecutorArguments addObjects(Collection<Object> objects) {
		if(CollectionHelper.isEmpty(objects))
			return this;
		getObjects(Boolean.TRUE).addAll(objects);
		return this;
	}
	
	public QueryExecutorArguments addObjects(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return this;
		addObjects(CollectionHelper.listOf(objects));
		return this;
	}
	
	public Collection<Object> getSystemIdentifiers(Boolean injectIfNull) {
		if(systemIdentifiers == null && Boolean.TRUE.equals(injectIfNull))
			systemIdentifiers = new ArrayList<>();
		return systemIdentifiers;
	}
	
	public QueryExecutorArguments addSystemIdentifiers(Collection<Object> systemIdentifiers) {
		if(CollectionHelper.isEmpty(systemIdentifiers))
			return this;
		getSystemIdentifiers(Boolean.TRUE).addAll(systemIdentifiers);
		return this;
	}
	
	public QueryExecutorArguments addSystemIdentifiers(Object...systemIdentifiers) {
		if(ArrayHelper.isEmpty(systemIdentifiers))
			return this;
		addSystemIdentifiers(CollectionHelper.listOf(systemIdentifiers));
		return this;
	}

	/**/
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString(callSuper = false,doNotUseGetters = true)
	public static class Dto extends AbstractObject implements Serializable {
		private String queryIdentifier;
		private FilterDto filter;
		private Integer firstTupleIndex;
		private Integer numberOfTuples;
		private Boolean isResultCachable;
		
		public FilterDto getFilter(Boolean injectIfNull) {
			if(filter == null && Boolean.TRUE.equals(injectIfNull))
				filter = new FilterDto();
			return filter;
		}
		
		public Dto addFilterField(String fieldName, Object fieldValue) {
			getFilter(Boolean.TRUE).addField(fieldName, fieldValue);
			return this;
		}
		
		/**/
		
		@org.mapstruct.Mapper
		public static abstract class Mapper extends MapperSourceDestination.AbstractImpl<Dto, QueryExecutorArguments> {

			@Override
			protected void __listenGetDestinationAfter__(Dto source, QueryExecutorArguments destination) {
				super.__listenGetDestinationAfter__(source, destination);
				if(StringHelper.isNotBlank(source.getQueryIdentifier()))
					destination.setQuery(QueryGetter.getInstance().get(source.getQueryIdentifier()));
				if(destination.getQuery() == null)
					destination.setQuery(new Query().setIdentifier(source.getQueryIdentifier()));
			}
			
			public FilterDto getFilterDto(Filter filter) {
				if(filter == null)
					return null;
				return MappingHelper.getSource(filter, FilterDto.class);
			}
			
			public Filter getFilter(FilterDto filter) {
				if(filter == null)
					return null;
				return MappingHelper.getDestination(filter, Filter.class);
			}
		}
	}
}