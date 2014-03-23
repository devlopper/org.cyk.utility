package org.cyk.utility.common.cdi;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.utility.common.CommonUtils;

public class AbstractBean implements Serializable {

	private static final long serialVersionUID = -2448439169984218703L;

	protected CommonUtils commonUtils = CommonUtils.getInstance();
	
	@PostConstruct
	public void postConstruct(){
		beforeInitialisation();
		initialisation();
		afterInitialisation();
	}
	
	protected void beforeInitialisation(){}
	
	protected void initialisation(){}

	protected void afterInitialisation(){}
	
	protected <T> T getReference(BeanManager aBeanManager,Class<T> aClass){
		Set<Bean<?>> beans = aBeanManager.getBeans(aClass);
		@SuppressWarnings("unchecked")
		Bean<T> bean = (Bean<T>) aBeanManager.resolve(beans);
		CreationalContext<T> context = aBeanManager.createCreationalContext(bean);
		@SuppressWarnings("unchecked")
		T result = (T) aBeanManager.getReference(bean, aClass, context);
		return result;
	}
	
	protected <T> T getReference(Class<T> aClass){
		return getReference(getBeanManager(), aClass);
	}
	
	protected BeanManager getBeanManager(){
		return CDI.current().getBeanManager();
	}
	
	/**/
	
	protected void debug(Object object){
		System.out.println("------------------------------------- Debug -----------------------------");
		System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
	}
}
