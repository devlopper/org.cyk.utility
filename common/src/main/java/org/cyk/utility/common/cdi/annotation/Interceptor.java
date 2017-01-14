package org.cyk.utility.common.cdi.annotation;

import java.io.Serializable;
import java.util.Collection;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.cyk.utility.common.Action;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;

public interface Interceptor extends Action<Object[], Object> {

	void processBefore(InvocationContext invocationContext);
	String getInputAsString(Object[] inputs);
	String getOutputAsString(Object[] outputs);
	void processAfter(InvocationContext invocationContext,String input,Object result,String output,Throwable throwable,Long startTime,Long endTime,Long numberOfMillisecond);
	
	public static class Adapter extends Action.Adapter<Object[], Object> implements Interceptor , Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public void processBefore(InvocationContext invocationContext) {}
		
		@Override
		public void processAfter(InvocationContext invocationContext,String input,Object result,String output,Throwable throwable,Long startTime,Long endTime,Long numberOfMillisecond) {}
		
		@Override
		public String getInputAsString(Object[] inputs) {
			return null;
		}
		
		@Override
		public String getOutputAsString(Object[] outputs) {
			return null;
		}
		
		public Adapter() {
			super("Intercept", Object[].class, null, Object.class, null);
		}
		
		/**/
		
		public static class Default extends Interceptor.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;

			public Default() {
				super();
			}
			
			@Override
			public String getInputAsString(Object[] inputs) {
				StringBuilder stringBuilder = new StringBuilder();
				for(Object input : inputs)
					stringBuilder.append(commonUtils.toString(input));
				return stringBuilder.toString();
			}
			
			@Override
			public String getOutputAsString(Object[] outputs) {
				StringBuilder stringBuilder = new StringBuilder();
				for(Object output : outputs)
					stringBuilder.append(commonUtils.toString(output));
				return stringBuilder.toString();
			}
		}
		
	}
	
	/**/
	
	public static abstract class Abstract extends AbstractBean implements Serializable {

		private static final long serialVersionUID = 1L;

		@AroundInvoke
		public Object method(final InvocationContext invocationContext) throws Exception {
			listenerUtils.execute(getListeners(), new ListenerUtils.VoidMethod<Interceptor>() {
				@Override
				public void execute(Interceptor listener) {
					listener.processBefore(invocationContext);
				}
			});
			final Long startTime = System.currentTimeMillis();
			final Object[] finals = new Object[2];
			try {
				finals[0] = invocationContext.proceed();
			} catch (Exception exception) {
				finals[1]  = exception;
			} finally {
				final Long endTime = System.currentTimeMillis();
				final String input = listenerUtils.getString(getListeners(), new ListenerUtils.StringMethod<Interceptor>() {
					@Override
					public String execute(Interceptor listener) {
						return listener.getInputAsString(invocationContext.getParameters());
					}
				});
				final String output = listenerUtils.getString(getListeners(), new ListenerUtils.StringMethod<Interceptor>() {
					@Override
					public String execute(Interceptor listener) {
						return listener.getOutputAsString(invocationContext.getParameters());
					}
				});
				
				listenerUtils.execute(getListeners(), new ListenerUtils.VoidMethod<Interceptor>() {
					@Override
					public void execute(Interceptor listener) {
						listener.processAfter(invocationContext,input,finals[0] ,output,(Throwable)finals[1] ,startTime,endTime,endTime - startTime);
					}
				});
			}
			if(finals[1]!=null)
				throw new RuntimeException((Throwable)finals[1]);
			return finals[0];
		}
		
		protected abstract Collection<Interceptor> getListeners();
		
		/**/
	}
}
