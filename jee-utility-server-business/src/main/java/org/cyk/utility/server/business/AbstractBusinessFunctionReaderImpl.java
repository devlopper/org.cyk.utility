package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;

public abstract class AbstractBusinessFunctionReaderImpl extends AbstractBusinessFunctionImpl implements BusinessFunctionReader, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionRead.class));
	}
	
	@Override
	protected void __execute__(SystemAction action) {
		Collection<Object> entities = null;
		entities = __execute__(__entitiesSystemIdentifiers__,ValueUsageType.SYSTEM,entities);
		entities = __execute__(__entitiesBusinessIdentifiers__,ValueUsageType.BUSINESS,entities);
		getProperties().setEntities(entities);
	}
	
	private Collection<Object> __execute__(Collection<Object> identifiers,ValueUsageType valueUsageType,Collection<Object> entities) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(identifiers))) {
			Properties properties = new Properties();
			properties.copyFrom(getProperties(), Properties.FIELDS);
			Collection<?> collection = __inject__(Persistence.class).readByIdentifiers(getEntityClass(),identifiers,valueUsageType,properties);
			if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(collection))) {
				if(entities == null)
					entities = new ArrayList<>();
				entities.addAll(collection);
			}
		}
		return entities;
	}
	
	@Override
	public BusinessFunctionReader setEntityIdentifier(Object identifier) {
		getProperties().setEntityIdentifier(identifier);
		return this;
	}
	
	@Override
	public Object getEntityIdentifier() {
		return getProperties().getEntityIdentifier();
	}
	
	@Override
	public BusinessFunctionReader setEntityIdentifierValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}
	
	@Override
	public ValueUsageType getEntityIdentifierValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}
	
	@Override
	public BusinessFunctionReader setAction(SystemAction action) {
		return (BusinessFunctionReader) super.setAction(action);
	}
	
	@Override
	public BusinessFunctionReader setEntity(Object entity) {
		return (BusinessFunctionReader) super.setEntity(entity);
	}
	
	@Override
	public BusinessFunctionReader setEntities(Collection<?> entities) {
		return (BusinessFunctionReader) super.setEntities(entities);
	}
}
