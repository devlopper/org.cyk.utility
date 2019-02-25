package org.cyk.utility.system.action;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.cyk.utility.string.AbstractStringsFunctionImpl;
import org.cyk.utility.string.StringConstant;
import org.cyk.utility.string.Strings;

public class SystemActionRelatedClassesNamesGetterImpl extends AbstractStringsFunctionImpl implements SystemActionRelatedClassesNamesGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<?> entityClass;
	private Class<? extends SystemAction> systemActionClass;
	private Class<?> extendedInterface;
	private String nameSuffix;
	private String defaultSuffix;
	
	@Override
	protected Strings __execute__() throws Exception {
		Strings strings = __inject__(Strings.class);
		Class<?> entityClass = getEntityClass();
		Class<? extends SystemAction> systemActionClass = getSystemActionClass();
		String nameSuffix = getNameSuffix();
		
		String systemActionName = null;
		if(systemActionClass != null) {
			systemActionName = StringUtils.substringAfter(systemActionClass.getSimpleName(), SYSTEM_ACTION);
			systemActionName = StringUtils.removeEnd(systemActionName, IMPL);
		}
		
		/*
		if(entityClass != null && systemActionClass != null) {
			//xxxx.EntityCreateXXX
			strings.add(entityClass.getName()+systemActionName+nameSuffix);
			
			if(Boolean.TRUE.equals(__injectClassHelper__().isInstanceOfOne(systemActionClass, EDIT_CLASSES)))
				//xxxx.EntityEditXXX
				strings.add(entityClass.getName()+EDIT+nameSuffix);
		}
		
		if(entityClass != null)
			//xxxx.EntityForm
			strings.add(entityClass.getName()+nameSuffix);
		*/
		__add__(strings, entityClass, systemActionClass, systemActionName, nameSuffix);
		
		Class<?> extendedInterface = getExtendedInterface();
		String defaultSuffix = getDefaultSuffix();
		if(defaultSuffix == null)
			defaultSuffix = StringConstant.EMPTY;
		/*
		if(extendedInterface!=null && systemActionClass != null) {
			//xxxx.XXXCreateDefault
			strings.add(extendedInterface.getName()+systemActionName+defaultSuffix);
			
			if(Boolean.TRUE.equals(__injectClassHelper__().isInstanceOfOne(systemActionClass, EDIT_CLASSES)))
				//xxxx.EntityEditXXX
				strings.add(extendedInterface.getName()+EDIT+defaultSuffix);
		}
		
		if(extendedInterface!=null) {
			//xxxx.XXXDefault
			strings.add(extendedInterface.getName()+defaultSuffix);
		}
		*/
		__add__(strings, extendedInterface, systemActionClass, systemActionName, defaultSuffix);
		
		return strings;
	}
	
	private void __add__(Strings strings,Class<?> clazz,Class<?> systemActionClass,String systemActionName,String suffix) {
		if(clazz != null && systemActionClass != null) {
			//xxxx.EntityCreateXXX
			strings.add(clazz.getName()+systemActionName+suffix);
			
			if(Boolean.TRUE.equals(__injectClassHelper__().isInstanceOfOne(systemActionClass, EDIT_CLASSES)))
				//xxxx.EntityEditXXX
				strings.add(clazz.getName()+EDIT+suffix);
		}
		
		if(clazz != null)
			//xxxx.EntityForm
			strings.add(clazz.getName()+suffix);
	}
	
	@Override
	public Class<?> getEntityClass() {
		return entityClass;
	}
	
	@Override
	public SystemActionRelatedClassesNamesGetter setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
		return this;
	}
	
	@Override
	public Class<? extends SystemAction> getSystemActionClass() {
		return systemActionClass;
	}
	
	@Override
	public SystemActionRelatedClassesNamesGetter setSystemActionClass(Class<? extends SystemAction> systemActionClass) {
		this.systemActionClass = systemActionClass;
		return this;
	}
	
	@Override
	public Class<?> getExtendedInterface() {
		return extendedInterface;
	}
	
	@Override
	public SystemActionRelatedClassesNamesGetter setExtendedInterface(Class<?> extendedInterface) {
		this.extendedInterface = extendedInterface;
		return this;
	}
	
	@Override
	public String getNameSuffix() {
		return nameSuffix;
	}
	
	@Override
	public SystemActionRelatedClassesNamesGetter setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
		return this;
	}
	
	@Override
	public String getDefaultSuffix() {
		return defaultSuffix;
	}
	
	@Override
	public SystemActionRelatedClassesNamesGetter setDefaultSuffix(String defaultSuffix) {
		this.defaultSuffix = defaultSuffix;
		return this;
	}
	
	/**/
	
	private static final String SYSTEM_ACTION = "SystemAction";
	private static final String IMPL = "Impl";
	private static final String EDIT = "Edit";
	private static final Class<?>[] EDIT_CLASSES = {SystemActionCreate.class,SystemActionRead.class,SystemActionUpdate.class,SystemActionDelete.class};
}
