package org.cyk.utility.architecture.system;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.service.AbstractServiceProviderImpl;

public abstract class AbstractSystemServiceProviderImpl extends AbstractServiceProviderImpl implements SystemServiceProvider, Serializable {
	private static final long serialVersionUID = 1L;
	
	protected SystemAction getSystemAction(Properties properties,Class<? extends SystemAction> aClass){
		return properties == null || properties.getSystemAction() == null ? __inject__(aClass) : (SystemAction) properties.getSystemAction();
	}
}
