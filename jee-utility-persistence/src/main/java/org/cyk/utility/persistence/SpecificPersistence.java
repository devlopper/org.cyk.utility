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
}