package org.cyk.utility.client.controller.application;

import javax.inject.Singleton;

import org.cyk.utility.client.controller.AbstractObject;

@Singleton
public class ApplicationImpl extends AbstractObject implements Application {
	private static final long serialVersionUID = 1L;

	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Application setName(String name) {
		this.name = name;
		return this;
	}
	
}
