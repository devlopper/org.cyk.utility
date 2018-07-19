package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
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
	public PersistenceFunction setNamedQueryIdentifier(Object identifier) {
		getProperties().setFromPath(new Object[]{Properties.QUERY}, identifier);
		return this;
	}
	
	@Override
	public Object getNamedQueryIdentifier() {
		return getProperties().getFromPath(Properties.QUERY);
	}

}
