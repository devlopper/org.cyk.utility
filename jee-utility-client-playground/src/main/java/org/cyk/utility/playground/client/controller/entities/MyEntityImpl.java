package org.cyk.utility.playground.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.client.controller.data.AbstractDataIdentifiedByStringAndCodedAndNamedImpl;

public class MyEntityImpl extends AbstractDataIdentifiedByStringAndCodedAndNamedImpl implements MyEntity,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public MyEntity setIdentifier(String identifier) {
		return (MyEntity) super.setIdentifier(identifier);
	}
	
	@Override
	public MyEntity setCode(String code) {
		return (MyEntity) super.setCode(code);
	}
	
	@Override
	public MyEntity setName(String name) {
		return (MyEntity) super.setName(name);
	}
}