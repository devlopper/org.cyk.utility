package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.architecture.system.SystemAction;

public class PersistenceFunctionReadImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionRead, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Boolean __isQueryExecutable__(SystemAction action) {
		return Boolean.TRUE;
	}
	
	@Override
	protected void __executeQuery__(SystemAction action) {
		Class<?> aClass = getEntityClass();
		Object entityIdentifier = getEntityIdentifier();
		Object entity = getEntityManager().find(aClass,entityIdentifier);
		getProperties().setEntity(entity);
	}

	@Override
	public PersistenceFunctionRead setEntityClass(Class<?> aClass) {
		getProperties().setFromPath(new Object[]{Properties.ENTITY,Properties.CLASS}, aClass);
		return this;
	}

	@Override
	public Class<?> getEntityClass() {
		return (Class<?>) getProperties().getFromPath(Properties.ENTITY,Properties.CLASS);
	}

	@Override
	public PersistenceFunctionRead setEntityIdentifier(Object identifier) {
		getProperties().setFromPath(new Object[]{Properties.ENTITY,Properties.IDENTIFIER}, identifier);
		return this;
	}

	@Override
	public Object getEntityIdentifier() {
		return getProperties().getFromPath(Properties.ENTITY,Properties.IDENTIFIER);
	}

	@Override
	public PersistenceFunctionRead setAction(SystemAction action) {
		return (PersistenceFunctionRead) super.setAction(action);
	}
}
