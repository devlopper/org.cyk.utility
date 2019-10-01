package org.cyk.utility.__kernel__.system.action;

import java.util.Collection;

import org.cyk.utility.__kernel__.AbstractAsFunctionParameterIdentified;
import org.cyk.utility.__kernel__.collection.CollectionInstanceAsFunctionParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class SystemActionAsFunctionParameter extends AbstractAsFunctionParameterIdentified<Object,SystemAction> {
	
	private CollectionInstanceAsFunctionParameter entities;
	private CollectionInstanceAsFunctionParameter entitiesIdentifiers;
	private SystemActionAsFunctionParameter next;

	public CollectionInstanceAsFunctionParameter getEntities(Boolean injectIfNull) {
		if(entities == null && Boolean.TRUE.equals(injectIfNull))
			entities = new CollectionInstanceAsFunctionParameter();
		return entities;
	}
	
	public CollectionInstanceAsFunctionParameter getEntitiesIdentifiers(Boolean injectIfNull) {
		if(entitiesIdentifiers == null && Boolean.TRUE.equals(injectIfNull))
			entitiesIdentifiers = new CollectionInstanceAsFunctionParameter();
		return entitiesIdentifiers;
	}
	
	public Class<?> getEntityClass() {
		if(entities == null)
			return null;
		return entities.getElementClass();
	}
	
	public Collection<?> getEntitiesCollection() {
		if(entities == null)
			return null;
		return entities.getElements();
	}
	
	public Collection<?> getEntitiesIdentifiersCollection() {
		if(entitiesIdentifiers == null)
			return null;
		return entitiesIdentifiers.getElements();
	}
}