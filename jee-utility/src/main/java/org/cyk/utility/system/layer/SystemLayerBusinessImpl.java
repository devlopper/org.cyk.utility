package org.cyk.utility.system.layer;

import java.io.Serializable;

import javax.inject.Singleton;

import org.codehaus.plexus.util.StringUtils;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.clazz.ClassHelper;

@Singleton
public class SystemLayerBusinessImpl extends AbstractSystemLayerImpl implements SystemLayerBusiness, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getInterfaceNameFromPersistenceEntityClassName(String persistenceEntityClassName) {
		//TODO DOT should be defined as constant somewhere. maybe in class helper
		return super.getInterfaceNameFromEntityClassName(StringUtils.replace(persistenceEntityClassName, CharacterConstant.DOT.toString()
				+__inject__(SystemLayerPersistence.class).getIdentifier().toString().toLowerCase()+CharacterConstant.DOT.toString()
				, CharacterConstant.DOT.toString()+getIdentifier().toString().toLowerCase()+CharacterConstant.DOT.toString()));
	}

	@Override
	public Class<?> getInterfaceClassFromPersistenceEntityClassName(String persistenceEntityClassName) {
		return __inject__(ClassHelper.class).getByName(getInterfaceNameFromPersistenceEntityClassName(persistenceEntityClassName));
	}

	@Override
	public Class<?> getInterfaceClassFromPersistenceEntityClassName(Class<?> persistenceEntityClass) {
		return getInterfaceClassFromPersistenceEntityClassName(persistenceEntityClass.getName());
	}

	@Override
	public <T> T injectInterfaceClassFromPersistenceEntityClassName(Class<?> persistenceEntityClass, Class<T> type) {
		return (T) __inject__(getInterfaceClassFromPersistenceEntityClassName(persistenceEntityClass));
	}

	@Override
	public Object injectInterfaceClassFromPersistenceEntityClassName(Class<?> persistenceEntityClass) {
		return injectInterfaceClassFromPersistenceEntityClassName(persistenceEntityClass, Object.class);
	}

	/*@Override
	public String getInterfaceNameFromEntityClassName(String persistenceEntityClassName) {
		//TODO DOT should be defined as constant somewhere. maybe in class helper
		return super.getInterfaceNameFromEntityClassName(StringUtils.replace(persistenceEntityClassName, CharacterConstant.DOT.toString()
				+__inject__(SystemLayerPersistence.class).getIdentifier().toString().toLowerCase()+CharacterConstant.DOT.toString()
				, CharacterConstant.DOT.toString()+getIdentifier().toString().toLowerCase()+CharacterConstant.DOT.toString()));
	}*/
}
