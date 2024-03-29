package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.field.FieldInstancesRuntime;
import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString;

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
				if(index instanceof IdentifiableSystem && FieldHelper.readSystemIdentifier(index) == null) {
					((IdentifiableSystem<String>)index).setSystemIdentifier(UUID.randomUUID().toString());
				}
				
				if(__entityClassSystemIdentifierField__ != null) {
					//Generate value if needed
					Object value = FieldHelper.read(index, __entityClassSystemIdentifierField__);
					if(value == null) {
						FieldInstance fieldInstance = __inject__(FieldInstancesRuntime.class).get(index.getClass(), __entityClassSystemIdentifierField__.getName());
						if(Boolean.TRUE.equals(fieldInstance.getIsGeneratable())) {
							if(String.class.equals(fieldInstance.getType())) {
								if(index instanceof AbstractIdentifiedByString)
									value = UUID.randomUUID().toString();
							}else
								throw new RuntimeException("cannot generate value of type "+fieldInstance.getType());
						}
						if(value != null)
							FieldHelper.write(index, __entityClassSystemIdentifierField__, value);
					}	
				}
			}
		}
		return entities;
	}
	
}
