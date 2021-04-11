package org.cyk.utility.business;

import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.persistence.query.QueryExecutorArguments;

public interface SpecificBusiness<ENTITY> {

	@Transactional
	void create(QueryExecutorArguments arguments);
	
	@Transactional
	void create(Collection<ENTITY> entities);
	
	@Transactional
	void create(ENTITY...entities);
}