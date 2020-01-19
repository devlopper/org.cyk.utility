package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogLevel;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.persistence.query.filter.FilterDto;

public abstract class AbstractRepresentationFunctionReaderImpl extends AbstractRepresentationFunctionImpl implements RepresentationFunctionReader, Serializable {
	private static final long serialVersionUID = 1L;
	
	public static LogLevel LOG_LEVEL = LogLevel.TRACE;
	
	public static Boolean IS_QUERY_RESULT_PAGINATED = Boolean.TRUE;
	public static Integer QUERY_FIRST_TUPLE_INDEX = 0;
	public static Integer QUERY_NUMBER_OF_TUPLE = 25;
	
	private Boolean isCollectionable,__isCollectionable__;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRead.class));
		setLogLevel(LOG_LEVEL);
	}
	
	@Override
	protected void __executeBusiness__() {
		__isCollectionable__ = getIsCollectionable();
		if(__isCollectionable__ == null) {
			__isCollectionable__ = Boolean.TRUE;
		}
		
		Properties properties = new Properties();
		Strings entityFieldNames = getEntityFieldNames();
		if(CollectionHelper.isNotEmpty(entityFieldNames)) {
			Collection<String> simpleAndNestedFieldsNames = FieldHelper.getSimpleNames(entityFieldNames.get(), Boolean.TRUE);
			simpleAndNestedFieldsNames.addAll(entityFieldNames.get());
			properties.setFields(__inject__(Strings.class).add(simpleAndNestedFieldsNames));
		}
		if(Boolean.TRUE.equals(__isCollectionable__)) {
			if(Boolean.TRUE.equals(CollectionHelper.isEmpty(__entitiesSystemIdentifiers__)) && Boolean.TRUE.equals(CollectionHelper.isEmpty(__entitiesBusinessIdentifiers__))) {
				FilterDto filterDto = (FilterDto) getProperty(Properties.QUERY_FILTERS);
				if(filterDto != null) {
					Filter filter = MappingHelper.getDestination(filterDto, Filter.class).normalize(__persistenceEntityClass__);
					properties.setQueryFilters(filter);
				}
				// no specific identifiers
				//In order to take less execution time and data size , we will set default values if not set by caller.
				properties.copyFrom(getProperties(), Properties.QUERY_IDENTIFIER,Properties.IS_QUERY_RESULT_PAGINATED, Properties.QUERY_FIRST_TUPLE_INDEX,Properties.QUERY_NUMBER_OF_TUPLE);
				if(properties.getIsQueryResultPaginated() == null)
					properties.setIsQueryResultPaginated(IS_QUERY_RESULT_PAGINATED == null ? Boolean.TRUE : IS_QUERY_RESULT_PAGINATED); //yes we paginate
				addLogMessageBuilderParameter("query paginated", properties.getIsQueryResultPaginated());
				if(Boolean.TRUE.equals(properties.getIsQueryResultPaginated())) {
					if(properties.getQueryFirstTupleIndex() == null)
						properties.setQueryFirstTupleIndex(QUERY_FIRST_TUPLE_INDEX == null ? 0 : QUERY_FIRST_TUPLE_INDEX); // first page
					if(properties.getQueryNumberOfTuple() == null)
						properties.setQueryNumberOfTuple(QUERY_NUMBER_OF_TUPLE == null ? 25 : QUERY_NUMBER_OF_TUPLE);
					addLogMessageBuilderParameter("first", properties.getQueryFirstTupleIndex());
					addLogMessageBuilderParameter("count", properties.getQueryNumberOfTuple());
				}
				if(properties.getQueryFilters()!=null)
					addLogMessageBuilderParameter("filter", properties.getQueryFilters());
				
				Collection<?> collection = __injectBusiness__().find(__persistenceEntityClass__, properties);
				if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(collection))) {
					if(__entities__ == null)
						__entities__ = new ArrayList<>();
					__entities__.addAll(MappingHelper.getSources(collection, __entityClass__,properties));
				}
				
				properties.setQueryIdentifier(null);
				__responseBuilder__.header(Constant.RESPONSE_HEADER_X_TOTAL_COUNT, __injectBusiness__().count(__persistenceEntityClass__, properties));
				/*
				HttpServletRequest request = __inject__(HttpServletRequest.class);
				String uri = __inject__(UniformResourceIdentifierStringBuilder.class).setRequest(request).execute().getOutput();
				__responseBuilder__.link(uri, "next");
				*/
			}else {
				//specific identifiers	
				
			}
		} else {
			Object identifier = CollectionHelper.getFirst(ValueUsageType.SYSTEM.equals(__entityIdentifierValueUsageType__) ? __entitiesSystemIdentifiers__ : __entitiesBusinessIdentifiers__);
			if(identifier != null) {
				properties.setValueUsageType(__entityIdentifierValueUsageType__);
				Object entity = __injectBusiness__().findByIdentifier(__persistenceEntityClass__,identifier,__entityIdentifierValueUsageType__,properties);
				if(entity != null) {
					if(__entities__ == null)
						__entities__ = new ArrayList<>();
					__entities__.add(MappingHelper.getSource(entity, __entityClass__,properties));	
				}
				
			}
		}
	}
	
	@Override
	protected Status __computeResponseStatus__() {
		if(!Boolean.TRUE.equals(__isCollectionable__) && Boolean.TRUE.equals(CollectionHelper.isEmpty(__entities__))) {
			return Response.Status.NOT_FOUND;	
		}
		return super.__computeResponseStatus__();
	}
	
	@Override
	protected Object __computeResponseEntity__() {
		if(__throwable__ == null) {
			if(Boolean.TRUE.equals(__isCollectionable__)) {
				if(Boolean.TRUE.equals(CollectionHelper.isEmpty(__entities__)))
					return null;
				else
					return new GenericEntity<List<?>>((List<?>) __entities__,(Type) __injectTypeHelper__().instanciateCollectionParameterizedType(List.class, __entityClass__));
			}else {
				if(Boolean.TRUE.equals(CollectionHelper.isEmpty(__entities__)))
					return null;
				else
					return new GenericEntity<Object>(__entities__.iterator().next(), __entityClass__);	
			}
		}else
			return super.__computeResponseEntity__();
	}
	
	@Override
	public RepresentationFunctionReader setEntityIdentifier(Object identifier) {
		getProperties().setEntityIdentifier(identifier);
		return this;
	}
	
	@Override
	public RepresentationFunctionReader setEntityIdentifierValueUsageType(Object valueUsageType) {
		return (RepresentationFunctionReader) super.setEntityIdentifierValueUsageType(valueUsageType);
	}
	
	@Override
	public RepresentationFunctionReader setAction(SystemAction action) {
		return (RepresentationFunctionReader) super.setAction(action);
	}
	
	@Override
	public RepresentationFunctionReader setEntity(Object entity) {
		return (RepresentationFunctionReader) super.setEntity(entity);
	}
	
	@Override
	public RepresentationFunctionReader setEntities(Collection<?> entities) {
		return (RepresentationFunctionReader) super.setEntities(entities);
	}
	
	@Override
	public RepresentationFunctionReader addEntityFieldNames(String... entityFieldNames) {
		return (RepresentationFunctionReader) super.addEntityFieldNames(entityFieldNames);
	}
	
	@Override
	public Boolean getIsCollectionable() {
		return isCollectionable;
	}
	
	@Override
	public RepresentationFunctionReader setIsCollectionable(Boolean isCollectionable) {
		this.isCollectionable = isCollectionable;
		return this;
	}

}
