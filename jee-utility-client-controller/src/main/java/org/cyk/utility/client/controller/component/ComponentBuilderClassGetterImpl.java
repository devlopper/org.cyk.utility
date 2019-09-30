package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.annotation.Annotations;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.input.InputBooleanButtonBuilder;
import org.cyk.utility.client.controller.component.input.InputBooleanCheckBoxBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.input.InputDateBuilder;
import org.cyk.utility.client.controller.component.input.InputDateTimeBuilder;
import org.cyk.utility.client.controller.component.input.InputFileBuilder;
import org.cyk.utility.client.controller.component.input.InputStringEditorBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyAutoCompleteBuilder;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyCheckBoxBuilder;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneAutoCompleteBuilder;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneComboBuilder;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneRadioBuilder;
import org.cyk.utility.client.controller.component.output.OutputBuilder;
import org.cyk.utility.client.controller.component.output.OutputFileBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringLinkBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@SuppressWarnings("rawtypes") @Deprecated
public class ComponentBuilderClassGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class> implements ComponentBuilderClassGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<String,Collection<Annotation>> ANNOTATIONS_MAP = new HashMap<>();
	//private static final Map<String,Class<? extends ComponentBuilder<?>> > CLASSES_MAP = new HashMap<>();
	
	private Class clazz;
	private Strings fieldNameStrings;
	private Field field;
	private String methodName;
	private Method method;
	private Class<?> baseClass;
	private Annotations annotations;
	
	@Override
	protected Class __execute__() throws Exception {
		Class<? extends ComponentBuilder<?>> builderClass = null;
		Class<?> baseClass = getBaseClass();
		Annotations annotations = getAnnotations();
		Collection<Annotation> annotationCollection = new HashSet<>();
		String key = ConstantEmpty.STRING;
		if(CollectionHelper.isNotEmpty(annotations))
			annotationCollection.addAll(annotations.get());
		
		if(CollectionHelper.isEmpty(annotationCollection)) {
			Class clazz = getClazz();
			Field field = getField();
			if(field == null) {
				Strings fieldNameStrings = getFieldNameStrings();
				if(clazz!=null && CollectionHelper.isNotEmpty(fieldNameStrings)) {
					field = FieldHelper.getByName(clazz, (List<String>)fieldNameStrings.get());
				}
			}
			Method method = getMethod();
			if(method == null) {
				String methodName = getMethodName();
				if(clazz!=null && StringHelper.isNotBlank(methodName)) {
					method = MethodUtils.getMatchingMethod(clazz, methodName);
				}
			}
			key = clazz.getName()+ConstantCharacter.UNDESCORE+(field == null ? ConstantEmpty.STRING : field.getName())
					+ConstantCharacter.UNDESCORE+(method == null ? ConstantEmpty.STRING : method.getName())
					;
						
			annotationCollection = ANNOTATIONS_MAP.get(key);
			if(annotationCollection == null) {
				annotationCollection = new ArrayList<>();
				if(field!=null) {
					Annotation[] fieldAnnotations = field.getAnnotations();
					if(fieldAnnotations!=null)
						for(Annotation index : fieldAnnotations) {
							annotationCollection.add(index);
						}
				}
				
				if(method!=null) {
					Annotation[] methodAnnotations = method.getAnnotations();
					if(methodAnnotations!=null)
						for(Annotation index : methodAnnotations) {
							annotationCollection.add(index);
						}
				}
				ANNOTATIONS_MAP.put(key, annotationCollection);
			}		
		}
		
		builderClass = __getBuilderClassFromAnnotations__(annotationCollection);
		if(builderClass!=null && baseClass!=null && !Boolean.TRUE.equals(ClassHelper.isInstanceOf(builderClass, baseClass))) {
			if(ClassHelper.isInstanceOf(builderClass, InputBuilder.class)) {
				if(baseClass.equals(OutputBuilder.class))
					if(InputFileBuilder.class.equals(builderClass))
						baseClass = OutputFileBuilder.class;
					
				if(ClassHelper.isInstanceOf(baseClass, OutputFileBuilder.class))
					builderClass = OutputFileBuilder.class;
				else if(ClassHelper.isInstanceOf(baseClass, OutputStringLinkBuilder.class))
					builderClass = OutputStringLinkBuilder.class;
				else if(ClassHelper.isInstanceOf(baseClass, OutputBuilder.class))
					builderClass = OutputStringTextBuilder.class;
			}						
			
		}
		return builderClass;
	}
	
	@Override
	public Field getField() {
		return field;
	}
	
	@Override
	public ComponentBuilderClassGetter setField(Field field) {
		this.field = field;
		return this;
	}
	
	@Override
	public String getMethodName() {
		return methodName;
	}
	
	@Override
	public ComponentBuilderClassGetter setMethodName(String methodName) {
		this.methodName = methodName;
		return this;
	}
	
	@Override
	public Method getMethod() {
		return method;
	}
	
	@Override
	public ComponentBuilderClassGetter setMethod(Method method) {
		this.method = method;
		return this;
	}
	
	@Override
	public Annotations getAnnotations() {
		return annotations;
	}
	
	@Override
	public Annotations getAnnotations(Boolean injectIfNull) {
		if(annotations == null && Boolean.TRUE.equals(injectIfNull))
			annotations = __inject__(Annotations.class);
		return annotations;
	}

	@Override
	public ComponentBuilderClassGetter setAnnotations(Annotations annotations) {
		this.annotations = annotations;
		return this;
	}
	
	@Override
	public ComponentBuilderClassGetter addAnnotations(Annotation... annotations) {
		getAnnotations(Boolean.TRUE).add(annotations);
		return this;
	}
	
	@Override
	public Class<?> getClazz() {
		return clazz;
	}
	
	@Override
	public ComponentBuilderClassGetter setClazz(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}
	
	@Override
	public Strings getFieldNameStrings() {
		return fieldNameStrings;
	}
	
	@Override
	public Strings getFieldNameStrings(Boolean injectIfNull) {
		if(fieldNameStrings == null && Boolean.TRUE.equals(injectIfNull))
			fieldNameStrings = __inject__(Strings.class);
		return fieldNameStrings;
	}
	
	@Override
	public ComponentBuilderClassGetter setFieldNameStrings(Strings fieldNameStrings) {
		this.fieldNameStrings = fieldNameStrings;
		return this;
	}
	
	@Override
	public ComponentBuilderClassGetter addFieldNameStrings(String... fieldNameStrings) {
		getFieldNameStrings(Boolean.TRUE).add(fieldNameStrings);
		return this;
	}
	
	@Override
	public Class<?> getBaseClass() {
		return baseClass;
	}
	
	@Override
	public ComponentBuilderClassGetter setBaseClass(Class<?> baseClass) {
		this.baseClass = baseClass;
		return this;
	}
	
	/**/
	
	protected <T extends Annotation> T __getAnnotation__(Class<T> annotationClass,Collection<? extends Annotation> annotations) {
		for(Annotation index : annotations) {
			if(index.annotationType().equals(annotationClass))
				return (T) index;
		}
		return null;
	}

	protected Class<? extends ComponentBuilder<?>> __getBuilderClassFromAnnotations__(Collection<? extends Annotation> annotations) {
		
		//if(org.cyk.utility.client.controller.component.output.Output.class.equals(componentBaseClass)) {
			/* Output */
			
			org.cyk.utility.client.controller.component.annotation.OutputStringText annotationOutputStringText =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.OutputStringText.class,annotations);
			if(annotationOutputStringText!=null)
				return OutputStringTextBuilder.class;	
		//}else if(org.cyk.utility.client.controller.component.input.Input.class.equals(componentBaseClass)) {
			/* Input */
			
			org.cyk.utility.client.controller.component.annotation.InputStringLineOne annotationInputStringLineOne =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputStringLineOne.class,annotations);
			if(annotationInputStringLineOne!=null)
				return InputStringLineOneBuilder.class;
			
			org.cyk.utility.client.controller.component.annotation.InputStringLineMany annotationInputStringLineMany =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputStringLineMany.class,annotations);
			if(annotationInputStringLineMany!=null)
				return InputStringLineManyBuilder.class;
			
			org.cyk.utility.client.controller.component.annotation.InputStringEditor annotationInputStringEditor =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputStringEditor.class,annotations);
			if(annotationInputStringEditor!=null)
				return InputStringEditorBuilder.class;
			
			org.cyk.utility.client.controller.component.annotation.InputBooleanButton annotationInputBooleanButton =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputBooleanButton.class,annotations);
			if(annotationInputBooleanButton!=null)
				return InputBooleanButtonBuilder.class;
			
			org.cyk.utility.client.controller.component.annotation.InputBooleanCheckBox annotationInputBooleanCheckBox =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputBooleanCheckBox.class,annotations);
			if(annotationInputBooleanCheckBox!=null)
				return InputBooleanCheckBoxBuilder.class;
			
			org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo annotationInputChoiceOneCombo =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo.class,annotations);
			if(annotationInputChoiceOneCombo!=null)
				return InputChoiceOneComboBuilder.class;
			
			org.cyk.utility.client.controller.component.annotation.InputChoiceOneRadio annotationInputChoiceOneRadio =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputChoiceOneRadio.class,annotations);
			if(annotationInputChoiceOneRadio!=null)
				return InputChoiceOneRadioBuilder.class;
			
			org.cyk.utility.client.controller.component.annotation.InputChoiceOneAutoComplete annotationInputChoiceOneAutoComplete =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputChoiceOneAutoComplete.class,annotations);
			if(annotationInputChoiceOneAutoComplete!=null)
				return InputChoiceOneAutoCompleteBuilder.class;
			
			org.cyk.utility.client.controller.component.annotation.InputChoiceManyCheckBox annotationInputChoiceManyCheckBox =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputChoiceManyCheckBox.class,annotations);
			if(annotationInputChoiceManyCheckBox!=null)
				return InputChoiceManyCheckBoxBuilder.class;	
			
			org.cyk.utility.client.controller.component.annotation.InputChoiceManyAutoComplete annotationInputChoiceManyAutoComplete =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputChoiceManyAutoComplete.class,annotations);
			if(annotationInputChoiceManyAutoComplete!=null)
				return InputChoiceManyAutoCompleteBuilder.class;
			
			org.cyk.utility.client.controller.component.annotation.InputFile annotationInputFile =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputFile.class,annotations);
			if(annotationInputFile!=null)
				return InputFileBuilder.class;	
			
			org.cyk.utility.client.controller.component.annotation.InputDate annotationInputDate =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputDate.class,annotations);
			if(annotationInputDate!=null)
				return InputDateBuilder.class;	
			
			org.cyk.utility.client.controller.component.annotation.InputDateTime annotationInputDateTime =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputDateTime.class,annotations);
			if(annotationInputDateTime!=null)
				return InputDateTimeBuilder.class;	
			
		//}else if(org.cyk.utility.client.controller.component.command.Commandable.class.equals(componentBaseClass)) {
			/* Commandable */
			
			org.cyk.utility.client.controller.component.annotation.Commandable annotationCommandable =
					__getAnnotation__(org.cyk.utility.client.controller.component.annotation.Commandable.class,annotations);
			if(annotationCommandable!=null)
				return CommandableBuilder.class;	
		//}
		
		return null;
	}
	
	/**/
	
	public static final String FIELD_ANNOTATIONS = "annotations";
	public static final String FIELD_FIELD_NAME_STRINGS = "fieldNameStrings";
}
