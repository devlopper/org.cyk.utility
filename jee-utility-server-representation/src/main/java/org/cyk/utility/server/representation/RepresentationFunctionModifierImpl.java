package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldValueCopy;
import org.cyk.utility.string.Strings;
import org.cyk.utility.value.ValueUsageType;

@Dependent
public class RepresentationFunctionModifierImpl extends AbstractRepresentationFunctionModifierImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __executeBusiness__() {
		if(getEntities()!=null)
			;
		else if(getEntity()!=null) {
			Object updatedEntity = getEntity();
			Object currentEntity;
			Object currentEntityIdentifier = __injectFieldHelper__().getFieldValueSystemIdentifier(updatedEntity);
			ValueUsageType currentEntityIdentifierType;
			if(currentEntityIdentifier == null) {
				currentEntityIdentifier = __injectFieldHelper__().getFieldValueBusinessIdentifier(updatedEntity);
				currentEntityIdentifierType = ValueUsageType.BUSINESS;
			}else {
				//TODO convert base on field type
				//currentEntityIdentifier = __injectNumberHelper__().getLong(currentEntityIdentifier);
				currentEntityIdentifierType = ValueUsageType.SYSTEM;
			}
			
			currentEntity = __injectBusiness__().findOne(getPersistenceEntityClass(),currentEntityIdentifier ,new Properties().setValueUsageType(currentEntityIdentifierType));
			/* Copy field value from updated entity to current entity*/
			Strings fieldNames = getEntityFieldNames();
			if(__injectCollectionHelper__().isNotEmpty(fieldNames)) {
				for(String index : fieldNames.get()) {
					__inject__(FieldValueCopy.class).execute(updatedEntity, currentEntity, index);
				}
			}
			
			//__inject__(FieldHelper.class).setFieldValueBusinessIdentifier(currentEntity, __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(updatedEntity));
			__injectBusiness__().update(currentEntity,new Properties().setFields(fieldNames));
		}else {
			
		}
	}
	
}
