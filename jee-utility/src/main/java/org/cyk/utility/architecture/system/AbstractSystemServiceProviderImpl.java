package org.cyk.utility.architecture.system;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.service.AbstractServiceProviderImpl;

public abstract class AbstractSystemServiceProviderImpl extends AbstractServiceProviderImpl implements SystemServiceProvider, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Collection<String> getLogMarkers(SystemAction systemAction,Object object) {
		return __inject__(CollectionHelper.class).concatenate(
				Arrays.asList( Arrays.asList(getSystemActor().getIdentifier().toString(),getSystemLayer().getIdentifier().toString()), super.getLogMarkers(systemAction,object))); 
	}
	
	protected abstract SystemActor getSystemActor();
	
	protected abstract SystemLayer getSystemLayer();
}
