package org.cyk.utility.persistence;

import java.util.Collection;
import java.util.Set;

import org.cyk.utility.persistence.query.QueryExecutorArguments;

public interface SpecificPersistence<ENTITY> {
	
	String getQueryIdentifierReadDynamic();
	Collection<ENTITY> readMany(QueryExecutorArguments arguments);
	Collection<ENTITY> readMany(String filterAsString,Integer firstTupleIndex,Integer numberOfTuples);
	
	String getQueryIdentifierReadDynamicOne();
	ENTITY readOne(QueryExecutorArguments arguments);
	ENTITY readOne(String identifier);
	
	String getQueryIdentifierCountDynamic();
	Long count(QueryExecutorArguments arguments);
	Long count(String filterAsString);
	Long count();
	
	Set<String> getQueriesIdentifiers();
	
	Boolean hasQueryIdentifier(String identifier);
	
	Boolean isProcessable(QueryExecutorArguments arguments);
	
	String getParameterNameFilterAsString();
	
	String getParameterNameIdentifier();
	
	/**/
	
	String PARAMETER_NAME_FILTER_AS_STRING = "filter_as_string";
	
	String PARAMETER_NAME_IDENTIFIER = "identifier";
}