package org.cyk.utility.common.cdi;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.annotation.BusinessLayer;
import org.cyk.utility.common.annotation.Deployment;

@Log
public class StartupBeanExtension implements Extension {

	@Getter private Set<Class<?>> classes = new HashSet<>();
	@Getter private Map<Class<? extends Annotation>, Set<Bean<?>>> annotationClasses = new HashMap<>();
	@Getter private Map<Class<?>, Set<Bean<?>>> implementationClasses = new HashMap<>();
	
	private Set<Object> references;
	
	protected BeanManager beanManager;
	
	public StartupBeanExtension() {
		addAnnotation(Deployment.class);
		addAnnotation(BusinessLayer.class);
	}

	<X> void processBean(@Observes ProcessBean<X> event) {
		//classes.add(event.getBean().getBeanClass());
		for(Entry<Class<? extends Annotation>, Set<Bean<?>>> entry : annotationClasses.entrySet())
			if (event.getAnnotated().isAnnotationPresent(entry.getKey())) {
				addBean(entry.getKey(),event.getBean());
			}
		
		for(Entry<Class<?>, Set<Bean<?>>> entry : implementationClasses.entrySet())
			if (entry.getKey().isInstance(event.getBean().getBeanClass())) {
				addBeanInterface(entry.getKey(),event.getBean());
			}
	}
	
	void afterDeploymentValidation(@Observes AfterDeploymentValidation event,BeanManager manager) {
		this.beanManager = manager;
		/*for (Bean<?> bean : annotationClasses.get(Deployment.class)) {
			Deployment deployment = bean.getBeanClass().getAnnotation(Deployment.class);
			if(Deployment.InitialisationType.EAGER.equals(deployment.initialisationType())){
				//reference(bean);
			}
		}*/
	}
		
	public Set<Object> getReferences() {
		if(references==null){
			references = new HashSet<>();
			log.info("Eager Deployment Starts");
			List<Bean<?>> beans = new ArrayList<>(annotationClasses.get(Deployment.class));
			/*Collections.sort(beans, new Comparator<Bean<?>>() {
				@Override
				public int compare(Bean<?> o1, Bean<?> o2) {
					Deployment deployment1 = o1.getBeanClass().getAnnotation(Deployment.class);
					Deployment deployment2 = o2.getBeanClass().getAnnotation(Deployment.class);
					return new Integer(deployment1.order()).compareTo(deployment2.order());
				}
			});*/
			for (Bean<?> bean : beans) {
				Deployment deployment = bean.getBeanClass().getAnnotation(Deployment.class);
				if(Deployment.InitialisationType.EAGER.equals(deployment.initialisationType())){
					Object object = beanManager.getReference(bean, bean.getBeanClass(),beanManager.createCreationalContext(bean));
					// the call to toString() is a cheat to force the bean to be initialized
					object.toString();
					log.info("*          ("+deployment.order()+") *"+object.getClass().getName());
					references.add(object);
				}
			}
			log.info("Eager Deployment Ends");
		}
			
		return references;
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
	
	private void addBeanInterface(Class<?> aClass,Bean<?> aBean){
		implementationClasses.get(aClass).add(aBean);
	}
	
	
}