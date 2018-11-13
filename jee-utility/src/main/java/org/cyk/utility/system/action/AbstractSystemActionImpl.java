package org.cyk.utility.system.action;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.object.Objects;

public abstract class AbstractSystemActionImpl extends AbstractObject implements SystemAction, Serializable {
	private static final long serialVersionUID = 1L;

	//TODO this one can be a property of CollectionInstance
	private Boolean isBatchProcessing;
	private Objects entities;
	
	@Override
	public Boolean getIsBatchProcessing() {
		return isBatchProcessing;
	}
	
	@Override
	public SystemAction setIsBatchProcessing(Boolean isBatchProcessing) {
		this.isBatchProcessing = isBatchProcessing;
		return this;
	}
	
	@Override
	public Objects getEntities() {
		return entities;
	}
	
	@Override
	public SystemAction setEntities(Objects entities) {
		this.entities = entities;
		return this;
	}
	
	@Override
	public Objects getEntities(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_ENTITIES, injectIfNull);
	}
	
	public static final String FIELD_ENTITIES = "entities";
	
}
