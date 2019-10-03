package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.server.persistence.Persistence;

@Dependent @org.cyk.utility.__kernel__.annotation.Persistence
public class InstanceGetterImpl extends AbstractObject implements InstanceGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass, Object identifier,ValueUsageType valueUsageType) {
		if(klass == null || identifier == null)
			return null;
		if(valueUsageType == null)
			valueUsageType = ValueUsageType.SYSTEM;
		return __inject__(Persistence.class).readByIdentifier(klass, identifier, valueUsageType);
	}

}
