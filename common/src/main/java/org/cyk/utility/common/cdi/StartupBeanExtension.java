package org.cyk.utility.common.cdi;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.annotation.Model;
import org.cyk.utility.common.annotation.Startup;

public class StartupBeanExtension implements Extension {

	@Getter private Set<Class<?>> classes = new HashSet<>();
	@Getter private Map<Class<? extends Annotation>, Set<Bean<?>>> annotationClasses = new HashMap<>();
	
	public StartupBeanExtension() {
		addAnnotation(Startup.class);
		addAnnotation(Model.class);
	}

	<X> void processBean(@Observes ProcessBean<X> event) {
		//classes.add(event.getBean().getBeanClass());
		for(Entry<Class<? extends Annotation>, Set<Bean<?>>> entry : annotationClasses.entrySet())
			if (event.getAnnotated().isAnnotationPresent(entry.getKey())) {
				addBean(entry.getKey(),event.getBean());
			}
	}

	void afterDeploymentValidation(@Observes AfterDeploymentValidation event,BeanManager manager) {
		for (Bean<?> bean : annotationClasses.get(Startup.class)) {
			// the call to toString() is a cheat to force the bean to be
			// initialized
			manager.getReference(bean, bean.getBeanClass(),manager.createCreationalContext(bean)).toString();
		}
	}
	
	/**/
	
	public Collection<Bean<?>> beans(Class<? extends Annotation> aClass,String aPackageBaseName){
		@SuppressWarnings("unchecked")
		Collection<Bean<?>> beans = (Collection<Bean<?>>) (annotationClasses.get(aClass)==null?new HashSet<>():annotationClasses.get(aClass));
		Collection<Bean<?>> r = new HashSet<>();
		for(Bean<?> bean : beans){
			//System.out.println(bean.getBeanClass().getName()+" - "+aPackageBaseName+" - "+bean.getBeanClass().getName().startsWith(aPackageBaseName));
			if(StringUtils.isEmpty(aPackageBaseName) || bean.getBeanClass().getName().startsWith(aPackageBaseName))
				r.add(bean);
		}
		return r;
	}
	
	public Collection<Bean<?>> beans(Class<? extends Annotation> aClass){
		return beans(aClass,null);
	}
	
	private void addAnnotation(Class<? extends Annotation> aClass){
		annotationClasses.put(aClass, new HashSet<Bean<?>>());
	}
	
	private void addBean(Class<?> anAnnotationClass,Bean<?> aBean){
		annotationClasses.get(anAnnotationClass).add(aBean);
	}
}