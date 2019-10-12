package org.cyk.utility.__kernel__.identifier.resource;

import java.net.URI;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.configuration.ConstantParameterName;
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
			if(ProxyHelperVariables.UNIFORM_RESOURCE_IDENTIFIER != null)
				return ProxyHelperVariables.UNIFORM_RESOURCE_IDENTIFIER;
			if(!UNIFORM_RESOURCE_IDENTIFIER.isHasBeenSet())
				UNIFORM_RESOURCE_IDENTIFIER.initialize();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("proxy uniform resource identifier", UNIFORM_RESOURCE_IDENTIFIER.get());
			ProxyHelperVariables.UNIFORM_RESOURCE_IDENTIFIER = URI.create((String)UNIFORM_RESOURCE_IDENTIFIER.get());
			return ProxyHelperVariables.UNIFORM_RESOURCE_IDENTIFIER;
		}
	};
	
	Value UNIFORM_RESOURCE_IDENTIFIER = DependencyInjection.inject(Value.class).setConfigurationValueName(ConstantParameterName.PROXY_UNIFORM_RESOURCE_IDENTIFIER);
}
