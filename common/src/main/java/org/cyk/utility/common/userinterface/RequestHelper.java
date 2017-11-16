package org.cyk.utility.common.userinterface;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ClassHelper;

@Singleton
public class RequestHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static RequestHelper INSTANCE;
	
	public static RequestHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new RequestHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Object get(){
		return ClassHelper.getInstance().instanciateOne(Listener.Adapter.Default.class).get();
	}
	
	public Object getUniformResourceLocator(Object request){
		return ClassHelper.getInstance().instanciateOne(Listener.Adapter.Default.class).getUniformResourceLocator(request);
	}
	
	public Object getUniformResourceLocator(){
		return ClassHelper.getInstance().instanciateOne(Listener.Adapter.Default.class).getUniformResourceLocator();
	}
	
	public Object getParameter(Object request,String name){
		return ClassHelper.getInstance().instanciateOne(Listener.Adapter.Default.class).getParameter(request,name);
	}
	
	public Object getParameter(String name){
		return ClassHelper.getInstance().instanciateOne(Listener.Adapter.Default.class).getParameter(name);
	}
	
	public String getParameterAsString(Object request,String name){
		return (String) getParameter(request,name);
	}
	
	public String getParameterAsString(String name){
		return (String) getParameter(name);
	}
	
	/**/
	
	public static interface Listener {
		
		Object get();
		String getUniformResourceLocator(Object request);
		String getUniformResourceLocator();
		Object getParameter(Object request,String name);
		Object getParameter(String name);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object get() {
				return null;
			}
			
			@Override
			public String getUniformResourceLocator(Object request) {
				return null;
			}
			
			@Override
			public String getUniformResourceLocator() {
				return null;
			}
			
			@Override
			public Object getParameter(Object request,String name) {
				return null;
			}
			
			@Override
			public Object getParameter(String name) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public Object getParameter(String name) {
					return getParameter(get(),name);
				}
				
				@Override
				public String getUniformResourceLocator() {
					return getUniformResourceLocator(get());
				}
				
			}
		}
	}
	
}
