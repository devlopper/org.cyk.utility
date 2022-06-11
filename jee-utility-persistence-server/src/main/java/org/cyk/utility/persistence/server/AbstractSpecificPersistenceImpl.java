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
import org.cyk.utility.persistence.server.query.RuntimeQueryBuilderImpl;
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
		RuntimeQueryBuilderImpl.BUILDABLES.addAll(queriesIdentifiers);
	}
	
	@Override
	public Collection<ENTITY> readMany(QueryExecutorArguments arguments) {
		if(arguments.getQuery() == null)
			arguments.setQuery(new Query().setIdentifier(queryIdentifierReadDynamic));
		if(arguments.getQuery().getIdentifier().equals(queryIdentifierReadDynamic))
			return CollectionHelper.cast(entityClass, DynamicManyExecutor.getInstance().read(entityImplClass,arguments.setQueryIdentifier(arguments.getQuery().getIdentifier()).setQuery(null)));
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
	public Collection<ENTITY> readManyByIdentifiers(Collection<String> identifiers, Collection<String> projections) {
		return readMany(new QueryExecutorArguments().addProjectionsFromStrings(projections).addFilterFieldsValues(getParameterNameIdentifiers(),identifiers));
	}
	
	@Override
	public Collection<ENTITY> readManyByIdentifiers(Collection<String> identifiers) {
		return readManyByIdentifiers(identifiers, null);
	}
	
	@Override
	public ENTITY readOne(QueryExecutorArguments arguments) {
		if(arguments.getQuery() == null)
			arguments.setQuery(new Query().setIdentifier(queryIdentifierReadDynamicOne));
		if(arguments.getQuery().getIdentifier().equals(queryIdentifierReadDynamicOne))
			return (ENTITY) DynamicOneExecutor.getInstance().read(entityImplClass,arguments.setQueryIdentifier(arguments.getQuery().getIdentifier()).setQuery(null));
		throw new RuntimeException("Not yet handled : "+arguments);
	}
	
	@Override
	public ENTITY readOne(String identifier, Collection<String> projections) {
		if(StringHelper.isBlank(identifier))
			return null;
		QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
		queryExecutorArguments.setQuery(new Query().setIdentifier(queryIdentifierReadDynamicOne));
		if(CollectionHelper.isNotEmpty(projections))
			queryExecutorArguments.addProjectionsFromStrings(projections);
		queryExecutorArguments.addFilterFieldsValues(getParameterNameIdentifier(),identifier);
		configure(queryExecutorArguments);
		return readOne(queryExecutorArguments);
	}
	
	@Override
	public ENTITY readOne(String identifier) {
		return readOne(identifier,null);
	}
	
	@Override
	public ENTITY readDefault(QueryExecutorArguments arguments) {
		if(arguments == null)
			arguments = new QueryExecutorArguments();
		if(arguments.getFilterField(getParameterNameDefaultValue()) == null)
			arguments.addFilterFieldsValues(getParameterNameDefaultValue(),Boolean.TRUE);
		return readOne(arguments);
	}
	
	@Override
	public ENTITY readDefault(Collection<String> projections) {
		return readDefault(new QueryExecutorArguments().addProjectionsFromStrings(projections));
	}
	
	@Override
	public ENTITY readDefault() {
		return readDefault(new QueryExecutorArguments());
	}
	
	@Override
	public Collection<ENTITY> readDefaults(QueryExecutorArguments arguments) {
		if(arguments == null)
			arguments = new QueryExecutorArguments();
		if(arguments.getFilterField(getParameterNameDefaultValue()) == null)
			arguments.addFilterFieldsValues(getParameterNameDefaultValue(),Boolean.TRUE);
		return readMany(arguments);
	}
	
	@Override
	public Collection<ENTITY> readDefaults(Collection<String> projections) {
		return readDefaults(new QueryExecutorArguments().addProjectionsFromStrings(projections));
	}
	
	@Override
	public Collection<ENTITY> readDefaults() {
		return readDefaults(new QueryExecutorArguments());
	}
	
	@Override
	public Long count(QueryExecutorArguments arguments) {
		if(arguments.getQuery() == null)
			arguments.setQuery(new Query().setIdentifier(queryIdentifierCountDynamic));
		if(arguments.getQuery().getIdentifier().equals(queryIdentifierCountDynamic))
			return DynamicManyExecutor.getInstance().count(entityImplClass,arguments.setQueryIdentifier(arguments.getQuery().getIdentifier()).setQuery(null));
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
	
	@Override
	public String getParameterNameIdentifiers() {
		return PARAMETER_NAME_IDENTIFIERS;
	}
	
	@Override
	public String getParameterNameDefaultValue() {
		return PARAMETER_NAME_DEFAULT_VALUE;
	}
	
	@Override
	public String getParameterNameUsername() {
		return PARAMETER_NAME_USERNAME;
	}
	
	@Override
	public String getParameterNameSearch() {
		return (String) PARAMETER_NAME_SEARCH.get();
	}
	
	protected static String getQueryIdentifierFromConfiguration(String propertyName) {
		return ConfigProvider.getConfig().getOptionalValue(propertyName, String.class).orElse(propertyName);
	}
}