package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;
import java.net.URL;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public class NavigationImpl extends AbstractObject implements Navigation,Serializable {
	private static final long serialVersionUID = 1L;

	private URL uniformResourceLocator;
	private Strings dynamicParameterNames;
	private SystemAction systemAction;
	
	@Override
	public URL getUniformResourceLocator() {
		return uniformResourceLocator;
	}

	@Override
	public Navigation setUniformResourceLocator(URL uniformResourceLocator) {
		this.uniformResourceLocator = uniformResourceLocator;
		return this;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}
	
	@Override
	public Navigation setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}
	
	@Override
	public Strings getDynamicParameterNames() {
		return dynamicParameterNames;
	}
	
	@Override
	public Navigation setDynamicParameterNames(Strings dynamicParameterNames) {
		this.dynamicParameterNames = dynamicParameterNames;
		return this;
	}

	@Override
	public String toString() {
		return getIdentifier()+":"+getUniformResourceLocator()+":"+getDynamicParameterNames();
	}
}
