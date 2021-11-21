package org.cyk.utility.service.server;

import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.SpecificPersistence;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.SpecificPersistenceGetter;
import org.cyk.utility.service.FilterFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractEntityRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY> extends AbstractRequestImpl implements Serializable {

	protected Class<SERVICE_ENTITY> serviceEntityClass;
	protected Class<PERSISTENCE_ENTITY> persistenceEntityClass;	
	protected SpecificPersistence<?> persistence;
	protected QueryExecutorArguments queryExecutorArguments;
	
	@SuppressWarnings("unchecked")
	public AbstractEntityRequestImpl(Class<SERVICE_ENTITY> serviceEntityClass) {
		this.serviceEntityClass = serviceEntityClass;
		this.persistenceEntityClass = (Class<PERSISTENCE_ENTITY>) DependencyInjection.inject(PersistenceEntityClassGetter.class).get(this.serviceEntityClass);
		if(persistenceEntityClass != null)
			persistence = DependencyInjection.inject(SpecificPersistenceGetter.class).get(persistenceEntityClass);
	}
	
	public QueryExecutorArguments getQueryExecutorArguments(Boolean instantiateIfNull) {
		if(queryExecutorArguments == null && Boolean.TRUE.equals(instantiateIfNull))
			queryExecutorArguments = new QueryExecutorArguments();
		return queryExecutorArguments;
	}
	
	public AbstractEntityRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY> query(String identifier) {
		getQueryExecutorArguments(Boolean.TRUE).setQuery(new Query().setIdentifier(identifier));
		return this;
	}
	
	public AbstractEntityRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY> filter(String string,FilterFormat format) {
		if(StringHelper.isBlank(string))
			return this;
		if(format == null)
			format = FilterFormat.PLAIN;
		getQueryExecutorArguments(Boolean.TRUE).setFilter(null);
		if(FilterFormat.PLAIN.equals(format))
			getQueryExecutorArguments().addFilterFieldsValues(persistence.getParameterNameFilterAsString(),string);
		else if(FilterFormat.JSON.equals(format))
			getQueryExecutorArguments().setFilter(Filter.instantiateFromJson(string));
		else
			throw new RuntimeException(String.format("Filter string format %s not yet handled", format));
		return this;
	}
	
	public AbstractEntityRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY> filter(String string) {
		return filter(string, FilterFormat.PLAIN);
	}
	
	public AbstractEntityRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY> filterByIdentifier(String identifier) {
		if(StringHelper.isNotBlank(identifier)) {
			getQueryExecutorArguments(Boolean.TRUE).addFilterFieldsValues(persistence.getParameterNameIdentifier(),identifier);
		}
		return this;
	}
	
	public AbstractEntityRequestImpl<SERVICE_ENTITY,PERSISTENCE_ENTITY> enableResponseHeadersCORS() {
		responseBuilderArguments.setHeadersCORS();
		return this;
	}
	
	@Override
	protected void validatePreConditions() {
		super.validatePreConditions();
		if(serviceEntityClass == null)
			throw new RuntimeException("Service entity class is required");
		if(persistenceEntityClass == null)
			throw new RuntimeException("Persistence entity class is required");
		if(queryExecutorArguments == null)
			throw new RuntimeException("Query executor arguments is required");
	}
}