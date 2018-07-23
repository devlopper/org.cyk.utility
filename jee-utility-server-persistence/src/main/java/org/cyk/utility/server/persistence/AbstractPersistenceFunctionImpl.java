package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.system.AbstractSystemFunctionServerImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerPersistence;

public abstract class AbstractPersistenceFunctionImpl extends AbstractSystemFunctionServerImpl implements PersistenceFunction, Serializable {
	private static final long serialVersionUID = 1L;
			
	@Override
	public PersistenceFunction setAction(SystemAction action) {
		return (PersistenceFunction) super.setAction(action);
	}
	
	@Override
	public PersistenceFunction setEntityClass(Class<?> aClass) {
		return (PersistenceFunction) super.setEntityClass(aClass);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerPersistence.class);
	}
	
	@Override
	public PersistenceFunction setQueryIdentifier(Object identifier) {
		getProperties().setFromPath(new Object[]{Properties.QUERY}, identifier);
		if(getEntityClass() == null){
			PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(identifier,Boolean.TRUE);
			if(persistenceQuery!=null)
				setEntityClass(persistenceQuery.getResultClass());
		}
		return this;
	}
	
	@Override
	public Object getQueryIdentifier() {
		return getProperties().getFromPath(Properties.QUERY);
	}

	@Override
	public PersistenceFunction setQueryParameters(Properties parameters) {
		getProperties().setParameters(parameters);
		return this;
	}
	
	@Override
	public Properties getQueryParameters() {
		return (Properties) getProperties().getParameters();
	}
	
	@Override
	public PersistenceFunction setQueryParameter(String name, Object value) {
		Properties parameters = getQueryParameters();
		if(parameters == null)
			setQueryParameters(parameters = new Properties());
		parameters.set(name, value);
		return this;
	}
}
