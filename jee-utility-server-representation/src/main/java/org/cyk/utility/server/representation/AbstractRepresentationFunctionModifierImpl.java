package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldValueCopy;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractRepresentationFunctionModifierImpl extends AbstractRepresentationFunctionTransactionImpl implements RepresentationFunctionModifier, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionUpdate.class));
	}

	@Override
	protected void __executeBusiness__() {
		if(__injectCollectionHelper__().isNotEmpty(__entities__)) {
			Collection<Object> persistenceEnities = null;
			if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(__entitiesSystemIdentifiers__))) {
				Collection<?> collection = __injectBusiness__().findByIdentifiers(__persistenceEntityClass__, __entitiesSystemIdentifiers__, ValueUsageType.SYSTEM);
				if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(collection))) {
					if(persistenceEnities == null)
						persistenceEnities = new ArrayList<>();
					persistenceEnities.addAll(collection);
				}
			}
			if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(__entitiesBusinessIdentifiers__))) {
				Collection<?> collection = __injectBusiness__().findByIdentifiers(__persistenceEntityClass__, __entitiesBusinessIdentifiers__, ValueUsageType.BUSINESS);
				if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(collection))) {
					if(persistenceEnities == null)
						persistenceEnities = new ArrayList<>();
					persistenceEnities.addAll(collection);
				}
			}
			
			if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(persistenceEnities))) {
				Strings fieldNames = getEntityFieldNames();
				if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(fieldNames))) {
					for(Object index : persistenceEnities) {
						Object entity = __getEntity__(__entities__, index);
						if(entity != null) {
							/* Copy field value from representation entity to persistence entity*/
							for(String indexFieldNameString : fieldNames.get())
								__inject__(FieldValueCopy.class).execute(entity, index, indexFieldNameString);					
						}
					}
					__injectBusiness__().updateMany(persistenceEnities,new Properties().setFields(fieldNames));		
				}
			}
		}
	}
	
	private Object __getEntity__(Collection<?> entities,Object persistenceEntity) {
		Object entity = null;
		Object persistenceSystemIdentifier = __injectFieldHelper__().getFieldValueSystemIdentifier(persistenceEntity);
		Object persistenceBusinessIdentifier = __injectFieldHelper__().getFieldValueBusinessIdentifier(persistenceEntity);
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(entities)))
			for(Object index : entities) {
				Object representationSystemIdentifier = null;
				Object representationBusinessIdentifier = null;
				
				if(__entityClassSystemIdentifierField__ != null) {
					representationSystemIdentifier = __injectFieldHelper__().getFieldValueSystemIdentifier(index);
					if(representationSystemIdentifier != null && persistenceSystemIdentifier!=null && representationSystemIdentifier.equals(persistenceSystemIdentifier)) {
						entity = index;
						break;
					}
				}
				
				if(__entityClassBusinessIdentifierField__ != null) {
					representationBusinessIdentifier = __injectFieldHelper__().getFieldValueBusinessIdentifier(index);
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
