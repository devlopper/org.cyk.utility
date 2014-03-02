package org.cyk.utility.common;

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
	
	/*
	@Test
	public void inject1(){
		Assert.assertTrue(myBean!=null);
	}
	
	@Test
	public void inject2(){
		CDI.setCDIProvider(null);
		System.out.println(myBean);
		BeanManager beanManager = CDI.current().getBeanManager();
		Bean<MyBean> bean = (Bean<MyBean>) beanManager.resolve(beanManager.getBeans(MyBean.class));
		MyBean myBean = (MyBean) beanManager.getReference(bean, bean.getBeanClass(), beanManager.createCreationalContext(bean));
		Assert.assertTrue( myBean!=null);
	}
	
	@Test
	public void inject3(){
		BeanManager beanManager = CDI.current().getBeanManager();
		Bean<MyBean> bean = (Bean<MyBean>) beanManager.resolve(beanManager.getBeans(MyBean.class));
		MyBean myBean = beanManager.getContext(bean.getScope()).get(bean, beanManager.createCreationalContext(bean));
		Assert.assertTrue( myBean!=null);
	}*/


}
