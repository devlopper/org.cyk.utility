package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.reflect.MethodUtils;
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
import org.cyk.utility.field.FieldHelperImpl;
import org.cyk.utility.helper.AbstractHelper;
import org.cyk.utility.internationalization.InternationalizationHelperImpl;
import org.cyk.utility.internationalization.InternationalizationKey;
import org.cyk.utility.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringHelperImpl;
import org.cyk.utility.system.action.SystemAction;

@ApplicationScoped
public class ComponentBuilderHelperImpl extends AbstractHelper implements ComponentBuilderHelper,Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<String,Class<? extends ComponentBuilder<?>> > COMPONENTS_BUILDERS_CLASSES_MAP = new HashMap<>();
	
	private static ComponentBuilderHelper INSTANCE;
	public static ComponentBuilderHelper getInstance(Boolean isNew) {
		if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE = __inject__(ComponentBuilderHelper.class);
		return INSTANCE;
	}
	public static ComponentBuilderHelper getInstance() {
		return getInstance(null);
	}

	/**/
	
	protected static <T extends Annotation> T __getAnnotation__(Class<T> annotationClass,Collection<? extends Annotation> annotations) {
		for(Annotation index : annotations) {
			if(index.annotationType().equals(annotationClass))
				return (T) index;
		}
		return null;
	}

	public static Class<? extends ComponentBuilder<?>> __getComponentBuilderClass__(Class<?> klass,Class<?> baseClass,Field field,String[] fieldNames,Method method,String methodName,Annotations annotations) {
		Class<? extends ComponentBuilder<?>> builderClass = null;
		if(klass != null) {
			Collection<Annotation> annotationCollection = null;
			StringBuilder key = new StringBuilder(klass.getName());
			if(annotations != null) {
				annotationCollection = new HashSet<>();
				annotationCollection.addAll(annotations.get());
			}
			
			if(annotationCollection == null) {
				annotationCollection = new HashSet<>();
				if(field == null && fieldNames!=null) {
					field = FieldHelperImpl.__getFieldByNames__(klass, fieldNames);
				}else if(method == null && methodName!=null) {
					method = MethodUtils.getMatchingMethod(klass, methodName);
				}
				if(field!=null) {
					key.append(field.getName());
					Annotation[] fieldAnnotations = field.getAnnotations();
					if(fieldAnnotations!=null)
						for(Annotation index : fieldAnnotations) {
							annotationCollection.add(index);
						}
				}else if(method!=null) {
					key.append(method.getName());
					Annotation[] methodAnnotations = method.getAnnotations();
					if(methodAnnotations!=null)
						for(Annotation index : methodAnnotations) {
							annotationCollection.add(index);
						}
				}
			}
			
			for(Annotation index : annotationCollection)
				key.append(index.annotationType().getName());
			
			builderClass = COMPONENTS_BUILDERS_CLASSES_MAP.get(key.toString());
			if(builderClass == null) {
				builderClass = __getBuilderClassFromAnnotations__(annotationCollection);
				if(builderClass!=null && baseClass!=null && !baseClass.isAssignableFrom(builderClass)) {
					if(InputBuilder.class.isAssignableFrom(builderClass)) {
						if(baseClass.equals(OutputBuilder.class))
							if(InputFileBuilder.class.equals(builderClass))
								baseClass = OutputFileBuilder.class;
							
						if(OutputFileBuilder.class.isAssignableFrom(baseClass))
							builderClass = OutputFileBuilder.class;
						else if(OutputStringLinkBuilder.class.isAssignableFrom(baseClass))
							builderClass = OutputStringLinkBuilder.class;
						else if(OutputBuilder.class.isAssignableFrom(baseClass))
							builderClass = OutputStringTextBuilder.class;
					}										
				}
				COMPONENTS_BUILDERS_CLASSES_MAP.put(key.toString(), builderClass);
			}	
		}
		return builderClass;
	}
	
	protected static Class<? extends ComponentBuilder<?>> __getBuilderClassFromAnnotations__(Collection<? extends Annotation> annotations) {
		
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

	public static ComponentBuilder<?> __getComponentBuilder__(Class<? extends ComponentBuilder<?>> klass,Object object,Field field,String[] fieldNames,Method method,String methodName,SystemAction systemAction) {
		ComponentBuilder<?> builder = null;
		if(object == null)
			return null;
		if(field == null && fieldNames!=null)
			field = FieldHelperImpl.__getFieldByNames__(object.getClass(), fieldNames);
		else if(method == null && StringHelperImpl.__isNotBlank__(methodName))
			method = MethodUtils.getMatchingAccessibleMethod(object.getClass(), methodName);

		if(klass!=null)
			builder = __inject__(klass);
		
		if(builder == null) {
			System.err.println(String.format("Component builder not found. %s.%", object.getClass().getName(),field == null ? method.getName() : field.getName()));
		}else {
			if(builder instanceof InputOutputBuilder<?, ?>) {
				InputOutputBuilder<?, ?> inputOutputBuilder = (InputOutputBuilder<?, ?>) builder;
				inputOutputBuilder.setObject(object);
				inputOutputBuilder.setField(field);
				
				if(inputOutputBuilder instanceof InputBuilder<?,?>) {
					InputBuilder<?,?> inputBuilder = (InputBuilder<?, ?>) inputOutputBuilder;
					//inputBuilder.setFieldNameStrings(__inject__(Strings.class).add(fieldNames));
					//inputBuilder.setFieldNameStrings(__inject__(Strings.class).add(field.getName()));
					//inputBuilder.getLabelBuilder(Boolean.TRUE).setOutputPropertyValue(inputBuilder.getFieldNameStrings().get().toString());
					
					//inputBuilder.getLabelBuilder(Boolean.TRUE).setOutputPropertyValue(inputBuilder.getField().getName());
					//inputBuilder.getLabel(Boolean.TRUE).setValue(inputBuilder.getField().getName());
					inputBuilder.getLabel(Boolean.TRUE).getValueInternationalizationString(Boolean.TRUE).setKey(new InternationalizationKey().setValue(inputBuilder.getField().getName()));
				}
			}else if(builder instanceof CommandableBuilder) {
				org.cyk.utility.client.controller.component.annotation.Commandable commandableAnnotation = method.getAnnotation(org.cyk.utility.client.controller.component.annotation.Commandable.class);
				CommandableBuilder commandableBuilder = (CommandableBuilder) builder;
				
				if(systemAction == null)
					commandableBuilder.setCommandFunctionActionClass(commandableAnnotation == null || SystemAction.class.equals(commandableAnnotation.systemActionClass())? null : commandableAnnotation.systemActionClass());
				else
					commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(systemAction);
				
				final Method finalMethod = method;
				commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).addRunnables(new Runnable() {
					@Override
					public void run() {
						try {
							finalMethod.invoke(object);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				if(commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).getAction()==null) {
					commandableBuilder.setName(method.getName());
				}else {
					/*
					commandableBuilder.getNameInternalization(Boolean.TRUE).setKeyValue(commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).getAction());
					commandableBuilder.getNameInternalization(Boolean.TRUE).getKeyBuilder(Boolean.TRUE).setType(InternationalizationKeyStringType.VERB);
					commandableBuilder.getNameInternalization(Boolean.TRUE).setCase(Case.FIRST_CHARACTER_UPPER);
					*/
					commandableBuilder.getNameInternationalization(Boolean.TRUE).setKey(InternationalizationHelperImpl.__buildKey__(
							commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).getAction(),InternationalizationKeyStringType.VERB));
					commandableBuilder.getNameInternationalization(Boolean.TRUE).setKase(Case.FIRST_CHARACTER_UPPER);
				}
			}
		}
		return builder;
	}
}
