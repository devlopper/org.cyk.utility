package org.cyk.utility.client.controller.web.jsf.primefaces.model.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.controller.EntityCounter;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Choices;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.Input;
import org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputChoice;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface InputClassBuilder {

	Class<?> build(Class<?> klass,Field field);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements Serializable,InputClassBuilder {
		
		@Override
		public Class<?> build(Class<?> klass,Field field) {
			if(field == null)
				throw new RuntimeException("field is required");
			Class<?> inputClass = null;
			Input annotation = field.getAnnotation(Input.class);
			if(annotation == null)
				inputClass = Void.class;
			else
				inputClass = annotation.klass();
			if(Void.class.equals(inputClass))
				inputClass = deduceClassFromComponentAnnotation(field);
			if(inputClass == null)
				inputClass = Void.class;
			if(Void.class.equals(inputClass)) {
				InputChoice inputChoiceAnnotation = field.getAnnotation(InputChoice.class);
				if(inputChoiceAnnotation == null) {
					if(ClassHelper.isInstanceOf(field.getType(),Collection.class)) {
						inputClass = AutoComplete.class;
					}else {
						Class<?> fieldType = (Class<?>) FieldHelper.getType(field, null);
						if(String.class.equals(fieldType))
							inputClass = InputText.class;
						else if(ClassHelper.isInstanceOfNumber(fieldType))
							inputClass = InputNumber.class;
						else if(ClassHelper.isInstanceOfDate(fieldType))
							inputClass = Calendar.class;
						else if(!ClassHelper.isBelongsToJavaPackages(fieldType)) {						
							inputClass = AutoComplete.class;				
						}else {
							LogHelper.logWarning(String.format("type %s not yet handled", inputClass), klass);
							inputClass = InputText.class;
						}
					}	
				}else {
					Class<?> tupleClass = null;
					if(ClassHelper.isInstanceOf(field.getType(),Collection.class)) {
						tupleClass = ClassHelper.getParameterAt(field.getType(), 0);
					}else
						tupleClass = (Class<?>) FieldHelper.getType(field, null);
					Choices.Count choicesCount = inputChoiceAnnotation.choices().count();
					Long numberOfChoices = null;
					if(Choices.Count.RUNTIME.equals(choicesCount)) {
						numberOfChoices = EntityCounter.getInstance().count(tupleClass);
						if(numberOfChoices > 100)
							choicesCount = Choices.Count.AUTO_COMPLETE;
						else
							choicesCount = Choices.Count.ALL;
					}
					if(Choices.Count.ALL.equals(choicesCount)) {
						if(ClassHelper.isInstanceOf(field.getType(),Collection.class)) {
							inputClass = SelectManyCheckbox.class;
						}else {
							inputClass = SelectOneCombo.class;
						}						
					}else if(Choices.Count.AUTO_COMPLETE.equals(choicesCount))
						inputClass = AutoComplete.class;
					else
						inputClass = AutoComplete.class;
				}				
			}				
			return inputClass;
		}
		
		private Class<?> deduceClassFromComponentAnnotation(Field field) {
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputText.class))
				return InputText.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputMask.class))
				return InputMask.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputTextarea.class))
				return InputTextarea.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputPass.class))
				return Password.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputDate.class))
				return Calendar.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputChoiceManyCheck.class))
				return SelectManyCheckbox.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputChoiceOneRadio.class))
				return SelectOneRadio.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputChoiceOneCombo.class))
				return SelectOneCombo.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputChoiceOneAutoComplete.class))
				return AutoComplete.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputBooleanCheckbox.class))
				return SelectBooleanCheckbox.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputBooleanButton.class))
				return SelectBooleanButton.class;
			if(field.isAnnotationPresent(org.cyk.utility.__kernel__.object.__static__.controller.annotation.InputFile.class))
				return FileUpload.class;
			return Void.class;
		}
	}
	
	/**/
	
	static InputClassBuilder getInstance() {
		return Helper.getInstance(InputClassBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}