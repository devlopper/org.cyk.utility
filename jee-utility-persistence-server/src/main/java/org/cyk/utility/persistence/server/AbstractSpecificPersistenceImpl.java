package org.cyk.utility.persistence.server;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.persistence.SpecificPersistence;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.executor.DynamicManyExecutor;
import org.cyk.utility.persistence.server.query.executor.DynamicOneExecutor;
import org.eclipse.microprofile.config.ConfigProvider;

import lombok.Getter;

public abstract class AbstractSpecificPersistenceImpl<ENTITY> implements SpecificPersistence<ENTITY> {

	protected Class<ENTITY> entityClass;
	protected Class<?> entityImplClass;
	@Getter protected String queryIdentifierReadDynamic;	
	@Getter protected String queryIdentifierReadDynamicOne;	
	@Getter protected String queryIdentifierCountDynamic;
	@Getter protected final Set<String> queriesIdentifiers = new HashSet<>();
	
	public AbstractSpecificPersistenceImpl() {
		entityImplClass = ClassHelper.getParameterAt(getClass(), 0);
	}
	
	@PostConstruct
	protected void listenPostConstruct() {
		queriesIdentifiers.add(queryIdentifierReadDynamic = getQueryIdentifierFromConfiguration(String.format("%s.query.identifier.read.dynamic", entityClass.getSimpleName().toLowerCase())));
		queriesIdentifiers.add(queryIdentifierReadDynamicOne = getQueryIdentifierFromConfiguration(String.format("%s.query.identifier.read.dynamic.one", entityClass.getSimpleName().toLowerCase())));
		queriesIdentifiers.add(queryIdentifierCountDynamic = getQueryIdentifierFromConfiguration(String.format("%s.query.identifier.count.dynamic", entityClass.getSimpleName().toLowerCase())));
	}
	
	@Override
	public Collection<ENTITY> readMany(QueryExecutorArguments arguments) {
		if(arguments.getQuery().getIdentifier().equals(queryIdentifierReadDynamic))
			return CollectionHelper.cast(entityClass, DynamicManyExecutor.getInstance().read(entityImplClass,arguments.setQuery(null)));
		throw new RuntimeException("Not yet handled : "+arguments);
	}
	
	@Override
	public Collection<ENTITY> readMany(String filterAsString, Integer firstTupleIndex, Integer numberOfTuples) {
		QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
		queryExecutorArguments.setQuery(new Query().setIdentifier(queryIdentifierReadDynamic));
		configure(queryExecutorArguments);
		if(StringHelper.isNotBlank(filterAsString))
			queryExecutorArguments.addFilterFieldsValues(getParameterNameFilterAsString(),filterAsString);
		queryExecutorArguments.page(null, firstTupleIndex, numberOfTuples);
		return readMany(queryExecutorArguments);
	}
	
	@Override
	public ENTITY readOne(QueryExecutorArguments arguments) {
		if(arguments.getQuery().getIdentifier().equals(queryIdentifierReadDynamicOne))
			return (ENTITY) DynamicOneExecutor.getInstance().read(entityImplClass,arguments.setQuery(null));
		throw new RuntimeException("Not yet handled : "+arguments);
	}
	
	@Override
	public ENTITY readOne(String identifier) {
		QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
		queryExecutorArguments.setQuery(new Query().setIdentifier(queryIdentifierReadDynamicOne));
		configure(queryExecutorArguments);
		return readOne(queryExecutorArguments);
	}
	
	@Override
	public Long count(QueryExecutorArguments arguments) {
		if(arguments.getQuery().getIdentifier().equals(queryIdentifierCountDynamic))
			return DynamicManyExecutor.getInstance().count(entityImplClass,arguments.setQuery(null));
		throw new RuntimeException("Not yet handled : "+arguments);
	}
	
	@Override
	public Long count(String filterAsString) {
		QueryExecutorArguments arguments = new QueryExecutorArguments().setQuery(new Query().setIdentifier(queryIdentifierCountDynamic));
		if(StringHelper.isNotBlank(filterAsString))
			arguments.addFilterFieldsValues(getParameterNameFilterAsString(),filterAsString);		
		return count(arguments);
	}
	
	@Override
	public Long count() {
		return count((String)null);
	}
	
	protected void configure(QueryExecutorArguments queryExecutorArguments) {
		
	}
	
	@Override
	public Boolean hasQueryIdentifier(String identifier) {
		if(CollectionHelper.isEmpty(queriesIdentifiers))
			return Boolean.FALSE;
		return queriesIdentifiers.contains(identifier);
	}
	
	@Override
	public Boolean isProcessable(QueryExecutorArguments arguments) {
		if(arguments == null || arguments.getQuery() == null)
			return null;
		return hasQueryIdentifier(arguments.getQuery().getIdentifier());
	}
	
	@Override
	public String getParameterNameFilterAsString() {
		return PARAMETER_NAME_FILTER_AS_STRING;
	}
	
	@Override
	public String getParameterNameIdentifier() {
		return PARAMETER_NAME_IDENTIFIER;
	}
	
	protected static String getQueryIdentifierFromConfiguration(String propertyName) {
		return ConfigProvider.getConfig().getOptionalValue(propertyName, String.class).orElse(propertyName);
	}
}