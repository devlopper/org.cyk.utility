package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.common.ListenerUtils;

@Singleton
public class InstanceHelper extends AbstractHelper implements Serializable  {

	private static final long serialVersionUID = 1L;

	public Object getIdentifier(final Object instance){
		return listenerUtils.getObject(Listener.COLLECTION, new ListenerUtils.ObjectMethod<Listener>() {
			@Override
			public Object execute(Listener listener) {
				return listener.getIdentifier(instance);
			}
			
			@Override
			public Object getNullValue() {
				if(instance!=null)
					return instance.toString();
				return super.getNullValue();
			}
		});
	}
	
	public <T> T getIfNotNullElseDefault(Class<T> valueClass,T value,T defaultValue){
		return value == null ? defaultValue : value;
	}
	
	public <T> T getIfNotNullElseDefault(T value,T defaultValue){
		return value == null ? defaultValue : value;
	}

	public String getMethodName(MethodType type,String suffix){
		return type.name().toLowerCase()+StringUtils.substring(suffix, 0,1).toUpperCase()+StringUtils.substring(suffix, 1).toLowerCase();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T callGetMethod(Object instance,Class<T> resultClass,String name){
		try {
			return (T) MethodUtils.invokeMethod(instance, getMethodName(MethodType.GET, name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> Collection<T> callGetMethod(Collection<?> instances,Class<T> resultClass,String name){
		Collection<T> result = new ArrayList<>();
		for(Object instance : instances){
			T value = (T) callGetMethod(instance, resultClass, name);
			result.add(value);
		}
		return result;
	}

	public <T> T instanciateOne(Class<T> aClass){
		try {
			return aClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**/
	
	public static interface Listener extends AbstractHelper.Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		Object getIdentifier(Object instance);
		
		/**/
		
		public static class Adapter extends AbstractHelper.Listener.Adapter.Default implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object getIdentifier(Object instance) {
				return null;
			}
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}
			
		}
	}

	/**/
	
	public static enum MethodType {
		GET
		,SET
	}

	/**/
	
	
}
