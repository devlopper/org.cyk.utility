package org.cyk.utility.__kernel__.object;

import java.util.Map;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;

public interface Builder<OBJECT> {

	static <OBJECT> OBJECT build(Class<OBJECT> klass,Map<Object,Object> arguments) {
		if(klass == null)
			return null;
		OBJECT object = ClassHelper.instanciate(klass);
		if(object == null)
			return null;
		@SuppressWarnings("unchecked")
		Configurator<OBJECT> configurator = (Configurator<OBJECT>) MapHelper.readByKey(arguments, Configurator.class);
		if(configurator == null)
			configurator = Configurator.get(klass);
		if(configurator != null)
			configurator.configure(object, arguments);
		return object;
	}
	
	static <OBJECT> OBJECT build(Class<OBJECT> klass) {
		if(klass == null)
			return null;
		return build(klass,null);
	}
	
}
