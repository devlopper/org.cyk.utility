package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public abstract class AbstractRepresentationFunctionModifierImpl extends AbstractRepresentationFunctionTransactionImpl implements RepresentationFunctionModifier, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionUpdate.class));
	}

	@Override
	protected void __executeBusiness__() {
		if(CollectionHelper.isEmpty(__entities__))
			return;		
		Collection<Object> persistenceEnities = null;
		if(CollectionHelper.isNotEmpty(__entitiesSystemIdentifiers__)) {
			Collection<?> collection = __injectBusiness__().findByIdentifiers(__persistenceEntityClass__, __entitiesSystemIdentifiers__, ValueUsageType.SYSTEM);
			if(CollectionHelper.isNotEmpty(collection)) {
				if(persistenceEnities == null)
					persistenceEnities = new ArrayList<>();
				persistenceEnities.addAll(collection);
			}
		}
		if(CollectionHelper.isNotEmpty(__entitiesBusinessIdentifiers__)) {
			Collection<?> collection = __injectBusiness__().findByIdentifiers(__persistenceEntityClass__, __entitiesBusinessIdentifiers__, ValueUsageType.BUSINESS);
			if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(collection))) {
				if(persistenceEnities == null)
					persistenceEnities = new ArrayList<>();
				persistenceEnities.addAll(collection);
			}
		}		
		if(CollectionHelper.isEmpty(persistenceEnities))
			return;
		Strings fieldNames = getEntityFieldNames();
		if(CollectionHelper.isEmpty(fieldNames))
			return;		
		for(Object index : persistenceEnities) {
			Object entity = __getEntity__(__entities__, index);
			if(entity != null) {
				/* Copy field value from representation entity to persistence entity*/
				InstanceHelper.copy(entity, index, fieldNames.get());				
			}
		}
		__injectBusiness__().updateMany(persistenceEnities,new Properties().setFields(fieldNames));					
	}
	
	private Object __getEntity__(Collection<?> entities,Object persistenceEntity) {
		Object entity = null;
		Object persistenceSystemIdentifier = FieldHelper.readSystemIdentifier(persistenceEntity);
		Object persistenceBusinessIdentifier = FieldHelper.readBusinessIdentifier(persistenceEntity);
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(entities)))
			for(Object index : entities) {
				Object representationSystemIdentifier = null;
				Object representationBusinessIdentifier = null;
				
				if(__entityClassSystemIdentifierField__ != null) {
					representationSystemIdentifier = FieldHelper.readSystemIdentifier(index);
					if(representationSystemIdentifier != null && persistenceSystemIdentifier!=null && representationSystemIdentifier.equals(persistenceSystemIdentifier)) {
						entity = index;
						break;
					}
				}
				
				if(__entityClassBusinessIdentifierField__ != null) {
					representationBusinessIdentifier = FieldHelper.readBusinessIdentifier(index);
					if(representationBusinessIdentifier != null && persistenceBusinessIdentifier!=null && representationBusinessIdentifier.equals(persistenceBusinessIdentifier)) {
						entity = index;
						break;
					}
				}
			}
		if(entity == null)
			__logWarning__(String.format("Representation entity not found for persistence entity %s | %s", persistenceEntity,entities));
		return entity;		
	}
}
