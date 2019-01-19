package org.cyk.utility.system;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.system.action.SystemActor;
import org.cyk.utility.system.action.SystemActorClient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Christian
 *
 */
@Getter @Setter @Accessors(chain=true)
public abstract class AbstractSystemFunctionClientImpl extends AbstractSystemFunctionImpl implements SystemFunctionClient, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> dataTransferClass;
	
	@Override
	public SystemFunctionClient setActionEntityClass(Class<?> entityClass) {
		return (SystemFunctionClient) super.setActionEntityClass(entityClass);
	}
	
	@Override
	public SystemFunctionClient addActionEntities(Object... entities) {
		return (SystemFunctionClient) super.addActionEntities(entities);
	}
	
	@Override
	public Class<?> getDataTransferClass() {
		return dataTransferClass;
	}
	
	@Override
	public SystemFunctionClient setDataTransferClass(Class<?> dataTransferClass) {
		this.dataTransferClass = dataTransferClass;
		return this;
	}

	@Override
	public SystemFunctionClient setEntityIdentifier(Object identifier) {
		return (SystemFunctionClient) super.setEntityIdentifier(identifier);
	}
	
	@Override
	public SystemFunctionClient addActionEntitiesIdentifiers(Object... entitiesIdentifiers) {
		return (SystemFunctionClient) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	public SystemFunctionClient addActionEntitiesIdentifiers(Collection<Object> entitiesIdentifiers) {
		return (SystemFunctionClient) super.addActionEntitiesIdentifiers(entitiesIdentifiers);
	}
	
	@Override
	public SystemFunctionClient setActionEntityIdentifierClass(Class<?> entityIdentifierClass) {
		return (SystemFunctionClient) super.setActionEntityIdentifierClass(entityIdentifierClass);
	}
	
	protected SystemActor getSystemActor(){
		return __inject__(SystemActorClient.class);
	}

}
