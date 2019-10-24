package org.cyk.utility.__kernel__.identifier.resource;

import java.net.URI;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.configuration.VariableName;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface ProxyUniformResourceIdentifierGetter {

	URI get(Class<?> klass);
	
	default URI get() {
		return get(null);
	}
	
	ProxyUniformResourceIdentifierGetter INSTANCE = new ProxyUniformResourceIdentifierGetter() {		
		@Override
		public URI get(Class<?> klass) {
			if(UNIFORM_RESOURCE_IDENTIFIER.isHasBeenSet())
				return (URI) UNIFORM_RESOURCE_IDENTIFIER.get();
			if(!UNIFORM_RESOURCE_IDENTIFIER_STRING.isHasBeenSet())
				UNIFORM_RESOURCE_IDENTIFIER_STRING.initialize();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("proxy uniform resource identifier", UNIFORM_RESOURCE_IDENTIFIER_STRING.get());
			UNIFORM_RESOURCE_IDENTIFIER.set(URI.create((String)UNIFORM_RESOURCE_IDENTIFIER_STRING.get()));
			return (URI) UNIFORM_RESOURCE_IDENTIFIER.get();
		}
	};
	
	Value UNIFORM_RESOURCE_IDENTIFIER_STRING = DependencyInjection.inject(Value.class).setConfigurationValueName(VariableName.PROXY_UNIFORM_RESOURCE_IDENTIFIER);
	Value UNIFORM_RESOURCE_IDENTIFIER = DependencyInjection.inject(Value.class);
}
