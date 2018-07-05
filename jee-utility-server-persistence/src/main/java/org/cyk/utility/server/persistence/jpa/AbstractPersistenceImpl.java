package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.architecture.system.SystemActionCreate;
import org.cyk.utility.architecture.system.SystemActionDelete;
import org.cyk.utility.architecture.system.SystemActionRead;
import org.cyk.utility.architecture.system.SystemActionUpdate;
import org.cyk.utility.server.persistence.PersistenceFunctionCreate;
import org.cyk.utility.server.persistence.PersistenceFunctionDelete;
import org.cyk.utility.server.persistence.PersistenceFunctionRead;
import org.cyk.utility.server.persistence.PersistenceFunctionUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends org.cyk.utility.server.persistence.AbstractPersistenceImpl implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Persistence create(Object entity,Properties properties) {
		__inject__(PersistenceFunctionCreate.class).setAction(getSystemAction(properties, SystemActionCreate.class)).setEntity(entity).execute();
		return this;
	}
	
	@Override
	public Persistence create(Object entity) {
		return create(entity, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> ENTITY read(Class<ENTITY> aClass, Object identifier,Properties properties) {
		return (ENTITY) __inject__(PersistenceFunctionRead.class).setAction(getSystemAction(properties, SystemActionRead.class)).setEntityClass(aClass).setEntityIdentifier(identifier).execute().getProperties().getEntity();
	}
	
	@Override
	public <ENTITY> ENTITY read(Class<ENTITY> aClass, Object identifier) {
		return read(aClass, identifier, null);
	}

	@Override
	public <ENTITY> Collection<ENTITY> readAll(Class<ENTITY> aClass, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <ENTITY> Collection<ENTITY> readAll(Class<ENTITY> aClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Persistence update(Object entity, Properties properties) {
		__inject__(PersistenceFunctionUpdate.class).setAction(getSystemAction(properties, SystemActionUpdate.class)).setEntity(entity).execute();
		return this;
	}
	
	@Override
	public Persistence update(Object entity) {
		update(entity, null);
		return this;
	}

	@Override
	public Persistence delete(Object entity, Properties properties) {
		__inject__(PersistenceFunctionDelete.class).setAction(getSystemAction(properties, SystemActionDelete.class)).setEntity(entity).execute();
		return this;
	}
	
	@Override
	public Persistence delete(Object entity) {
		return delete(entity, null);
	}

	@Override
	public <ENTITY> Long countAll(Class<ENTITY> aClass, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <ENTITY> Long countAll(Class<ENTITY> aClass) {
		// TODO Auto-generated method stub
		return null;
	}
}
