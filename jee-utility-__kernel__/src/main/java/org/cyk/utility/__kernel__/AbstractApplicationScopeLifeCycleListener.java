package org.cyk.utility.__kernel__;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public abstract class AbstractApplicationScopeLifeCycleListener extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Set<Class<?>> INITIALIZED = new LinkedHashSet<>();
	private static final Set<Class<?>> DESTROYED = new LinkedHashSet<>();
	
	public void initialize(@Observes @Initialized(ApplicationScoped.class) Object object) {
		if(!INITIALIZED.contains(getClass())) {
			__initialize__(object);
			System.out.println(getClass()+" initialized : OK");	
			INITIALIZED.add(getClass());
		}
	}
 
	protected abstract void __initialize__(Object object);
	
    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object object) {
    	if(!DESTROYED.contains(getClass())) {
    		__destroy__(object);
        	System.out.println(getClass()+" destroyed : OK");	
    		DESTROYED.add(getClass());
    	}
    }
    
    protected abstract void __destroy__(Object object);
	
}
