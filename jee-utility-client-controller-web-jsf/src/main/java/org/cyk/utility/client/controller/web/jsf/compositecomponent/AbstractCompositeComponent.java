package org.cyk.utility.client.controller.web.jsf.compositecomponent;

import java.io.Serializable;

import javax.faces.component.UINamingContainer;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.clazz.ClassHelper;

public abstract class AbstractCompositeComponent<T> extends UINamingContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public AbstractCompositeComponent() {
		clazz = (Class<T>) DependencyInjection.inject(ClassHelper.class).getParameterAt(getClass(), 0, Object.class);
	}
	
	public String getIdentifier() {
		String identifier = __getString__(Property.identifier);
		if(identifier == null) {
			Object object = getValue();
			if(object instanceof AbstractObject) {
				Object identifierObject = ((AbstractObject)object).getIdentifier();
				if(identifierObject!=null)
					identifier = identifierObject.toString();
			}
			if(identifier!=null)
				setIdentifier(identifier);
		}
        return identifier;
    }

    public void setIdentifier(String identifier) {
    	__set__(Property.identifier, identifier);
    } 
	
    public T getValue() {
        return __get__(Property.value, clazz);
    }

    public void setValue(T value) {
    	__set__(Property.value, value);
    } 
    
    public String getFor_() {
    	String value = __getString__(Property.for_);
		if(value == null) {
			Object object = getValue();
			if(object instanceof AbstractObject) {
				Object forObject = ((AbstractObject)object).getProperties().getFor();
				if(forObject!=null)
					value = forObject.toString();
			}
			if(value!=null)
				setFor_(value);
		}
        return value;
    	// return __getString__(Property.for_);
    }

    public void setFor_(String for_) {
    	__set__(Property.for_, for_);
    } 

    /**/
    
    private String __getString__(Property property) {
    	return __get__(property, String.class);
    }
     
    @SuppressWarnings("unchecked")
	private <C> C __get__(Property property,Class<C> aClass) {
        return (C) getStateHelper().eval(property);
    }

    private void __set__(Property property,Object value) {
        getStateHelper().put(property, value);
    } 
}