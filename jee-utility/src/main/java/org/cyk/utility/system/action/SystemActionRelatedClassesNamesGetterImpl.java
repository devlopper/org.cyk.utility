package org.cyk.utility.system.action;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.string.AbstractStringsFunctionImpl;
import org.cyk.utility.string.Strings;

@Dependent
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
		String nameSuffix = __injectValueHelper__().defaultToIfNull(getNameSuffix(),ConstantEmpty.STRING);
		
		String systemActionName = null;
		if(systemActionClass != null) {
			systemActionName = StringUtils.substringAfter(systemActionClass.getSimpleName(), SYSTEM_ACTION);
			systemActionName = StringUtils.removeEnd(systemActionName, IMPL);
		}
		
		__add__(strings, entityClass, systemActionClass, systemActionName, nameSuffix);
		
		Class<?> extendedInterface = getExtendedInterface();
		String defaultSuffix = getDefaultSuffix();
		if(defaultSuffix == null)
			defaultSuffix = ConstantEmpty.STRING;
		
		__add__(strings, extendedInterface, systemActionClass, systemActionName, defaultSuffix);
		
		return strings;
	}
	
	private void __add__(Strings strings,Class<?> clazz,Class<?> systemActionClass,String systemActionName,String suffix) {
		if(clazz != null && systemActionClass != null) {
			//xxxx.EntityCreateXXX
			strings.add(clazz.getName()+systemActionName+suffix);
			
			if(Boolean.TRUE.equals(ClassHelper.isInstanceOfOne(systemActionClass, EDIT_CLASSES)))
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
