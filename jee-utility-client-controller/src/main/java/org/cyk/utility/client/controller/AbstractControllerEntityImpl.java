package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractControllerEntityImpl<ENTITY> extends AbstractControllerServiceProviderImpl<ENTITY> implements ControllerEntity<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<ENTITY> read(Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<ENTITY> read() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ENTITY readOne(Object identifier, Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ENTITY readOne(Object identifier, ValueUsageType valueUsageType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ENTITY readOne(Object identifier) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ENTITY readOneByBusinessIdentifier(Object identifier) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<ENTITY> readMany(Properties properties) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<ENTITY> readMany() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
