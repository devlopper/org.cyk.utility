package org.cyk.utility.__kernel__.object;

import java.util.Map;

import org.cyk.utility.__kernel__.cache.CacheManager;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;

public interface Builder<OBJECT> {

	static <OBJECT> OBJECT build(Class<OBJECT> klass,Map<Object,Object> arguments) {
		if(klass == null)
			return null;		
		Configurator<OBJECT> configurator = (Configurator<OBJECT>) MapHelper.readByKey(arguments, Configurator.class);		
		if(configurator == null) {
			klass = (Class<OBJECT>) ClassHelper.getByName(klass.getName());//to force class static initialization
			configurator = Configurator.get(klass);
		}
		Object cacheIdentifier = MapHelper.readByKey(arguments, "identifier");
		if(configurator != null && Boolean.TRUE.equals(configurator.getIsCachable())) {
			if(cacheIdentifier == null)
				LogHelper.logWarning(String.format("no cache identifier found for %s in arguments %s", klass,arguments), klass);
			else {
				OBJECT object = (OBJECT) CacheManager.getInstance().get(klass, cacheIdentifier);
				if(object != null)
					return object;
			}
		}
		OBJECT object = ClassHelper.instanciate(klass);
		if(object == null)
			return null;
		Listener<OBJECT> listener = (Listener<OBJECT>) MapHelper.readByKey(arguments, Listener.class);
		if(listener != null)
			listener.listenBefore(object, arguments);		
		if(configurator != null)
			configurator.configure(object, arguments);	
		if(listener != null)
			listener.listenAfter(object, arguments);
		if(cacheIdentifier != null && Boolean.TRUE.equals(configurator.getIsCachable()))
			CacheManager.getInstance().set(klass, cacheIdentifier, object);
		return object;
	}
	
	static <OBJECT> OBJECT build(Class<OBJECT> klass) {
		if(klass == null)
			return null;
		return build(klass,null);
	}
	
	/**/
	
	public static interface Listener<OBJECT> {
		
		void listenBefore(OBJECT object,Map<Object,Object> arguments);
		void listenAfter(OBJECT object,Map<Object,Object> arguments);
		
		/**/
		
		public static abstract class AbstractImpl<OBJECT> extends AbstractObject implements Listener<OBJECT> {

			@Override
			public void listenBefore(OBJECT object, Map<Object, Object> arguments) {}

			@Override
			public void listenAfter(OBJECT object, Map<Object, Object> arguments) {}
			
		}
	}

	/**/
	
}
