package org.cyk.utility.service.client;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.method.MethodHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface SpecificClient<ENTITY> extends Client {

	static String formatClientClassName(String basePackageName,Class<?> klass) {
		if(StringHelper.isBlank(basePackageName) || klass == null)
			return null;
		return String.format(CLIENT_CLASS_NAME_FORMAT,basePackageName,klass.getSimpleName());
	}

	static Object getService(Class<?> klass) {
		String clientClassName = SpecificClient.formatClientClassName(StringUtils.substringBefore(klass.getName(),".server."), klass);
		if(StringHelper.isBlank(clientClassName))
			throw new RuntimeException(String.format("Client class name not found for %s", klass));
		Object service = MethodHelper.executeStatic(ClassHelper.getByName(clientClassName), "proxy");
		if(service == null)
			throw new RuntimeException(String.format("Service is required for %s", klass));
		return service;
	}
	
	String CLIENT_CLASS_NAME_FORMAT = "%s.server.client.rest.%sClient";
}