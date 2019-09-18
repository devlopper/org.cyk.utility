package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.field.FieldHelperImpl;
import org.cyk.utility.server.representation.Constant;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemActionCreate;

public class ControllerFunctionCreatorImpl extends AbstractControllerFunctionImpl implements ControllerFunctionCreator , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}
	
	@Override
	protected void __executeRepresentation__() {
		if(__representation__ instanceof RepresentationEntity<?, ?, ?>)
			__response__ = ((RepresentationEntity<?,Object,?>)__representation__).createMany(__representationEntities__,null);
	}
	
	@Override
	protected void __listenExecuteServiceFound__() {
		super.__listenExecuteServiceFound__();
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(__representationEntities__))) {
			Collection<String> systemIdentifiers = __injectResponseHelper__().getHeaderAndDisjoin(__response__, Constant.RESPONSE_HEADER_ENTITY_IDENTIFIER_SYSTEM);
			Collection<String> businessIdentifiers = __injectResponseHelper__().getHeaderAndDisjoin(__response__, Constant.RESPONSE_HEADER_ENTITY_IDENTIFIER_BUSINESS);
			Integer count = 0;
			for(Object index : __entities__) {
				//if(index instanceof DataIdentifiedByString)
				//	((DataIdentifiedByString)index).setIdentifier(identifier);
				if(__entityClassSystemIdentifierField__ != null)
					FieldHelperImpl.__write__(index, __entityClassSystemIdentifierField__, CollectionHelperImpl.__getElementAt__(systemIdentifiers, count));
				if(__entityClassBusinessIdentifierField__ != null)
					FieldHelperImpl.__write__(index, __entityClassBusinessIdentifierField__, CollectionHelperImpl.__getElementAt__(businessIdentifiers, count));
				count++;
			}	
		}
	}
	
	@Override
	public ControllerFunctionCreator addActionEntities(Object... entities) {
		return (ControllerFunctionCreator) super.addActionEntities(entities);
	}
	
	@Override
	public ControllerFunctionCreator setActionEntityClass(Class<?> entityClass) {
		return (ControllerFunctionCreator) super.setActionEntityClass(entityClass);
	}

}
