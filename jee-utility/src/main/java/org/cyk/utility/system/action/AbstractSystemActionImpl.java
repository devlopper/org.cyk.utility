package org.cyk.utility.system.action;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractSystemActionImpl extends AbstractObject implements SystemAction, Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean isBatchProcessing;
	
	@Override
	public Boolean getIsBatchProcessing() {
		return isBatchProcessing;
	}
	
	@Override
	public SystemAction setIsBatchProcessing(Boolean isBatchProcessing) {
		this.isBatchProcessing = isBatchProcessing;
		return this;
	}
	
}
