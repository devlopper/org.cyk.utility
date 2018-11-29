package org.cyk.utility.client.controller;
import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public abstract class AbstractControllerServiceProviderImpl<OBJECT> extends AbstractSystemServiceProviderImpl implements ControllerServiceProvider<OBJECT>, Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	protected Boolean __isCreateManyOneByOne__() {
		return Boolean.TRUE;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> create(OBJECT object,Properties properties) {
		____inject____(ControllerFunctionCreator.class).setEntity(object).execute();
		return this;
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> create(OBJECT object) {
		return create(object, null);
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> update(OBJECT object, Properties properties) {
		____inject____(ControllerFunctionModifier.class).setEntity(object).execute();
		return this;
	}

	@Override
	public ControllerServiceProvider<OBJECT> update(OBJECT object) {
		return update(object, null);
	}

	@Override
	public ControllerServiceProvider<OBJECT> delete(OBJECT object, Properties properties) {
		____inject____(ControllerFunctionRemover.class).setEntity(object).execute();
		return this;
	}

	@Override
	public ControllerServiceProvider<OBJECT> delete(OBJECT object) {
		return delete(object, null);
	}
	
	@Override
	public ControllerServiceProvider<OBJECT> deleteAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**/
	
	protected static ControllerLayer __injectPersistenceLayer__() {
		return __inject__(ControllerLayer.class);
	}
	
}
