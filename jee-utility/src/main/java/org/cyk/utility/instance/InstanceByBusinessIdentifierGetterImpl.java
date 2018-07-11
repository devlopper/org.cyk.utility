package org.cyk.utility.instance;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class InstanceByBusinessIdentifierGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements InstanceByBusinessIdentifierGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getIdentifierValue() {
		return getProperties().getFromPath(Properties.BUSINESS,Properties.IDENTIFIER);
	}

	@Override
	public InstanceByBusinessIdentifierGetter setIdentifierValue(Object value) {
		getProperties().setFromPath(new Object[]{Properties.BUSINESS,Properties.IDENTIFIER}, value);
		return this;
	}

	

}
