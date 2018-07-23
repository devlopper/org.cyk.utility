package org.cyk.utility.system.layer;

import java.io.Serializable;

import javax.inject.Singleton;

import org.codehaus.plexus.util.StringUtils;
import org.cyk.utility.character.CharacterConstant;

@Singleton
public class SystemLayerBusinessImpl extends AbstractSystemLayerImpl implements SystemLayerBusiness, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getInterfaceNameFromEntityClassName(String entityClassName) {
		//TODO DOT should be defined as constant somewhere. maybe in class helper
		return super.getInterfaceNameFromEntityClassName(StringUtils.replace(entityClassName, CharacterConstant.DOT.toString()
				+__inject__(SystemLayerPersistence.class).getIdentifier().toString().toLowerCase()+CharacterConstant.DOT.toString()
				, CharacterConstant.DOT.toString()+getIdentifier().toString().toLowerCase()+CharacterConstant.DOT.toString()));
	}
}
