package org.cyk.utility.__kernel__.identifier.resource;

import java.net.URI;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.variable.VariableName;

public interface ProxyUniformResourceIdentifierGetter {

	default URI get(Class<?> klass) {
		if(UNIFORM_RESOURCE_IDENTIFIER.isHasBeenSet())
			return (URI) UNIFORM_RESOURCE_IDENTIFIER.get();
		if(!UNIFORM_RESOURCE_IDENTIFIER_STRING.isHasBeenSet())
			UNIFORM_RESOURCE_IDENTIFIER_STRING.initialize();
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("proxy uniform resource identifier", UNIFORM_RESOURCE_IDENTIFIER_STRING.get());
		UNIFORM_RESOURCE_IDENTIFIER.set(URI.create((String)UNIFORM_RESOURCE_IDENTIFIER_STRING.get()));
		return (URI) UNIFORM_RESOURCE_IDENTIFIER.get();
	}
	
	default URI get() {
		return get(null);
	}
	
	/**/
	
	static ProxyUniformResourceIdentifierGetter getInstance() {
		ProxyUniformResourceIdentifierGetter instance = (ProxyUniformResourceIdentifierGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(ProxyUniformResourceIdentifierGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", ProxyUniformResourceIdentifierGetter.class);
		return instance;
	}
	
	Value INSTANCE = new Value();
	
	Value UNIFORM_RESOURCE_IDENTIFIER_STRING = new Value().setConfigurationValueName(VariableName.PROXY_UNIFORM_RESOURCE_IDENTIFIER);
	Value UNIFORM_RESOURCE_IDENTIFIER = new Value();
}
