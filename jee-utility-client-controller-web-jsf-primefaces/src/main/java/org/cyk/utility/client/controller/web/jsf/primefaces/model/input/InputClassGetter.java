package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface InputClassGetter {

	Class<?> get(Class<?> klass,Field field);
	
	/**/
	
	public static abstract class AbstractInputClassGetterImpl extends AbstractObject implements Serializable,InputClassGetter {
		
		@Override
		public Class<?> get(Class<?> klass,Field field) {
			if(field == null)
				return null;
			Class<?> inputClass = CLASSES.get(field);
			if(inputClass != null)
				return inputClass;
			inputClass = InputClassBuilder.getInstance().build(klass, field);
			if(inputClass == null) {
				LogHelper.logSevere(String.format("Input class cannot be build from field %s", field), getClass());
			}else {
				CLASSES.put(field, inputClass);
			}
			return inputClass;
		}
	}
	
	/**/
	
	static InputClassGetter getInstance() {
		return Helper.getInstance(InputClassGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	Map<Field,Class<?>> CLASSES = new HashMap<>();
}