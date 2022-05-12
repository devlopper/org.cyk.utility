package org.cyk.utility.__kernel__;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.BeforeDestroyed;
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
			//__logFine__(String.format("%s initialized", getClass()));	
			INITIALIZED.add(getClass());
			DESTROYED.remove(getClass());
		}
	}
	
	public abstract void __initialize__(Object object);
	
    public void destroy(@Observes @BeforeDestroyed(ApplicationScoped.class) Object object) {
    	if(!DESTROYED.contains(getClass())) {
    		__destroy__(object);
    		//__logFine__(String.format("%s destroyed", getClass()));	
        	DESTROYED.add(getClass());
    		INITIALIZED.remove(getClass());
    	}
    }
    
    public abstract void __destroy__(Object object);
	
    /**/
    
    public static void initialize(Class<?> klass,Runnable runnable) {
		if(klass == null || isInitialized(klass))
			return;
		runnable.run();
	}
    
    public static Boolean isInitialized(Class<?> klass) {
		return INITIALIZED.contains(klass);
	}
	
	public static Boolean isNotInitialized(Class<?> klass) {
		return !INITIALIZED.contains(klass);
	}
	
	public static Boolean isDestroyed(Class<?> klass) {
		return DESTROYED.contains(klass);
	}
	
	public static Boolean isNotDestroyed(Class<?> klass) {
		return !DESTROYED.contains(klass);
	}
    
    protected static void __setQualifiersClasses__(Class<?> clazz,Collection<Class<?>> qualifiersClasses) {
		DependencyInjection.setQualifiersClasses(clazz, qualifiersClasses);	
	}
	
    protected static void __setQualifiersClasses__(Class<?> clazz,Class<?>...qualifiersClasses) {
		DependencyInjection.setQualifiersClasses(clazz, qualifiersClasses);	
	}
    
    protected static void __setQualifierClassTo__(Class<?> qualifierClass,Class<?>...classes) {
    	DependencyInjection.setQualifierClassTo(qualifierClass, classes);
    }
}
