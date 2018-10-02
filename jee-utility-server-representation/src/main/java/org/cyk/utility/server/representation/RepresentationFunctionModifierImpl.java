package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.collection.CollectionInstanceString;
import org.cyk.utility.field.FieldValueCopy;

public class RepresentationFunctionModifierImpl extends AbstractRepresentationFunctionModifierImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __executeBusiness__() {
		if(getEntities()!=null)
			;
		else if(getEntity()!=null) {
			Object updatedEntity = getEntity();
			Object currentEntity = __injectBusiness__().findOne(getPersistenceEntityClass(), __injectNumberHelper__().getLong(
					__injectFieldHelper__().getFieldValueSystemIdentifier(updatedEntity)));
			/* Copy field value from updated entity to current entity*/
			CollectionInstanceString fieldNameCollection = getEntityFieldNames();
			if(fieldNameCollection!=null && fieldNameCollection.get()!=null) {
				for(String index : fieldNameCollection.get())
					__inject__(FieldValueCopy.class).execute(updatedEntity, currentEntity, index);
			}
			//__inject__(FieldHelper.class).setFieldValueBusinessIdentifier(currentEntity, __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(updatedEntity));
			__injectBusiness__().update(currentEntity);
		}else {
			
		}
	}
	
}
