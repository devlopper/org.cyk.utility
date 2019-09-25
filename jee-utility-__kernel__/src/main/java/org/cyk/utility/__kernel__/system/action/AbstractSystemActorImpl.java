package org.cyk.utility.__kernel__.system.action;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;

public abstract class AbstractSystemActorImpl extends AbstractSingleton implements SystemActor, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String __getIdentifier__() {
		return StringUtils.substringAfter(super.__getIdentifier__(), SystemActor.class.getSimpleName());
	}
	
}
