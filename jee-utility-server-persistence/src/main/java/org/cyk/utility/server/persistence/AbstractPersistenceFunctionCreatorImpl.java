package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import org.cyk.utility.field.FieldsGetter;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldInstance;
import org.cyk.utility.field.FieldInstancesRuntime;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.Fields;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceFunctionCreatorImpl extends AbstractPersistenceFunctionTransactionImpl implements PersistenceFunctionCreator, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}
	
	@Override
	protected Collection<Object> __getEntities__() {
		Collection<Object> entities = super.__getEntities__();
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(entities))) {
			for(Object index : entities) {
				if(index instanceof AbstractIdentifiedByString) {
					if(((AbstractIdentifiedByString)index).getIdentifier() == null)
						((AbstractIdentifiedByString)index).setIdentifier(UUID.randomUUID().toString());
				}else {
					Object identifierSystemValue = __inject__(FieldHelper.class).getFieldValueSystemIdentifier(index);
					if(identifierSystemValue == null) {
						Fields fields = __inject__(FieldsGetter.class).setValueUsageType(ValueUsageType.SYSTEM).setFieldName(FieldName.IDENTIFIER).execute().getOutput();
						FieldInstance identifierSystemFieldInstance = __inject__(FieldInstancesRuntime.class).get(index.getClass(), fields.getFirst().getName());
						if(Boolean.TRUE.equals(identifierSystemFieldInstance.getIsGeneratable())) {
							if(String.class.equals(identifierSystemFieldInstance.getType()))
								identifierSystemValue = UUID.randomUUID().toString();
							else
								__injectThrowableHelper__().throwRuntimeException("cannot generate value of type "+identifierSystemFieldInstance.getType());
						}	
					}
				}
			}
		}
		return entities;
	}
	
}
