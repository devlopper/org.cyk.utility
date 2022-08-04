package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface InputClassGetter {

	Class<?> get(Class<?> klass,Field field);
	
	default Class<?> get(Class<?> klass,String fieldName) {
		if(StringHelper.isBlank(fieldName))
			throw new RuntimeException("field name is required");
		return get(klass,FieldHelper.getByName(klass, fieldName));
	}
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements Serializable,InputClassGetter {
		
		@Override
		public Class<?> get(Class<?> klass,Field field) {
			if(field == null)
				throw new RuntimeException("field is required");
			if(CLASSES.containsKey(field))
				return CLASSES.get(field);
			Class<?> inputClass = InputClassBuilder.getInstance().build(klass, field);
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
	
	Value INSTANCE = new Value();
	
	Map<Field,Class<?>> CLASSES = new HashMap<>();
}