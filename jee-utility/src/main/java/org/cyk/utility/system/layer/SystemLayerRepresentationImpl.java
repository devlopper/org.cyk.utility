package org.cyk.utility.system.layer;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

@Singleton
public class SystemLayerRepresentationImpl extends AbstractSystemLayerImpl implements SystemLayerRepresentation, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		getEntityLayer().setClassNameRegularExpression("representation.entities..+Dto$");
		
	}
	
	@Override //TODO to be renamed as getInterfaceNameFromPersistenceEntityClassName
	public String getInterfaceNameFromEntityClassName(String entityClassName) {
		//TODO DOT should be defined as constant somewhere. maybe in class helper
		if(StringUtils.endsWith(entityClassName, "Dto"))
			entityClassName = StringUtils.substring(entityClassName, 0,entityClassName.length()-3);
		return super.getInterfaceNameFromEntityClassName(entityClassName);
	}
}
