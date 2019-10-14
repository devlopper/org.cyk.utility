package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.instance.AbstractInstanceGetterImpl;
import org.cyk.utility.__kernel__.value.ValueUsageType;

@org.cyk.utility.__kernel__.annotation.Controller
public class InstanceGetterImpl extends AbstractInstanceGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected <INSTANCE> INSTANCE __getByIdentifier__(Class<INSTANCE> klass, Object identifier,ValueUsageType valueUsageType) {
		return __inject__(Controller.class).readByIdentifier(klass, identifier,valueUsageType);
	}
	
}