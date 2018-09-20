package org.cyk.utility.system.layer;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class SystemLayerRepresentationImpl extends AbstractSystemLayerImpl implements SystemLayerRepresentation, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		getEntityLayer().setClassNameRegularExpression("representation.entities..+Dto$");
		
	}
	/*
	@Override //TODO to be renamed as getInterfaceNameFromPersistenceEntityClassName
	public String getInterfaceNameFromEntityClassName(String entityClassName) {
		//TODO DOT should be defined as constant somewhere. maybe in class helper
		return super.getInterfaceNameFromEntityClassName(StringUtils.replace(entityClassName, CharacterConstant.DOT.toString()
				+__inject__(SystemLayerPersistence.class).getIdentifier().toString().toLowerCase()+CharacterConstant.DOT.toString()
				, CharacterConstant.DOT.toString()+getIdentifier().toString().toLowerCase()+CharacterConstant.DOT.toString()));
	}*/
}
