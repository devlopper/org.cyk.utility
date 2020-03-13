package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.representation.Action;
import org.cyk.utility.__kernel__.object.__static__.representation.Actions;

public abstract class AbstractDataImpl extends AbstractObject implements Data,Serializable {
	private static final long serialVersionUID = 1L;

	private Actions __actions__;
	
	@Override
	public Actions get__actions__() {
		return __actions__;
	}
	
	@Override
	public Data set__actions__(Actions __actions__) {
		this.__actions__ = __actions__;
		return this;
	}
	
	@Override
	public Action getActionByIdentifier(String identifier) {
		Actions actions = get__actions__();
		return actions == null ? null : actions.getByIdentifier(identifier);
	}
	
	@Override
	public String get__actionUniformResourceLocatorByIdentifier__(String identifier) {
		Actions actions = get__actions__();
		return actions == null ? null : actions.getUniformResourceLocatorByIdentifier(identifier);
	}
	
}