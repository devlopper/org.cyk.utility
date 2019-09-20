package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemActionUpdate;

public class ControllerFunctionModifierImpl extends AbstractControllerFunctionImpl implements ControllerFunctionModifier , Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionUpdate.class));
	}
	
	@Override
	protected void __executeRepresentation__() {
		String fields = (String) getProperty(Properties.FIELDS);//TODO get logic from centralized function
		if(__representation__ instanceof RepresentationEntity<?, ?, ?>) {
			//FIXME it is making body parsing error
			__response__ = ((RepresentationEntity<?,Object,?>)__representation__).updateMany(__representationEntities__,fields);
			//for(Object index : __representationEntities__)
			//	__response__ = ((RepresentationEntity<?,Object,?>)__representation__).updateOne(index,fields);
		}
	}
	
	@Override
	public ControllerFunctionModifier addActionEntities(Object... entities) {
		return (ControllerFunctionModifier) super.addActionEntities(entities);
	}
	
	@Override
	public ControllerFunctionModifier setActionEntityClass(Class<?> entityClass) {
		return (ControllerFunctionModifier) super.setActionEntityClass(entityClass);
	}

}
