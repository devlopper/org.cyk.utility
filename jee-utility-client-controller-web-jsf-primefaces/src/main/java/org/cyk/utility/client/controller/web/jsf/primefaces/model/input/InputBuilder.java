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

public interface InputBuilder {

	AbstractInput<?> build(Object object,Field field,Map<Object,Object> arguments);
	
	/**/
	
	public static abstract class AbstractInputBuilderImpl extends AbstractObject implements Serializable,InputBuilder {		
		@Override
		public AbstractInput<?> build(Object object,Field field,Map<Object,Object> arguments) {
			if(object == null || field == null)
				return null;
			Class<?> inputClass = InputClassGetter.getInstance().get(object.getClass(), field);
			if(inputClass == null) {
				LogHelper.logSevere(String.format("Input class cannot be deduced from field %s", field), getClass());
				return null;
			}
			if(arguments == null)
				arguments = new HashMap<>();
			arguments.put(AbstractInput.FIELD_OBJECT, object);
			arguments.put(AbstractInput.FIELD_FIELD, field);
			AbstractInput<?> input = null;
			try {
				//input = (AbstractInput<?>) MethodUtils.invokeStaticMethod(inputClass, "buildFromArray", inputArguments);
				if(InputText.class.equals(inputClass))
					input = InputText.build(arguments);
				else if(InputNumber.class.equals(inputClass))
					input = InputNumber.build(arguments);
				else if(AutoComplete.class.equals(inputClass))
					input = AutoComplete.build(arguments);
			} catch (Exception exception) {
				LogHelper.log(exception, AbstractInputBuilderImpl.class);
			}
			return input;
		}
	}
	
	/**/
	
	static InputBuilder getInstance() {
		return Helper.getInstance(InputBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}