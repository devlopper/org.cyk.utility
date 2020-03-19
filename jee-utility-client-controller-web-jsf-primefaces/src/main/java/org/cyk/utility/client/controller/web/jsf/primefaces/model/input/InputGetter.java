package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface InputGetter {

	AbstractInput<?> get(Object object,Field field,Map<Object,Object> arguments);
	
	default AbstractInput<?> get(Object object,Field field) {
		if(object == null || field == null)
			return null;
		return get(object, field, null);
	}
	
	default AbstractInput<?> get(Object object,String fieldName,Map<Object,Object> arguments) {
		if(object == null || StringHelper.isBlank(fieldName))
			return null;
		Field field = FieldHelper.getByName(object.getClass(), fieldName);
		if(field == null)
			return null;
		return get(object, field, arguments);
	}
	
	default AbstractInput<?> get(Object object,String[] fieldNames,Map<Object,Object> arguments) {
		if(object == null || ArrayHelper.isEmpty(fieldNames))
			return null;
		return get(object, FieldHelper.join(fieldNames), arguments);
	}
	
	default AbstractInput<?> get(Object object,String[] fieldNames) {
		return get(object,fieldNames,null);
	}
	
	default Collection<AbstractInput<?>> getMany(Class<?> klass) {
		if(klass == null)
			return null;
		// find inputs from class
		Collection<AbstractInput<?>> inputs = null;
		return inputs;
	}
	
	/**/
	
	public static abstract class AbstractInputGetterImpl extends AbstractObject implements Serializable,InputGetter {
		
		@Override
		public AbstractInput<?> get(Object object,Field field,Map<Object,Object> arguments) {
			if(object == null || field == null)
				return null;
			AbstractInput<?> input = CLASSES.get(field);
			if(input != null)
				return input;
			input = (AbstractInput<?>) InputBuilder.getInstance().build(object, field, arguments);
			if(input == null) {
				LogHelper.logSevere(String.format("Input cannot be build from field %s", field), getClass());
			}else {
				CLASSES.put(field, input);
			}
			return input;
		}
	}
	
	/**/
	
	static InputGetter getInstance() {
		return Helper.getInstance(InputGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
	Map<Field,AbstractInput<?>> CLASSES = new HashMap<>();
}