package org.cyk.utility.server.persistence.jpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.AbstractPersistenceFunctionModifierImpl;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionUpdate;

public class PersistenceFunctionModifierImpl extends AbstractPersistenceFunctionModifierImpl implements PersistenceFunctionModifier {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __executeQuery__(SystemAction action) {
		getEntityManager().merge(getEntity());		
	}
	
	@Override
	protected void __executeQuery__(SystemAction action, PersistenceQuery persistenceQuery) {
		//Instantiate query
		Query query = getEntityManager().createNamedQuery(persistenceQuery.getIdentifier().toString());
		
		//Set query parameters
		Properties parameters = getQueryParameters();
		if(parameters != null && parameters.__getMap__()!=null)
			for(Map.Entry<Object, Object> entry : parameters.__getMap__().entrySet())
				query.setParameter(entry.getKey().toString(), entry.getValue());
		
		//Execute query statement
		Integer count = query.executeUpdate();
		
		getProperties().setCount(count);
	}
	
	@Override
	protected void __listenPostConstruct__() {
		getProperties().setEntityManager(__inject__(EntityManager.class)).setAction(__inject__(SystemActionUpdate.class));
		super.__listenPostConstruct__();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return (EntityManager) getProperties().getEntityManager();
	}
	
	@Override
	public PersistenceFunctionModifier setEntityManager(EntityManager entityManager) {
		getProperties().setEntityManager(entityManager);
		return this;
	}
}
