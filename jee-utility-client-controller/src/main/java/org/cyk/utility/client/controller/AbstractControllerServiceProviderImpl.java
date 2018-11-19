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
	

	/**/
	
	protected static ControllerLayer __injectPersistenceLayer__() {
		return __inject__(ControllerLayer.class);
	}
	
}
