package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.instance.AbstractInstanceGetterImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.server.persistence.Persistence;

@Dependent @org.cyk.utility.__kernel__.annotation.Persistence
public class InstanceGetterImpl extends AbstractInstanceGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected <INSTANCE> INSTANCE __getByIdentifier__(Class<INSTANCE> klass, Object identifier,ValueUsageType valueUsageType) {
		return __inject__(Persistence.class).readByIdentifier(klass, identifier, valueUsageType);
	}

	@Override
	protected <INSTANCE> Collection<INSTANCE> __getAll__(Class<INSTANCE> klass, Properties properties) {
		return __inject__(Persistence.class).read(klass, properties);
	}
	
}
