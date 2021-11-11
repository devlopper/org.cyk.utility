package org.cyk.utility.service.client;

import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

public interface Client {

	static String getServiceUniformResourceIdentifier(String propertyName) {
		if(StringHelper.isBlank(propertyName))
			throw new RuntimeException("Uniform resource identifier property is required");
		String uri = ConfigProvider.getConfig().getOptionalValue(propertyName, String.class).orElseThrow();
		if(StringHelper.isBlank(uri))
			throw new RuntimeException("Uniform resource identifier is required");
		return uri;
	}
	
	static String getServiceUniformResourceIdentifier(Class<?> klass) {
		if(klass == null)
			throw new RuntimeException("Class is required");
		String propertyName = (String) FieldHelper.readStatic(klass, STATIC_FIELD_UNIFORM_RESOURCE_IDENTIFIER);
		if(StringHelper.isBlank(propertyName))
			throw new RuntimeException(String.format("Uniform resource identifier property name is required for %s",klass));
		return getServiceUniformResourceIdentifier(propertyName);
	}
	
	static String getServiceUniformResourceIdentifier(Class<?> klass,String propertyName) {
		if(klass == null)
			throw new RuntimeException("Class is required");
		if(StringHelper.isBlank(propertyName))
			throw new RuntimeException(String.format("Uniform resource identifier property of %s is required",klass));
		String uri = ConfigProvider.getConfig().getOptionalValue(propertyName, String.class).orElseThrow();
		if(StringHelper.isBlank(uri))
			throw new RuntimeException(String.format("Uniform resource identifier of %s is required",klass));
		return uri;
	}
	
	static <T> T getService(Class<T> klass,String uniformResourceIdentifier) {
		if(klass == null)
			throw new RuntimeException("Class is required");
		if(StringHelper.isBlank(uniformResourceIdentifier))
			throw new RuntimeException(String.format("Uniform resource identifier of %s is required",klass));
		return RestClientBuilder.newBuilder().baseUri(URI.create(uniformResourceIdentifier)).build(klass);
	}
	
	static <T> T getService(Class<T> klass) {
		String identifier = klass == null ? null : StringUtils.substringBetween(klass.getName(), ".system.", ".server.");
		if(StringHelper.isBlank(identifier))
			throw new RuntimeException(String.format("Identifier of %s is required",klass));
		return getService(klass, String.format(UNIFORM_RESOURCE_IDENTIFIER_PROPERTY_FORMAT, identifier));
	}
	
	static <T> T getServiceProxy(Class<T> klass,String uriFieldValue) {
		if(klass == null)
			throw new RuntimeException("Proxy class is required");
		if(StringHelper.isBlank(uriFieldValue))
			throw new RuntimeException(String.format("Uniform resource identifier field of %s is required",klass));
		String uri = ConfigProvider.getConfig().getOptionalValue(uriFieldValue, String.class).orElseThrow();
		if(StringHelper.isBlank(uri))
			throw new RuntimeException(String.format("Uniform resource identifier of %s is required",klass));
		//return RestClientBuilder.newBuilder().baseUri(URI.create(uri)).build(klass);
		return RestClientBuilder.newBuilder().baseUri(URI.create("http://localhost:8081/api")).build(klass);
	}
	
	static <T> T getServiceProxy(Class<T> klass,Class<?> uriFieldContainer) {
		return getServiceProxy(klass, (String) FieldHelper.readStatic(uriFieldContainer, STATIC_FIELD_UNIFORM_RESOURCE_IDENTIFIER));
	}
	
	String STATIC_FIELD_UNIFORM_RESOURCE_IDENTIFIER = "UNIFORM_RESOURCE_IDENTIFIER";
	String UNIFORM_RESOURCE_IDENTIFIER_PROPERTY_FORMAT = "%s.server.client.rest.uri";
}