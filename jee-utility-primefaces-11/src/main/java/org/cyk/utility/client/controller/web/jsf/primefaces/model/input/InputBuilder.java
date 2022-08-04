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

public interface InputBuilder {

	AbstractInput<?> build(Object object,Field field,Map<Object,Object> arguments,Class<?> inputClass);
	
	default AbstractInput<?> build(Object object,Field field,Map<Object,Object> arguments) {
		return build(object, field, arguments,null);
	}
	
	default AbstractInput<?> build(Object object,String fieldName,Map<Object,Object> arguments,Class<?> inputClass) {
		if(object == null)
			throw new RuntimeException("object is required");
		if(StringHelper.isBlank(fieldName))
			throw new RuntimeException("field name is required");
		return build(object, FieldHelper.getByName(object.getClass(), fieldName), arguments,inputClass);
	}
	
	default AbstractInput<?> build(Object object,String fieldName,Map<Object,Object> arguments) {
		return build(object, fieldName, arguments, null);
	}
	
	/**/
	
	public static abstract class AbstractInputBuilderImpl extends AbstractObject implements Serializable,InputBuilder {		
		@Override
		public AbstractInput<?> build(Object object,Field field,Map<Object,Object> arguments,Class<?> inputClass) {
			if(object == null)
				throw new RuntimeException("object is required");
			if(field == null)
				throw new RuntimeException("field is required");
			if(inputClass == null)
				inputClass = InputClassGetter.getInstance().get(object.getClass(), field);
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
				else if(InputMask.class.equals(inputClass))
					input = InputMask.build(arguments);
				else if(InputTextarea.class.equals(inputClass))
					input = InputTextarea.build(arguments);
				else if(Password.class.equals(inputClass))
					input = Password.build(arguments);
				else if(InputNumber.class.equals(inputClass))
					input = InputNumber.build(arguments);
				else if(Calendar.class.equals(inputClass))
					input = Calendar.build(arguments);
				else if(AutoComplete.class.equals(inputClass))
					input = AutoComplete.build(arguments);
				else if(SelectManyCheckbox.class.equals(inputClass))
					input = SelectManyCheckbox.build(arguments);
				else if(SelectOneRadio.class.equals(inputClass))
					input = SelectOneRadio.build(arguments);
				else if(SelectOneCombo.class.equals(inputClass))
					input = SelectOneCombo.build(arguments);
				else if(SelectBooleanCheckbox.class.equals(inputClass))
					input = SelectBooleanCheckbox.build(arguments);
				else if(SelectBooleanButton.class.equals(inputClass))
					input = SelectBooleanButton.build(arguments);
				else if(FileUpload.class.equals(inputClass))
					input = FileUpload.build(arguments);
			} catch (Exception exception) {
				LogHelper.log(exception, AbstractInputBuilderImpl.class);
			}
			if(input == null)
				LogHelper.logSevere(String.format("Input cannot be deduced from field %s", field), getClass());
			return input;
		}
	}
	
	/**/
	
	static InputBuilder getInstance() {
		return Helper.getInstance(InputBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}