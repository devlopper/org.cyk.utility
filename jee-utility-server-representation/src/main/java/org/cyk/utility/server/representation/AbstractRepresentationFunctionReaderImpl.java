package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.mapping.MappingHelper;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractRepresentationFunctionReaderImpl extends AbstractRepresentationFunctionImpl implements RepresentationFunctionReader, Serializable {
	private static final long serialVersionUID = 1L;
	
	public static LogLevel LOG_LEVEL = LogLevel.TRACE;
	
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
		
		Strings entityFieldNames = getEntityFieldNames();
		Properties properties = new Properties().setFields(entityFieldNames);
		if(Boolean.TRUE.equals(__isCollectionable__)) {
			if(Boolean.TRUE.equals(__injectCollectionHelper__().isEmpty(__entitiesSystemIdentifiers__)) && Boolean.TRUE.equals(__injectCollectionHelper__().isEmpty(__entitiesBusinessIdentifiers__))) {
				// no specific identifiers
				//In order to take less execution time and data size , we will set default values if not set by caller.
				properties.copyFrom(getProperties(), Properties.IS_QUERY_RESULT_PAGINATED, Properties.QUERY_FIRST_TUPLE_INDEX,Properties.QUERY_NUMBER_OF_TUPLE,Properties.QUERY_FILTERS);
				if(properties.getIsQueryResultPaginated() == null)
					properties.setIsQueryResultPaginated(Boolean.TRUE); //yes we paginate
				addLogMessageBuilderParameter("query paginated", properties.getIsQueryResultPaginated());
				if(Boolean.TRUE.equals(properties.getIsQueryResultPaginated())) {
					if(properties.getQueryFirstTupleIndex() == null)
						properties.setQueryFirstTupleIndex(0); // first page
					if(properties.getQueryNumberOfTuple() == null)
						properties.setQueryNumberOfTuple(5); // 5 results	
					addLogMessageBuilderParameter("first", properties.getQueryFirstTupleIndex());
					addLogMessageBuilderParameter("count", properties.getQueryNumberOfTuple());
				}
				if(properties.getQueryFilters()!=null)
					addLogMessageBuilderParameter("filters", properties.getQueryFilters());
				
				Collection<?> collection = __injectBusiness__().find(__persistenceEntityClass__, properties);
				if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(collection))) {
					if(__entities__ == null)
						__entities__ = new ArrayList<>();
					__entities__.addAll(__inject__(MappingHelper.class).getSources(collection, getEntityClass()));
				}
				properties.setQueryIdentifier(null);
				__responseBuilder__.header("X-Total-Count", __injectBusiness__().count(__persistenceEntityClass__, properties));
				/*
				HttpServletRequest request = __inject__(HttpServletRequest.class);
				String uri = __inject__(UniformResourceIdentifierStringBuilder.class).setRequest(request).execute().getOutput();
				__responseBuilder__.link(uri, "next");
				*/
			}else {
				//specific identifiers	
				
			}
		} else {
			Object identifier = __injectCollectionHelper__().getFirst(ValueUsageType.SYSTEM.equals(__entityIdentifierValueUsageType__) ? __entitiesSystemIdentifiers__ : __entitiesBusinessIdentifiers__);
			if(identifier != null) {
				properties.setValueUsageType(__entityIdentifierValueUsageType__);
				Object entity = __injectBusiness__().findByIdentifier(__persistenceEntityClass__,identifier,__entityIdentifierValueUsageType__,properties);
				if(entity != null) {
					if(__entities__ == null)
						__entities__ = new ArrayList<>();
					__entities__.add(__inject__(MappingHelper.class).getSource(entity, getEntityClass()));	
				}
				
			}
		}
	}
	
	@Override
	protected Status __computeResponseStatus__() {
		if(!Boolean.TRUE.equals(__isCollectionable__) && Boolean.TRUE.equals(__injectCollectionHelper__().isEmpty(__entities__))) {
			return Response.Status.NOT_FOUND;	
		}
		return super.__computeResponseStatus__();
	}
	
	@Override
	protected Object __computeResponseEntity__() {
		if(__throwable__ == null) {
			if(Boolean.TRUE.equals(__isCollectionable__)) {
				if(Boolean.TRUE.equals(__injectCollectionHelper__().isEmpty(__entities__)))
					return null;
				else
					return new GenericEntity<List<?>>((List<?>) __entities__,(Type) __injectTypeHelper__().instanciateCollectionParameterizedType(List.class, getEntityClass()));
			}else {
				if(Boolean.TRUE.equals(__injectCollectionHelper__().isEmpty(__entities__)))
					return null;
				else
					return new GenericEntity<Object>(__entities__.iterator().next(), getEntityClass());	
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
