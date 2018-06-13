package org.cyk.utility.instance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

@Singleton
@Deprecated //TODO use inject from Instance Helper
public class Singletons implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Map<Class<?>,Object> map = new HashMap<Class<?>, Object>();
	
	public Singletons add(Class<?> aClass,Object instance){
		map.put(aClass, instance);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <CLASS> CLASS get(Class<CLASS> aClass,Boolean instanciateIfNull) {
		CLASS instance = (CLASS) map.get(aClass);
		if(instance == null && Boolean.TRUE.equals(instanciateIfNull)){
			try {
				instance = aClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public <CLASS> CLASS get(Class<CLASS> aClass){
		return get(aClass, Boolean.FALSE);
	}
	
	/**/
	
	private static Singletons INSTANCE;
	
	public static Singletons getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Singletons();
		return INSTANCE;
	}
}
