package org.cyk.utility.common;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CdiTestNo  {
	
	@Inject 
	private MyBean myBean;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(MyBean.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	
	@Test
	public void inject1(){
		Assert.assertTrue(myBean!=null);
	}
	
	@Test
	public void inject2(){
		System.out.println(myBean = getReference(CDI.current().getBeanManager(), MyBean.class));
		Assert.assertTrue( myBean!=null);
	}
	
	protected <T> T getReference(BeanManager beanManager,Class<T> aClass){
		Set<Bean<?>> beans = beanManager.getBeans(aClass);
		@SuppressWarnings("unchecked")
		Bean<T> bean = (Bean<T>) beanManager.resolve(beans);
		CreationalContext<T> context = beanManager.createCreationalContext(bean);
		@SuppressWarnings("unchecked")
		T result = (T) beanManager.getReference(bean, aClass, context);
		return result;
	}
	
	/*
	@Test
	public void inject3(){
		BeanManager beanManager = CDI.current().getBeanManager();
		Bean<MyBean> bean = (Bean<MyBean>) beanManager.resolve(beanManager.getBeans(MyBean.class));
		MyBean myBean = beanManager.getContext(bean.getScope()).get(bean, beanManager.createCreationalContext(bean));
		Assert.assertTrue( myBean!=null);
	}*/


}
