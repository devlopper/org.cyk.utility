package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;

import lombok.Getter;

public abstract class AbstractPersistenceEntityImpl<ENTITY> extends AbstractPersistenceServiceProviderImpl<ENTITY> implements PersistenceEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter protected Class<ENTITY> entityClass;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		entityClass = (Class<ENTITY>) __inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ENTITY readOne(Object identifier, Properties properties) {
		return (ENTITY) __inject__(PersistenceFunctionReader.class).setEntityClass(getEntityClass()).setEntityIdentifier(identifier).execute().getProperties().getEntity();
	}
	
	@Override
	public ENTITY readOne(Object identifier) {
		return readOne(identifier, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ENTITY> readMany(Properties properties) {
		return (Collection<ENTITY>) __inject__(PersistenceFunctionReader.class).setEntityClass(getEntityClass()).execute().getProperties().getEntities();
	}
	
	@Override
	public Collection<ENTITY> readMany() {
		return readMany(null);
	}
	
	@Override
	public Long count(Properties properties) {
		return null;
	}
	
	@Override
	public Long count() {
		return null;
	}
}
