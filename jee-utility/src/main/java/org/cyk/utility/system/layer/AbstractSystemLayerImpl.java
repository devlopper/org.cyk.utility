package org.cyk.utility.system.layer;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;

public abstract class AbstractSystemLayerImpl extends AbstractSingleton implements SystemLayer, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setEntityPackageNameToken(".entities.");
		setInterfacePackageNameToken(".api.");
		setInterfaceNameSuffix(getIdentifier());
	}
	
	@Override
	protected String __getIdentifier__() {
		return StringUtils.substringAfter(super.__getIdentifier__(), SystemLayer.class.getSimpleName());
	}
	
	@Override
	public SystemLayer setEntityPackageNameToken(String token) {
		getProperties().setFromPath(new Object[]{Properties.ENTITY,Properties.PACKAGE,Properties.NAME,Properties.TOKEN}, token);
		return this;
	}
	
	@Override
	public String getEntityPackageNameToken() {
		return (String) getProperties().getFromPath(Properties.ENTITY,Properties.PACKAGE,Properties.NAME,Properties.TOKEN);
	}
	
	@Override
	public SystemLayer setInterfacePackageNameToken(String token) {
		getProperties().setFromPath(new Object[]{Properties.INTERFACE,Properties.PACKAGE,Properties.NAME,Properties.TOKEN}, token);
		return this;
	}

	@Override
	public String getInterfacePackageNameToken() {
		return (String) getProperties().getFromPath(Properties.INTERFACE,Properties.PACKAGE,Properties.NAME,Properties.TOKEN);
	}
	
	@Override
	public SystemLayer setInterfaceNameSuffix(String suffix) {
		getProperties().setFromPath(new Object[]{Properties.INTERFACE,Properties.NAME,Properties.SUFFIX}, suffix);
		return this;
	}
	
	@Override
	public String getInterfaceNameSuffix() {
		return (String) getProperties().getFromPath(Properties.INTERFACE,Properties.NAME,Properties.SUFFIX);
	}
	
	@Override
	public String getInterfaceNameFromEntityClassName(String entityClassName) {
		return StringUtils.replace(entityClassName, getEntityPackageNameToken(), getInterfacePackageNameToken())+getInterfaceNameSuffix();
	}
	
	@Override
	public Class<?> getInterfaceClassFromEntityClassName(String entityClassName) {
		return __inject__(ClassHelper.class).getByName(getInterfaceNameFromEntityClassName(entityClassName));
	}
	
	
	@Override
	public Class<?> getInterfaceClassFromEntityClassName(Class<?> entityClass) {
		return getInterfaceClassFromEntityClassName(entityClass.getName());
	}
	
	@Override
	public <T> T injectInterfaceClassFromEntityClassName(Class<?> entityClass, Class<T> type) {
		T object = null;
		Class<?> interfaceClass = getInterfaceClassFromEntityClassName(entityClass);
		if(interfaceClass == null){
			//TODO log warning
		}else{
			object = (T) __inject__(interfaceClass);
		}
		
		if(object == null){
			//TODO log warning
		}
		
		return object;
	}
	
	@Override
	public Object injectInterfaceClassFromEntityClassName(Class<?> entityClass) {
		return injectInterfaceClassFromEntityClassName(entityClass, Object.class);
	}
}
