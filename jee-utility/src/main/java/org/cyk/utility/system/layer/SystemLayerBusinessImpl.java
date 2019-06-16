package org.cyk.utility.system.layer;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.clazz.ClassHelper;

@Singleton
public class SystemLayerBusinessImpl extends AbstractSystemLayerImpl implements SystemLayerBusiness, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getInterfaceNameFromPersistenceEntityClassName(String persistenceEntityClassName) {
		//TODO DOT should be defined as constant somewhere. maybe in class helper
		return getInterfaceNameFromEntityClassName(StringUtils.replace(persistenceEntityClassName, ConstantCharacter.DOT.toString()
				+__inject__(SystemLayerPersistence.class).getIdentifier().toString().toLowerCase()+ConstantCharacter.DOT.toString()
				, ConstantCharacter.DOT.toString()+getIdentifier().toString().toLowerCase()+ConstantCharacter.DOT.toString()));
		
		//String name = StringUtils.replace(persistenceEntityClass.getName(), "persistence", "business");
		//return __inject__(SystemLayerBusiness.class).injectInterfaceClassFromEntityClassName(name,BusinessEntity.class);
	}

	@Override
	public Class<?> getInterfaceClassFromPersistenceEntityClassName(String persistenceEntityClassName) {
		return __inject__(ClassHelper.class).getByName(getInterfaceNameFromPersistenceEntityClassName(persistenceEntityClassName));
	}

	@Override
	public Class<?> getInterfaceClassFromPersistenceEntityClassName(Class<?> persistenceEntityClass) {
		return persistenceEntityClass == null ? null :getInterfaceClassFromPersistenceEntityClassName(persistenceEntityClass.getName());
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
