package org.cyk.utility.business;

import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.persistence.query.QueryExecutorArguments;

public interface SpecificBusiness<ENTITY> {

	@Transactional
	TransactionResult create(QueryExecutorArguments arguments);
	
	@Transactional
	TransactionResult create(Collection<ENTITY> entities);
	
	@Transactional
	TransactionResult create(ENTITY...entities);

	String DELETE = "delete";
	@Transactional
	TransactionResult delete(Collection<String> identifiers,String actorCode);
}