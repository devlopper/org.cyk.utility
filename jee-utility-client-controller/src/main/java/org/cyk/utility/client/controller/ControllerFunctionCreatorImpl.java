package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.server.representation.Constant;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;

public class ControllerFunctionCreatorImpl extends AbstractControllerFunctionImpl implements ControllerFunctionCreator , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}
	
	@Override
	protected void __executeRepresentation__() {
		if(__representation__ instanceof RepresentationEntity<?>)
			//FIXME it is making body parsing error
			__response__ = ((RepresentationEntity<Object>)__representation__).createMany(__representationEntities__,null);
			//for(Object index : __representationEntities__)
			//	__response__ = ((RepresentationEntity<?,Object,?>)__representation__).createOne(index);
	}
	
	@Override
	protected void __listenExecuteServiceFound__() {
		super.__listenExecuteServiceFound__();
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(__representationEntities__))) {
			Collection<String> systemIdentifiers = ResponseHelper.getHeaderAndDisjoin(__response__, Constant.RESPONSE_HEADER_ENTITY_IDENTIFIER_SYSTEM);
			Collection<String> businessIdentifiers = ResponseHelper.getHeaderAndDisjoin(__response__, Constant.RESPONSE_HEADER_ENTITY_IDENTIFIER_BUSINESS);
			Integer count = 0;
			for(Object index : __entities__) {
				//if(index instanceof DataIdentifiedByString)
				//	((DataIdentifiedByString)index).setIdentifier(identifier);
				if(__entityClassSystemIdentifierField__ != null)
					FieldHelper.write(index, __entityClassSystemIdentifierField__, CollectionHelper.getElementAt(systemIdentifiers, count));
				if(__entityClassBusinessIdentifierField__ != null)
					FieldHelper.write(index, __entityClassBusinessIdentifierField__, CollectionHelper.getElementAt(businessIdentifiers, count));
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
