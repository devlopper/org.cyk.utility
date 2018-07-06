package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.PersistenceFunctionCreator;
import org.cyk.utility.server.persistence.PersistenceFunctionRemover;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.PersistenceFunctionModifier;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractPersistenceImpl extends org.cyk.utility.server.persistence.AbstractPersistenceImpl implements Persistence,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Persistence create(Object entity,Properties properties) {
		__inject__(PersistenceFunctionCreator.class).setAction(getSystemAction(properties, SystemActionCreate.class)).setEntity(entity).execute();
		return this;
	}
	
	@Override
	public Persistence create(Object entity) {
		return create(entity, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ENTITY> ENTITY read(Class<ENTITY> aClass, Object identifier,Properties properties) {
		return (ENTITY) __inject__(PersistenceFunctionReader.class).setAction(getSystemAction(properties, SystemActionRead.class)).setEntityClass(aClass).setEntityIdentifier(identifier).execute().getProperties().getEntity();
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
		__inject__(PersistenceFunctionModifier.class).setAction(getSystemAction(properties, SystemActionUpdate.class)).setEntity(entity).execute();
		return this;
	}
	
	@Override
	public Persistence update(Object entity) {
		update(entity, null);
		return this;
	}

	@Override
	public Persistence delete(Object entity, Properties properties) {
		__inject__(PersistenceFunctionRemover.class).setAction(getSystemAction(properties, SystemActionDelete.class)).setEntity(entity).execute();
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
