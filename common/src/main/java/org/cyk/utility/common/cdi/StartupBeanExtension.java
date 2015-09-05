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

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.annotation.BusinessLayer;
import org.cyk.utility.common.annotation.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartupBeanExtension implements Extension {

	private static final Logger LOGGER = LoggerFactory.getLogger(StartupBeanExtension.class);
	
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
	}
		
	public Set<Object> getReferences() {
		if(references==null){
			references = new HashSet<>();
		
			List<Bean<?>> beans = new ArrayList<>(annotationClasses.get(Deployment.class));
			Collections.sort(beans, new Comparator<Bean<?>>() {
				@Override
				public int compare(Bean<?> o1, Bean<?> o2) {
					Deployment deployment1 = o1.getBeanClass().getAnnotation(Deployment.class);
					Deployment deployment2 = o2.getBeanClass().getAnnotation(Deployment.class);
					return new Integer(deployment1.order()).compareTo(deployment2.order());
				}
			});
			
			
			LOGGER.debug("Eager Deployment of {} bean(s) Starts",beans.size());
			for (Bean<?> bean : beans) {
				Deployment deployment = bean.getBeanClass().getAnnotation(Deployment.class);
				if(Deployment.InitialisationType.EAGER.equals(deployment.initialisationType())){
					String m = "\t\t("+deployment.order()+") *"+bean.getBeanClass().getName();
					try {
						Object object = beanManager.getReference(bean, bean.getBeanClass(),beanManager.createCreationalContext(bean));
						// the call to toString() is a cheat to force the bean to be initialized
						object.toString();
						references.add(object);
						LOGGER.debug(m+" : OK");
					} catch (Exception e) {
						LOGGER.error("Cannot eager deploy "+m,e);
					}
				}
			}
			LOGGER.debug("Eager Deployment Ends");
		}
			
		return references;
	}
	
	/**/
	
	public Collection<Bean<?>> beans(Class<? extends Annotation> aClass,String aPackageBaseName){
		@SuppressWarnings("unchecked")
		Collection<Bean<?>> beans = (Collection<Bean<?>>) (annotationClasses.get(aClass)==null?new HashSet<>():annotationClasses.get(aClass));
		Collection<Bean<?>> r = new HashSet<>();
		for(Bean<?> bean : beans){
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