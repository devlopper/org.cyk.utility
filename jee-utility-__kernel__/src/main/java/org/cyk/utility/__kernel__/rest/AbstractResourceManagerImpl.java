package org.cyk.utility.__kernel__.rest;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractResourceManagerImpl<RESOURCE> implements ResourceManager<RESOURCE>,Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter @Accessors(chain=true) private URI uniformResourceIdentifier;
	@Getter @Setter @Accessors(chain=true) private Class<RESOURCE> resourceClass;
	@Getter @Setter @Accessors(chain=true) private Map<String,String> fieldsNamesMap;
}
