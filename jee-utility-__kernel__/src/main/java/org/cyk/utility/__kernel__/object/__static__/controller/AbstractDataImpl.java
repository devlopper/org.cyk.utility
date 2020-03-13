package org.cyk.utility.__kernel__.object.__static__.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.Action;
import org.cyk.utility.__kernel__.object.__static__.representation.Actions;

public abstract class AbstractDataImpl extends AbstractObjectImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Actions __actions__;
	
	public Actions get__actions__() {
		return __actions__;
	}
	
	public AbstractDataImpl set__actions__(Actions __actions__) {
		this.__actions__ = __actions__;
		return this;
	}
	
	public Action getActionByIdentifier(String identifier) {
		Actions actions = get__actions__();
		return actions == null ? null : actions.getByIdentifier(identifier);
	}
	
	public String get__actionUniformResourceLocatorByIdentifier__(String identifier) {
		Actions actions = get__actions__();
		return actions == null ? null : actions.getUniformResourceLocatorByIdentifier(identifier);
	}
	
}