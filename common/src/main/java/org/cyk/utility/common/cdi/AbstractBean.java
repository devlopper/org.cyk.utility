package org.cyk.utility.common.cdi;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
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
	
	protected void pause(long millisecond){
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**/
	
	protected String __info__(String message,int lineLength){
		return StringUtils.rightPad(message, lineLength);
	}
	
	protected void __writeInfo__(String message,int lineLength,String separator){
		if(separator==null)
			System.out.println(__info__(message, lineLength));
		else
			System.out.print(__info__(message, lineLength)+separator);
	}
	
	protected void __writeInfo__(String message,int lineLength){
		__writeInfo__(message, lineLength, null);
	}
	
	protected void __writeInfoStart__(String message){
		__writeInfo__(message, 40, " : ");
	}
	
	protected void __writeInfoStartOK__(){
		__writeInfo__("OK");
	}
	
	protected void __writeInfo__(String message){
		__writeInfo__(message, 0, null);
	}
	
	protected void debug(Object object){
		System.out.println("------------------------------------- Debug -----------------------------");
		System.out.println(ToStringBuilder.reflectionToString(object, ToStringStyle.MULTI_LINE_STYLE));
	}
	
	/**/
	
	protected Execution __watchExecute__(String name,Runnable aRunnable){
		Execution execution;
		execution = new Execution(name);
		execution.start();
		aRunnable.run();
		execution.end();
		return execution;
	}
	
	@Getter
	protected class Execution{
		private String name;
		private long startTimestamp;
		private long duration;
		
		public void start(){
			__writeInfoStart__(name);
			startTimestamp = System.currentTimeMillis();
		}
		
		public void end(){
			duration = System.currentTimeMillis()-startTimestamp;
			__writeInfoStartOK__();
		}

		public Execution(String name) {
			super();
			this.name = name;
		}
	}
}
