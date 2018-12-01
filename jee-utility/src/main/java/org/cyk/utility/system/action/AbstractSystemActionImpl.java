package org.cyk.utility.system.action;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.object.Objects;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractSystemActionImpl extends AbstractObject implements SystemAction, Serializable {
	private static final long serialVersionUID = 1L;

	//TODO this one can be a property of CollectionInstance
	private Boolean isBatchProcessing;
	private Objects entities;
	private Objects entitiesIdentifiers;
	private ValueUsageType entityIdentifierValueUsageType;
	//private Object subActionIdentifier;
	
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
	
	@Override
	public SystemAction setEntityClass(Class<?> entityClass) {
		getEntities(Boolean.TRUE).setElementClass(entityClass);
		return this;
	}
	
	@Override
	public Class<?> getEntityClass() {
		Objects objects = getEntities();
		return objects == null ? null : objects.getElementClass();
	}
	
	@Override
	public Objects getEntitiesIdentifiers() {
		return entitiesIdentifiers;
	}
	
	@Override
	public Objects getEntitiesIdentifiers(Boolean injectIfNull) {
		return (Objects) __getInjectIfNull__(FIELD_ENTITIES_IDENTIFIERS, injectIfNull);
	}
	
	@Override
	public Class<?> getEntityIdentifierClass() {
		return getEntitiesIdentifiers(Boolean.TRUE).getElementClass();
	}
	
	@Override
	public SystemAction setEntitiesIdentifiers(Objects entitiesIdentifiers) {
		this.entitiesIdentifiers = entitiesIdentifiers;
		return this;
	}
	
	@Override
	public SystemAction setEntityIdentifierClass(Class<?> entityIdentifierClass) {
		getEntitiesIdentifiers(Boolean.TRUE).setElementClass(entityIdentifierClass);
		return this;
	}
	
	@Override
	public SystemAction setEntityIdentifierValueUsageType(ValueUsageType entityIdentifierValueUsageType) {
		this.entityIdentifierValueUsageType = entityIdentifierValueUsageType;
		return this;
	}
	
	@Override
	public ValueUsageType getEntityIdentifierValueUsageType() {
		return entityIdentifierValueUsageType;
	}
	/*
	@Override
	public Object getSubActionIdentifier() {
		return subActionIdentifier;
	}
	
	@Override
	public SystemAction setSubActionIdentifier(Object subActionIdentifier) {
		this.subActionIdentifier = subActionIdentifier;
		return this;
	}
	*/
	public static final String FIELD_ENTITIES = "entities";
	public static final String FIELD_ENTITIES_IDENTIFIERS = "entitiesIdentifiers";
	
}
