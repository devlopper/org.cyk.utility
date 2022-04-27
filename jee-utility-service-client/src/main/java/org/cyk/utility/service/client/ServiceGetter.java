package org.cyk.utility.service.client;

import java.io.Serializable;
import java.net.URI;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

public interface ServiceGetter {

	<T> T get(Class<T> klass);
	
	public static abstract class AbstractImpl extends AbstractObject implements ServiceGetter,Serializable {
		
		public <T> T get(Class<T> klass) {
			if(klass == null)
				throw new RuntimeException("Interface is required");
			return __get__(klass);
		}
	
		protected <T> T __get__(Class<T> klass) {
			URI uri = getUniformResourceIdentifier();
			return (T) buildClient(uri, klass,getClientBuilder(uri, klass));
			//return (SpecificService<?>) RestClientBuilder.newBuilder().baseUri(getUniformResourceIdentifier()).build(serviceInterface);
		}
		
		protected RestClientBuilder getClientBuilder(URI uniformResourceIdentifier,Class<?> klass) {
			//LogHelper.logInfo(String.format("Rest client builder created for uri <<%s>>", uniformResourceIdentifier), getClass());
			return RestClientBuilder.newBuilder().baseUri(uniformResourceIdentifier).register(new ClientRequestFilter());
		}
		
		protected <T> T buildClient(URI uniformResourceIdentifier,Class<T> klass,RestClientBuilder builder) {
			return (T) builder.build(klass);
		}
		
		public static String getSystemIdentifier() {
			return ConfigProvider.getConfig().getOptionalValue(SYSTEM_IDENTIFIER_PROPERTY, String.class).orElseThrow();
		}
		
		public static String getUniformResourceIdentifierAsString(String systemIdentifier) {
			if(StringHelper.isBlank(systemIdentifier))
				throw new RuntimeException("System identifier is required");
			String value = ConfigProvider.getConfig().getOptionalValue(String.format(SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_PROPERTY_FORMAT, systemIdentifier), String.class)
					.orElseThrow();
			if(StringHelper.isBlank(value))
				throw new RuntimeException("Uniform resource identifier is required");
			return value;
		}
		
		public static URI getUniformResourceIdentifier(String systemIdentifier) {
			String string = getUniformResourceIdentifierAsString(systemIdentifier);
			if(StringHelper.isBlank(string))
				throw new RuntimeException("Uniform resource identifier is required");
			return URI.create(string);
		}
		
		public static URI getUniformResourceIdentifier() {
			return getUniformResourceIdentifier(getSystemIdentifier());
		}
		
		private static final String SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_PROPERTY_FORMAT = "%s.server.client.rest.uri";
		private static final String SYSTEM_IDENTIFIER_PROPERTY = "system.identifier";
	}
}