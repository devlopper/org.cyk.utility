package org.cyk.utility.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.cyk.utility.common.helper.ArrayHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClassLocator extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -5858728987562936549L;

	private String classType;
	private final Collection<Listener> classLocatorListeners = new ArrayList<>();
	private Map<Class<?>,Class<?>> cache = new HashMap<>();
	private Boolean logClassNotFoundException = Boolean.TRUE;
	
	@SuppressWarnings("rawtypes")
	public Class<?> locate(final Class<?> basedClass){
		Class<?> clazz = null;
		if(Boolean.TRUE.equals(listenerUtils.getBoolean(classLocatorListeners, new ListenerUtils.BooleanMethod<Listener>() {
			@Override
			public Boolean execute(Listener listener) {
				return listener.isLocatable(basedClass);
			}
			@Override
			public Boolean getNullValue() {
				return Boolean.TRUE;
			}
		}))){
			clazz = cache.get(basedClass);
			if(clazz==null){
				@SuppressWarnings("unchecked")
				AbstractMethod<String[], Class<?>> getNameMethod = listenerUtils.getValue(AbstractMethod.class, classLocatorListeners, new ListenerUtils.ResultMethod<Listener, AbstractMethod>(){
		
					@Override
					public AbstractMethod execute(Listener listener) {
						return listener.getGetNameMethod();
					}
		
					@Override
					public AbstractMethod getNullValue() {
						return null;
					}
					
				});
				
				if(getNameMethod==null){
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
				}else{
					String[] names = getNames(getNameMethod,basedClass);
					if(ArrayHelper.getInstance().isNotEmpty(names))
						for(String name : names){
							if(StringUtils.isNotBlank(name)){
								try {
									clazz = Class.forName(name);
								} catch (ClassNotFoundException e) {
									clazz = listenClassNotFound(name);
									//if(clazz == null && Boolean.TRUE.equals(getLogClassNotFoundException()))
									//	logThrowable(e);
								}
								if(clazz!=null)
									break;
							}
						}
				}
			}
		}
		
		if(clazz==null)
			logClassIsNull(basedClass);
		else
			cache.put(basedClass, clazz);
		return clazz;
	}
	
	protected String[] getNames(AbstractMethod<String[], Class<?>> getNameMethod,Class<?> basedClass){
		String[] names = getNameMethod.execute(basedClass);
		return names;
	}
	
	protected Class<?> listenClassNotFound(String name){
		return null;
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
		Class<?> clazz = locate(aClass);
		if(clazz==null)
			return null;
		return (T) inject(clazz);
	}
	
	/**/
	
	public static interface Listener {
		
		Class<?> locate(Class<?> basedClass);
		
		Boolean isLocatable(Class<?> basedClass);
		
		AbstractMethod<String[], Class<?>> getGetNameMethod();
		void setGetNameMethod(AbstractMethod<String[], Class<?>> method);
		
		/**/
		
		public static abstract class AbstractGetOrgCykSystem extends AbstractMethod<String[], Class<?>> implements Serializable {
			private static final long serialVersionUID = -7213562588417233353L;
			@Override
			protected String[] __execute__(Class<?> aClass) {
				String[] names = new String[getModuleSuffixes().length];
				String systemIdentifier = StringUtils.substringBetween(aClass.getName(), Constant.PREFIX_PACKAGE_ORG_CYK_SYSTEM,Constant.CHARACTER_DOT.toString());
				String module = StringUtils.substringAfter(aClass.getName(), Constant.CHARACTER_DOT+getBaseClassPackageName()+Constant.CHARACTER_DOT);
				Integer index = 0;
				for(String suffix : getModuleSuffixes())
					names[index++] = Constant.PREFIX_PACKAGE_ORG_CYK_SYSTEM+systemIdentifier+Constant.CHARACTER_DOT+getModulePrefix()+Constant.CHARACTER_DOT+module+suffix;
				return names;
			}
			
			protected abstract String getBaseClassPackageName();
			protected abstract String getModulePrefix();
			protected abstract String[] getModuleSuffixes();
		}
		
		/**/
		
		@Getter @Setter
		public static class Adapter extends BeanAdapter implements Listener,Serializable {

			private static final long serialVersionUID = -4338231956722553859L;

			private AbstractMethod<String[], Class<?>> getNameMethod;
			
			@Override
			public Class<?> locate(Class<?> basedClass) {
				return null;
			}
			@Override
			public Boolean isLocatable(Class<?> basedClass) {
				return null;
			}
			/**/
			
			@Getter @Setter
			public static class Default extends Adapter implements Serializable {

				private static final long serialVersionUID = -4338231956722553859L;
				
			}
			
		}
	}

}
