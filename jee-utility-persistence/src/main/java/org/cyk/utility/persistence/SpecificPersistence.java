package org.cyk.utility.persistence;

import java.util.Set;

import org.cyk.utility.persistence.query.QueryExecutorArguments;

public interface SpecificPersistence<ENTITY> {
	
	String getQueryIdentifierReadDynamic();
	
	String getQueryIdentifierReadDynamicOne();
	
	String getQueryIdentifierCountDynamic();
	
	Set<String> getQueriesIdentifiers();
	
	Boolean hasQueryIdentifier(String identifier);
	
	Boolean isProcessable(QueryExecutorArguments arguments);
	
	String getParameterNameFilterAsString();
	
	String getParameterNameIdentifier();
	
	/**/
	
	String PARAMETER_NAME_FILTER_AS_STRING = "filter_as_string";
	
	String PARAMETER_NAME_IDENTIFIER = "identifier";
}