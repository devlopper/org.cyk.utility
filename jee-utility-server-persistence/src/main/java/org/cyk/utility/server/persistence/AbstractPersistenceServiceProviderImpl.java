package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public abstract class AbstractPersistenceServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements PersistenceServiceProvider<OBJECT>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public PersistenceServiceProvider<OBJECT> create(Object object,Properties properties) {
		__inject__(PersistenceFunctionCreator.class).setEntity(object).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> create(Object object) {
		return create(object, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects,Properties properties) {
		__inject__(PersistenceFunctionCreator.class).setEntities(objects).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> update(Object object, Properties properties) {
		__inject__(PersistenceFunctionModifier.class).setEntity(object).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> update(Object object) {
		update(object, null);
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects,Properties properties) {
		__inject__(PersistenceFunctionModifier.class).setEntities(objects).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		return updateMany(objects, null);
	}

	@Override
	public PersistenceServiceProvider<OBJECT> delete(Object object, Properties properties) {
		__inject__(PersistenceFunctionRemover.class).setEntity(object).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> delete(Object object) {
		return delete(object, null);
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		__inject__(PersistenceFunctionRemover.class).setEntities(objects).execute();
		return this;
	}
	
	@Override
	public PersistenceServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects) {
		return deleteMany(objects, null);
	}

}
