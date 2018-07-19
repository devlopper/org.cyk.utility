package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public  class AbstractBusinessServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements BusinessServiceProvider<OBJECT>,Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public BusinessServiceProvider<OBJECT> create(OBJECT object, Properties properties) {
		__inject__(BusinessFunctionCreator.class).setEntity(object).execute();
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects, Properties properties) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> createMany(Collection<OBJECT> objects) {
		return createMany(objects, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessServiceProvider<OBJECT> update(OBJECT object) {
		return update(object, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects, Properties properties) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> updateMany(Collection<OBJECT> objects) {
		return updateMany(objects, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}

	@Override
	public BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects, Properties properties) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public BusinessServiceProvider<OBJECT> deleteMany(Collection<OBJECT> objects) {
		return deleteMany(objects, null);
	}

}
