package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.type.BooleanHelper;
import org.cyk.utility.value.ValueHelper;

public abstract class AbstractBusinessFunctionCreatorImpl extends AbstractBusinessFunctionTransactionImpl implements BusinessFunctionCreator, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}

	@Override
	protected void __execute__(SystemAction action) {
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(__entities__))) {
			__inject__(Persistence.class).createMany(__entities__);
		}
	}
	
	@Override
	protected Collection<Object> __getEntities__() {
		Collection<Object> collection = super.__getEntities__();
		Collection<Object> entities = null;
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(collection)))
			for(Object index : collection) {
				Object isCreateIfSystemIdentifierIsBlank = Properties.getFromPath(getProperties(), Properties.IS_CREATE_IF_SYSTEM_IDENTIFIER_IS_BLANK);
				if(isCreateIfSystemIdentifierIsBlank == null) {
					if(entities == null)
						entities = new ArrayList<>();
					entities.add(index);
				}else {
					if(Boolean.TRUE.equals(BooleanHelper.get(isCreateIfSystemIdentifierIsBlank))) {
						Object identifier = FieldHelper.readSystemIdentifier(index);
						if(ValueHelper.isBlank(identifier)) {
							if(entities == null)
								entities = new ArrayList<>();
							entities.add(index);
						}
					}
				}	
			}
		return entities;
	}
	
	@Override @Transactional
	public BusinessFunctionCreator execute() {
		return (BusinessFunctionCreator) super.execute();
	}
	
}
