package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.net.URI;

import org.cyk.utility.client.controller.proxy.ProxyClassUniformResourceIdentifierGetterImpl;
import org.cyk.utility.configuration.ConstantParameterName;
import org.cyk.utility.string.StringHelper;

public abstract class AbstractSystemContextListener<CONTEXT> extends  org.cyk.utility.context.AbstractSystemContextListenerImpl<CONTEXT> implements SystemContextListener<CONTEXT>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __initialize__(CONTEXT context) {
		super.__initialize__(context);
		String proxyClassUniformResourceIdentifier = getConfigurationParameterValue(ConstantParameterName.PROXY_UNIFORM_RESOURCE_IDENTIFIER);
		if(__inject__(StringHelper.class).isNotBlank(proxyClassUniformResourceIdentifier))
			ProxyClassUniformResourceIdentifierGetterImpl.UNIFORM_RESOURCE_IDENTIFIER = URI.create(proxyClassUniformResourceIdentifier);
	}
	
}
