package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceFunctionRemoverImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionRemover;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.system.action.SystemAction;

public class PersistenceFunctionRemoverImpl extends AbstractPersistenceFunctionRemoverImpl implements PersistenceFunctionRemover,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __executeQuery__(SystemAction action) {
		EntityManager entityManager = __inject__(EntityManager.class);
		entityManager.remove(entityManager.merge(getEntity()));		
	}
	
	@Override
	protected void __executeQuery__(SystemAction action, PersistenceQuery persistenceQuery) {
		EntityManager entityManager = __inject__(EntityManager.class);
		
		//Instantiate query
		Query query = entityManager.createNamedQuery(persistenceQuery.getIdentifier().toString());
		
		//Set query parameters
		Properties parameters = getQueryParameters();
		if(parameters != null && parameters.__getMap__()!=null)
			for(Map.Entry<Object, Object> entry : parameters.__getMap__().entrySet())
				query.setParameter(entry.getKey().toString(), entry.getValue());
		
		//Execute query statement
		Integer count = query.executeUpdate();
		
		getProperties().setCount(count);
		
		//This is required when doing batch processing
		entityManager.clear();
	}

}
