package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.system.AbstractSystemServiceProviderImpl;

public abstract class AbstractRepresentationServiceProviderImpl extends AbstractSystemServiceProviderImpl implements RepresentationServiceProvider,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected static RepresentationLayer __injectRepresentationLayer__() {
		return __inject__(RepresentationLayer.class);
	}
}
