package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.annotation.Annotations;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyCheckBoxBuilder;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneComboBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Strings;

@SuppressWarnings("rawtypes")
public class ComponentBuilderClassGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class> implements ComponentBuilderClassGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class clazz;
	private Strings fieldNameStrings;
	private Field field;
	private String methodName;
	private Method method;
	private Annotations annotations;
	
	@Override
	protected Class __execute__() throws Exception {
		Class<? extends ComponentBuilder<?>> builderClass = null;
		Annotations annotations = getAnnotations();
		Collection<Annotation> annotationCollection = new HashSet<>();
		if(__injectCollectionHelper__().isNotEmpty(annotations))
			annotationCollection.addAll(annotations.get());
		
		if(__injectCollectionHelper__().isEmpty(annotationCollection)) {
			Field field = getField();
			if(field == null) {
				Class clazz = getClazz();
				Strings fieldNameStrings = getFieldNameStrings();
				if(clazz!=null && __injectCollectionHelper__().isNotEmpty(fieldNameStrings)) {
					field = __injectCollectionHelper__().getFirst(
							__inject__(FieldGetter.class).execute(clazz, __injectFieldHelper__().concatenate(fieldNameStrings.get())).getOutput());
				}
			}
			if(field!=null) {
				Annotation[] fieldAnnotations = field.getAnnotations();
				if(fieldAnnotations!=null)
					for(Annotation index : fieldAnnotations) {
						annotationCollection.add(index);
					}
			}
			
			Method method = getMethod();
			if(method == null) {
				Class clazz = getClazz();
				String methodName = getMethodName();
				if(clazz!=null && __injectStringHelper__().isNotBlank(methodName)) {
					method = MethodUtils.getMatchingMethod(clazz, methodName);
				}
			}
			
			if(method!=null) {
				Annotation[] methodAnnotations = method.getAnnotations();
				if(methodAnnotations!=null)
					for(Annotation index : methodAnnotations) {
						annotationCollection.add(index);
					}
			}
		}
		
		builderClass = __getBuilderClassFromAnnotations__(annotationCollection);
		
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
		return (Annotations) __getInjectIfNull__(FIELD_ANNOTATIONS, injectIfNull);
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
		return (Strings) __getInjectIfNull__(FIELD_FIELD_NAME_STRINGS, injectIfNull);
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
	
	/**/
	
	protected <T extends Annotation> T __getAnnotation__(Class<T> annotationClass,Collection<? extends Annotation> annotations) {
		for(Annotation index : annotations) {
			if(index.annotationType().equals(annotationClass))
				return (T) index;
		}
		return null;
	}

	protected Class<? extends ComponentBuilder<?>> __getBuilderClassFromAnnotations__(Collection<? extends Annotation> annotations) {
		/* Output */
		
		org.cyk.utility.client.controller.component.annotation.OutputStringText annotationOutputStringText =
				__getAnnotation__(org.cyk.utility.client.controller.component.annotation.OutputStringText.class,annotations);
		if(annotationOutputStringText!=null)
			return OutputStringTextBuilder.class;
		
		/* Input */
		
		org.cyk.utility.client.controller.component.annotation.InputStringLineOne annotationInputStringLineOne =
				__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputStringLineOne.class,annotations);
		if(annotationInputStringLineOne!=null)
			return InputStringLineOneBuilder.class;
		
		org.cyk.utility.client.controller.component.annotation.InputStringLineMany annotationInputStringLineMany =
				__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputStringLineMany.class,annotations);
		if(annotationInputStringLineMany!=null)
			return InputStringLineManyBuilder.class;
		
		org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo annotationInputChoiceOneCombo =
				__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo.class,annotations);
		if(annotationInputChoiceOneCombo!=null)
			return InputChoiceOneComboBuilder.class;
		
		org.cyk.utility.client.controller.component.annotation.InputChoiceManyCheckBox annotationInputChoiceManyCheckBox =
				__getAnnotation__(org.cyk.utility.client.controller.component.annotation.InputChoiceManyCheckBox.class,annotations);
		if(annotationInputChoiceManyCheckBox!=null)
			return InputChoiceManyCheckBoxBuilder.class;
		
		/* Commandable */
		
		org.cyk.utility.client.controller.component.annotation.Commandable annotationCommandable =
				__getAnnotation__(org.cyk.utility.client.controller.component.annotation.Commandable.class,annotations);
		if(annotationCommandable!=null)
			return CommandableBuilder.class;
		
		return null;
	}
	
	/**/
	
	public static final String FIELD_ANNOTATIONS = "annotations";
	public static final String FIELD_FIELD_NAME_STRINGS = "fieldNameStrings";
}
