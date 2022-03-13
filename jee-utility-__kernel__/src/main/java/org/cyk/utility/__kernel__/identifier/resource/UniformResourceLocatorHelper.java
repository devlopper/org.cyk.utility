package org.cyk.utility.__kernel__.identifier.resource;

import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.__kernel__.protocol.http.HttpHelper;

public interface UniformResourceLocatorHelper {

	static byte[] getBytes(String uniformResourceLocator) {
		if(uniformResourceLocator.toLowerCase().startsWith("http"))
			return HttpHelper.get(uniformResourceLocator,byte[].class,null).body();
		try {
			return IOUtils.toByteArray(URI.create(uniformResourceLocator).toURL());
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
}