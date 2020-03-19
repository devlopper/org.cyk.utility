package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.__kernel__.value.Value;

public interface InputClassBuilder {

	Class<?> build(Class<?> klass,Field field);
	
	/**/
	
	public static abstract class AbstractInputClassBuilderImpl extends AbstractObject implements Serializable,InputClassBuilder {
		
		@Override
		public Class<?> build(Class<?> klass,Field field) {
			if(field == null)
				return null;
			Class<?> inputClass = null;
			Input annotation = field.getAnnotation(Input.class);
			if(annotation != null)
				inputClass = annotation.klass();
			if(Void.class.equals(inputClass))
				inputClass = InputText.class;
			if(inputClass == null) {
				Class<?> fieldType = (Class<?>) FieldHelper.getType(field, null);
				if(String.class.equals(fieldType))
					inputClass = InputText.class;
				else if(ClassHelper.isInstanceOfNumber(fieldType))
					inputClass = InputNumber.class;
				else if(!ClassHelper.isBelongsToJavaPackages(fieldType))
					inputClass = AutoComplete.class;
			}
			return inputClass;
		}
	}
	
	/**/
	
	static InputClassBuilder getInstance() {
		return Helper.getInstance(InputClassBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}