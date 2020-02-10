package org.cyk.utility.__kernel__.object;

import java.util.Map;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;

public interface Builder<OBJECT> {

	@SuppressWarnings("unchecked")
	static <OBJECT> OBJECT build(Class<OBJECT> klass,Map<Object,Object> arguments) {
		if(klass == null)
			return null;
		OBJECT object = ClassHelper.instanciate(klass);
		if(object == null)
			return null;
		Listener<OBJECT> listener = (Listener<OBJECT>) MapHelper.readByKey(arguments, Listener.class);
		if(listener != null)
			listener.listenBefore(object, arguments);
		
		Configurator<OBJECT> configurator = (Configurator<OBJECT>) MapHelper.readByKey(arguments, Configurator.class);		
		if(configurator == null)
			configurator = Configurator.get(klass);
		if(configurator != null)
			configurator.configure(object, arguments);
		
		if(listener != null)
			listener.listenAfter(object, arguments);
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
}
