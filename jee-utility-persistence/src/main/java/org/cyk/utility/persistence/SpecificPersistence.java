package org.cyk.utility.persistence;

import java.util.Collection;
import java.util.Set;

import org.cyk.utility.persistence.query.QueryExecutorArguments;

public interface SpecificPersistence<ENTITY> {
	
	String getQueryIdentifierReadDynamic();
	Collection<ENTITY> readMany(QueryExecutorArguments arguments);
	Collection<ENTITY> readMany(String filterAsString,Integer firstTupleIndex,Integer numberOfTuples);
	
	Collection<ENTITY> readManyByIdentifiers(Collection<String> identifiers,Collection<String> projections);
	Collection<ENTITY> readManyByIdentifiers(Collection<String> identifiers);
	
	String getQueryIdentifierReadDynamicOne();
	ENTITY readOne(QueryExecutorArguments arguments);
	ENTITY readOne(String identifier);
	ENTITY readOne(String identifier,Collection<String> projections);
	
	ENTITY readDefault(QueryExecutorArguments arguments);
	ENTITY readDefault(Collection<String> projections);
	ENTITY readDefault();
	
	String getQueryIdentifierCountDynamic();
	Long count(QueryExecutorArguments arguments);
	Long count(String filterAsString);
	Long count();
	
	Set<String> getQueriesIdentifiers();
	
	Boolean hasQueryIdentifier(String identifier);
	
	Boolean isProcessable(QueryExecutorArguments arguments);
	
	String getParameterNameFilterAsString();
	
	String getParameterNameIdentifier();
	String getParameterNameIdentifiers();
	
	String getParameterNameDefaultValue();
	String getParameterNameDefaultValues();
	
	/**/
	
	String PARAMETER_NAME_FILTER_AS_STRING = "filter_as_string";
	
	String PARAMETER_NAME_IDENTIFIER = "identifier";
	String PARAMETER_NAME_IDENTIFIERS = "identifiers";
	
	String PARAMETER_NAME_DEFAULT_VALUE = "default_value";
	String PARAMETER_NAME_DEFAULT_VALUES = "default_values";
}