package org.cyk.utility.service.client;

import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.service.SpecificService;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

public interface SpecificServiceGetter {

	SpecificService<?> get(Class<?> klass);
	
	public static abstract class AbstractImpl extends AbstractObject implements SpecificServiceGetter,Serializable {
		
		public SpecificService<?> get(Class<?> klass) {
			if(klass == null)
				throw new RuntimeException("Class is required");
			return __get__(klass);
		}
	
		protected SpecificService<?> __get__(Class<?> klass) {
			Class<?> serviceInterface = MAP.get(klass);
			if(serviceInterface == null) {
				String className = StringUtils.replaceOnce(klass.getName(), ".client.rest.",".api.service.")+"Service";
				serviceInterface = ClassHelper.getByName(className);
				if(serviceInterface == null)
					throw new RuntimeException(String.format("Service interface not found for %s",klass));
				MAP.put(klass, serviceInterface);
			}
			URI uri = getUniformResourceIdentifier();
			return buildClient(uri, serviceInterface,getClientBuilder(uri, serviceInterface));
			//return (SpecificService<?>) RestClientBuilder.newBuilder().baseUri(getUniformResourceIdentifier()).build(serviceInterface);
		}
		
		protected RestClientBuilder getClientBuilder(URI uniformResourceIdentifier,Class<?> klass) {
			//LogHelper.logInfo(String.format("Rest client builder created for uri <<%s>>", uniformResourceIdentifier), getClass());
			return RestClientBuilder.newBuilder().baseUri(uniformResourceIdentifier).register(new ClientRequestFilter());
		}
		
		protected SpecificService<?> buildClient(URI uniformResourceIdentifier,Class<?> klass,RestClientBuilder builder) {
			return (SpecificService<?>) builder.build(klass);
		}
		
		protected String getSystemIdentifier() {
			return ConfigProvider.getConfig().getOptionalValue(SYSTEM_IDENTIFIER_PROPERTY, String.class).orElseThrow();
		}
		
		protected String getUniformResourceIdentifierAsString(String systemIdentifier) {
			if(StringHelper.isBlank(systemIdentifier))
				throw new RuntimeException("System identifier is required");
			String value = ConfigProvider.getConfig().getOptionalValue(String.format(SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_PROPERTY_FORMAT, systemIdentifier), String.class)
					.orElseThrow();
			if(StringHelper.isBlank(value))
				throw new RuntimeException("Uniform resource identifier is required");
			return value;
		}
		
		protected URI getUniformResourceIdentifier(String systemIdentifier) {
			String string = getUniformResourceIdentifierAsString(systemIdentifier);
			if(StringHelper.isBlank(string))
				throw new RuntimeException("Uniform resource identifier is required");
			return URI.create(string);
		}
		
		protected URI getUniformResourceIdentifier() {
			return getUniformResourceIdentifier(getSystemIdentifier());
		}
		
		private static final String SYSTEM_UNIFORM_RESOURCE_IDENTIFIER_PROPERTY_FORMAT = "%s.server.client.rest.uri";
		private static final String SYSTEM_IDENTIFIER_PROPERTY = "system.identifier";
		
		private static final Map<Class<?>,Class<?>> MAP = new HashMap<>();
	}
}