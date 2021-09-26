package org.cyk.utility.persistence.server;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.persistence.SpecificPersistence;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.eclipse.microprofile.config.ConfigProvider;

import lombok.Getter;

public abstract class AbstractSpecificPersistenceImpl<ENTITY> implements SpecificPersistence<ENTITY> {

	protected Class<?> entityClass;
	@Getter protected String queryIdentifierReadDynamic;	
	@Getter protected String queryIdentifierReadDynamicOne;	
	@Getter protected String queryIdentifierCountDynamic;
	@Getter protected final Set<String> queriesIdentifiers = new HashSet<>();
	
	public AbstractSpecificPersistenceImpl() {
		entityClass = ClassHelper.getParameterAt(getClass(), 0);
	}
	
	@PostConstruct
	protected void listenPostConstruct() {
		queriesIdentifiers.add(queryIdentifierReadDynamic = getQueryIdentifierFromConfiguration(String.format("%s.query.identifier.read.dynamic", entityClass.getSimpleName().toLowerCase())));
		queriesIdentifiers.add(queryIdentifierReadDynamicOne = getQueryIdentifierFromConfiguration(String.format("%s.query.identifier.read.dynamic.one", entityClass.getSimpleName().toLowerCase())));
		queriesIdentifiers.add(queryIdentifierCountDynamic = getQueryIdentifierFromConfiguration(String.format("%s.query.identifier.count.dynamic", entityClass.getSimpleName().toLowerCase())));
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
	public String getFilterAsStringParameterName() {
		return PARAMETER_NAME_FILTER_AS_STRING;
	}
	
	protected static String getQueryIdentifierFromConfiguration(String propertyName) {
		return ConfigProvider.getConfig().getOptionalValue(propertyName, String.class).orElse(propertyName);
	}
}