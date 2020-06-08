package org.cyk.utility.context;

import java.io.Serializable;

import org.cyk.utility.__kernel__.identifier.resource.ProxyUniformResourceIdentifierGetter;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractSystemContextListenerImpl<CONTEXT> extends AbstractObject implements SystemContextListener<CONTEXT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public SystemContextListener<CONTEXT> initialize(CONTEXT context) {
		__logInfo__("Context initialization running.");
		__initialize__(context);
		__logInfo__("Context initialization done.");
		return this;
	}
	
	protected void __initialize__(CONTEXT context) {
		initializeFromStatic();
	}
	
	@Override
	public SystemContextListener<CONTEXT> destroy(CONTEXT context) {
		__logInfo__("Context destruction running.");
		__destroy__(context);
		__logInfo__("Context destruction done.");
		return this;
	}
	
	protected void __destroy__(CONTEXT context) {}

	public static void initializeFromStatic() {
		ProxyUniformResourceIdentifierGetter.UNIFORM_RESOURCE_IDENTIFIER_STRING.initialize();
	}
}
