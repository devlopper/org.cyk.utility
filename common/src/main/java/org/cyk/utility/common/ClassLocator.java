package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

@Getter @Setter
public class ClassLocator extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -5858728987562936549L;

	private String classType;
	private final Collection<Listener> classLocatorListeners = new ArrayList<>();
	private Map<Class<?>,Class<?>> cache = new HashMap<>();
	
	@SuppressWarnings("rawtypes")
	public Class<?> locate(final Class<?> basedClass){
		Class<?> clazz = cache.get(basedClass);
		if(clazz==null)
			clazz = listenerUtils.getValue(Class.class, classLocatorListeners, new ListenerUtils.ResultMethod<Listener, Class>(){
	
				@Override
				public Class execute(Listener listener) {
					return listener.locate(basedClass);
				}
	
				@Override
				public Class getNullValue() {
					return null;
				}
				
			});
		
		if(clazz==null)
			logClassIsNull(basedClass);
		else
			cache.put(basedClass, clazz);
		return clazz;
	}
	
	protected void logClassIsNull(Class<?> basedClass){
		logWarning(getLogClassIsNullMessageFormat(), basedClass);
	}
	
	protected String getLogClassIsNullMessageFormat(){
		return (StringUtils.isBlank(classType) ? Constant.EMPTY_STRING : (classType+Constant.CHARACTER_SPACE))
				+"class cannot be found based on {}";
	}
	
	@SuppressWarnings("unchecked")
	public <T> T injectLocated(Class<T> aClass) {
		return (T) inject(locate(aClass));
	}
	
	/**/
	
	public static interface Listener {
		
		Class<?> locate(Class<?> basedClass);
		
		/**/
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable {

			private static final long serialVersionUID = -4338231956722553859L;

			@Override
			public Class<?> locate(Class<?> basedClass) {
				return null;
			}
			
		}
	}
}
