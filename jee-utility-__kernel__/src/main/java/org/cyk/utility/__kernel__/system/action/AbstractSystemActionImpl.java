package org.cyk.utility.__kernel__.system.action;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public abstract class AbstractSystemActionImpl extends AbstractObject implements SystemAction, Serializable {
	private static final long serialVersionUID = 1L;

	//TODO this one can be a property of CollectionInstance
	private Boolean isBatchProcessing;
	private Integer batchSize;
	private Objects entities;
	private Objects entitiesIdentifiers;
	private Strings entitiesFieldsNames;
	private ValueUsageType entityIdentifierValueUsageType;
	private SystemAction nextAction;
	
	@Override
	public SystemAction setIdentifier(Object identifier) {
		return (SystemAction) super.setIdentifier(identifier);
	}
	
	@Override
	public Boolean getIsBatchable() {
		return isBatchProcessing;
	}
	
	@Override
	public SystemAction setIsBatchable(Boolean isBatchProcessing) {
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
		if(entities == null && Boolean.TRUE.equals(injectIfNull))
			entities = __inject__(Objects.class);
		return entities;
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
		if(entitiesIdentifiers == null && Boolean.TRUE.equals(injectIfNull))
			entitiesIdentifiers = __inject__(Objects.class);
		return entitiesIdentifiers;
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
	
	@Override
	public SystemAction getNextAction() {
		return nextAction;
	}
	
	@Override
	public SystemAction setNextAction(SystemAction nextAction) {
		this.nextAction = nextAction;
		return this;
	}
	
	@Override
	public Strings getEntitiesFieldsNames() {
		return entitiesFieldsNames;
	}
	
	@Override
	public Strings getEntitiesFieldsNames(Boolean injectIfNull) {
		if(entitiesFieldsNames == null && Boolean.TRUE.equals(injectIfNull))
			entitiesFieldsNames = __inject__(Strings.class);
		return entitiesFieldsNames;
	}
	
	@Override
	public SystemAction setEntitiesFieldsNames(Strings entitiesFieldsNames) {
		this.entitiesFieldsNames = entitiesFieldsNames;
		return this;
	}
	
	@Override
	public SystemAction addEntitiesFieldsNames(Collection<String> entitiesFieldsNames) {
		getEntitiesFieldsNames(Boolean.TRUE).add(entitiesFieldsNames);
		return this;
	}
	
	@Override
	public SystemAction addEntitiesFieldsNames(String... entitiesFieldsNames) {
		getEntitiesFieldsNames(Boolean.TRUE).add(entitiesFieldsNames);
		return this;
	}
	
	@Override
	public Integer getBatchSize() {
		return batchSize;
	}
	
	@Override
	public SystemAction setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
		return this;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"."+getIdentifier()+":"+getEntityClass()+":"+getEntitiesIdentifiers();
	}
	
	public static final String FIELD_ENTITIES = "entities";
	public static final String FIELD_ENTITIES_IDENTIFIERS = "entitiesIdentifiers";
	public static final String FIELD_ENTITIES_FIELDS_NAMES = "entitiesFieldsNames";
	
}
