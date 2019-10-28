package org.cyk.utility.__kernel__.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.string.StringHelper;

public interface ResourceManager<RESOURCE> {

	URI getUniformResourceIdentifier();
	ResourceManager<RESOURCE> setUniformResourceIdentifier(URI uniformResourceIdentifier);
	
	default ResourceManager<RESOURCE> setUniformResourceIdentifier(String uniformResourceIdentifier) {
		try {
			return setUniformResourceIdentifier(StringHelper.isBlank(uniformResourceIdentifier) ? null : new URI(uniformResourceIdentifier));
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	Class<RESOURCE> getResourceClass();
	ResourceManager<RESOURCE> setResourceClass(Class<RESOURCE> resourceClass);
	
	Map<String,String> getFieldsNamesMap();
	ResourceManager<RESOURCE> setFieldsNamesMap(Map<String,String> fieldsNamesMap);
	
	Collection<RESOURCE> getMany();
	
}
