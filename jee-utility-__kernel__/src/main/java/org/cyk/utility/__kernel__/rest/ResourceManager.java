package org.cyk.utility.__kernel__.rest;

import java.io.Serializable;
import java.util.Collection;

public interface ResourceManager<RESOURCE> {
	/*
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
	*/
	Collection<RESOURCE> get();
	
	/**/
	
	public static abstract class AbstractResourceManagerImpl<RESOURCE> implements ResourceManager<RESOURCE>,Serializable {
		private static final long serialVersionUID = 1L;
		/*
		@Getter @Setter @Accessors(chain=true) private URI uniformResourceIdentifier;
		@Getter @Setter @Accessors(chain=true) private Class<RESOURCE> resourceClass;
		@Getter @Setter @Accessors(chain=true) private Map<String,String> fieldsNamesMap;
		*/
	}

}
