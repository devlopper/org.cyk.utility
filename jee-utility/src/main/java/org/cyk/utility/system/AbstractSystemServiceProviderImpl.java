package org.cyk.utility.system;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.util.AnnotationLiteral;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.service.AbstractServiceProviderImpl;
import org.cyk.utility.service.ServiceProvider;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractSystemServiceProviderImpl extends AbstractServiceProviderImpl implements SystemServiceProvider, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isPersisted(Object object) {
		return Boolean.TRUE.equals(__injectInstanceHelper__().isPersisted(object));
	}
	
	@Override
	public ServiceProvider validateOne(Object object, SystemAction action) {
		__logTrace__("validate "+action+" on object");
		if(object != null){
			if(action == null){
				if(Boolean.TRUE.equals(isPersisted(object))){
					__validateOne__(object,null);
				}else{
					__injectThrowableHelper__().throwRuntimeException("object must be persisted");
				}
			}else {
				__validateOne__(object, action);
			}
		}
		return this;
	}
	
	protected void __validateOne__(Object object, SystemAction action){
		__injectThrowableHelper__().throwRuntimeExceptionNotYetImplemented();
	}
	
	@Override
	public ServiceProvider validateOne(Object object) {
		return validateOne(object, null);
	}
	
	protected void __validateOne__(Object object){
		__validateOne__(object, null);
	}
	
	@Override
	public ServiceProvider validateMany(Collection<?> objects, SystemAction action) {
		__validateMany__(objects, action);
		return this;
	}
	
	protected void __validateMany__(Collection<?> objects, SystemAction action){
		for(Object index : objects)
			validateOne(index, action);
	}
	
	@Override
	public ServiceProvider validateMany(Collection<?> objects) {
		__validateMany__(objects);
		return this;
	}
	
	protected void __validateMany__(Collection<?> objects){
		__validateMany__(objects, null);
	}
	
	protected SystemAction getSystemAction(Properties properties,Class<? extends SystemAction> aClass){
		return properties == null || properties.getSystemAction() == null ? __inject__(aClass) : (SystemAction) properties.getSystemAction();
	}
	
	/**/
	
	protected Strings __getFieldsFromProperties__(Properties properties) {
		Strings fields = null;
		if(properties != null) {
			Object fieldsObject = Properties.getFromPath(properties, Properties.FIELDS);
			if(fieldsObject instanceof Strings) {
				fields = (Strings) fieldsObject;
			}else if(fieldsObject instanceof Collection) {
				fields = __inject__(Strings.class).add((Collection<String>) fieldsObject);
			}else if(fieldsObject instanceof String) {
				fields = __inject__(Strings.class).add(StringUtils.split((String) fieldsObject,ConstantCharacter.COMA.toString()));
			}	
		}
		return fields;
	}
	/*
	protected Map<String,Object> __getFiltersFromProperties__(Properties properties) {
		Map<String,Object> filters = null;
		if(properties != null) {
			Object filtersObject = properties.getQueryFilters();
			if(filtersObject instanceof Strings) {
				
			}else if(filtersObject instanceof Collection) {
				
			}else if(filtersObject instanceof Map) {
				filters = (Map<String, Object>) filtersObject;
			}	
		}
		return filters;
	}
	*/
	@Override
	protected <O> O ____inject____(Class<O> aClass, AnnotationLiteral<?>... annotationLiterals) {
		O o = super.____inject____(aClass, annotationLiterals);
		if(o instanceof SystemFunction) {
			((SystemFunction)o).setCallerClass(getClass()).setCallerIdentifier(getIdentifier());
		}
		return o;
	}
}
