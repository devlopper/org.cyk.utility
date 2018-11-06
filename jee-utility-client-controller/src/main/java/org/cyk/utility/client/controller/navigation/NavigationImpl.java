package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;
import java.net.URL;

import org.cyk.utility.client.controller.AbstractObject;

public class NavigationImpl extends AbstractObject implements Navigation,Serializable {
	private static final long serialVersionUID = 1L;

	private URL uniformResourceLocator;
	
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
	public String toString() {
		return getIdentifier()+":"+getUniformResourceLocator();
	}
}
