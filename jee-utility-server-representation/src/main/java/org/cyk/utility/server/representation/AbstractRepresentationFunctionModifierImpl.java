package org.cyk.utility.server.representation;

import java.io.Serializable;

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
			Object representationEntity = __injectCollectionHelper__().getFirst(__entities__);
			Object persistenceEntity;
			Object persistenceEntityIdentifier = __injectFieldHelper__().getFieldValueSystemIdentifier(representationEntity);
			ValueUsageType persistenceEntityIdentifierType;
			if(persistenceEntityIdentifier == null) {
				persistenceEntityIdentifier = __injectFieldHelper__().getFieldValueBusinessIdentifier(representationEntity);
				persistenceEntityIdentifierType = ValueUsageType.BUSINESS;
			}else {
				//TODO convert base on field type
				//currentEntityIdentifier = __injectNumberHelper__().getLong(currentEntityIdentifier);
				persistenceEntityIdentifierType = ValueUsageType.SYSTEM;
			}
			
			persistenceEntity = __injectBusiness__().findOne(getPersistenceEntityClass(),persistenceEntityIdentifier ,new Properties().setValueUsageType(persistenceEntityIdentifierType));
			/* Copy field value from updated entity to current entity*/
			Strings fieldNames = getEntityFieldNames();
			if(__injectCollectionHelper__().isNotEmpty(fieldNames)) {
				for(String index : fieldNames.get()) {
					__inject__(FieldValueCopy.class).execute(representationEntity, persistenceEntity, index);
				}
			}
			
			//__inject__(FieldHelper.class).setFieldValueBusinessIdentifier(currentEntity, __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(updatedEntity));
			__injectBusiness__().update(persistenceEntity,new Properties().setFields(fieldNames));
		}
	}
}
