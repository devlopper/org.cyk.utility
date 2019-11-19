package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.instance.AbstractInstanceGetterImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;

@org.cyk.utility.__kernel__.annotation.Controller
public class InstanceGetterImpl extends AbstractInstanceGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected <INSTANCE> INSTANCE __getByIdentifier__(Class<INSTANCE> klass, Object identifier,ValueUsageType valueUsageType) {
		return __inject__(Controller.class).readByIdentifier(klass, identifier,valueUsageType);
	}
	
	@Override
	protected <INSTANCE> Collection<INSTANCE> __get__(Class<INSTANCE> klass, Properties properties) {
		return __inject__(Controller.class).read(klass, properties);
	}
	
	@Override
	protected <INSTANCE> Collection<INSTANCE> __getAll__(Class<INSTANCE> klass, Properties properties) {
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.IS_PAGEABLE, Boolean.FALSE);
		return __inject__(Controller.class).read(klass, properties);
	}
	
}