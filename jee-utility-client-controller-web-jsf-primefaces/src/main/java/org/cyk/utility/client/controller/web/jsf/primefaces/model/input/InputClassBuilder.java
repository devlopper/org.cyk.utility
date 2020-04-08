package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface InputClassBuilder {

	Class<?> build(Class<?> klass,Field field);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements Serializable,InputClassBuilder {
		
		@Override
		public Class<?> build(Class<?> klass,Field field) {
			return build_static(klass, field);
		}
		
		/**/
		
		public static Class<?> build_static(Class<?> klass,Field field) {
			if(field == null)
				throw new RuntimeException("field is required");
			Class<?> inputClass = null;
			Input annotation = field.getAnnotation(Input.class);
			if(annotation == null)
				inputClass = Void.class;
			else
				inputClass = annotation.klass();
			if(Void.class.equals(inputClass)) {
				if(ClassHelper.isInstanceOf(field.getType(),Collection.class)) {
					inputClass = AutoComplete.class;
				}else {
					Class<?> fieldType = (Class<?>) FieldHelper.getType(field, null);
					if(String.class.equals(fieldType))
						inputClass = InputText.class;
					else if(ClassHelper.isInstanceOfNumber(fieldType))
						inputClass = InputNumber.class;
					else if(!ClassHelper.isBelongsToJavaPackages(fieldType))
						inputClass = AutoComplete.class;
					else {
						LogHelper.logWarning(String.format("type %s not yet handled", inputClass), klass);
						inputClass = InputText.class;
					}
				}
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