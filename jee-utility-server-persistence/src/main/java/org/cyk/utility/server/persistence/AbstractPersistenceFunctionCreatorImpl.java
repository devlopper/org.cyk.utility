package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.field.FieldInstance;
import org.cyk.utility.field.FieldInstancesRuntime;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.field.FieldValueSetter;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;

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
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(entities))) {
			for(Object index : entities) {
				if(__entityClassSystemIdentifierField__ != null) {
					//Generate value if needed
					Object value = __inject__(FieldValueGetter.class).execute(index, __entityClassSystemIdentifierField__).getOutput();
					if(value == null) {
						FieldInstance fieldInstance = __inject__(FieldInstancesRuntime.class).get(index.getClass(), __entityClassSystemIdentifierField__.getName());
						if(Boolean.TRUE.equals(fieldInstance.getIsGeneratable())) {
							if(String.class.equals(fieldInstance.getType()) || index instanceof AbstractIdentifiedByString)
								value = UUID.randomUUID().toString();
							else
								__injectThrowableHelper__().throwRuntimeException("cannot generate value of type "+fieldInstance.getType());
						}
						if(value != null)
							__inject__(FieldValueSetter.class).execute(index, __entityClassSystemIdentifierField__, value);
					}	
				}
			}
		}
		return entities;
	}
	
}
