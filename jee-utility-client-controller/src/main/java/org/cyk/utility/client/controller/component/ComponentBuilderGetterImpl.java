package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationKey;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@SuppressWarnings("rawtypes") @Deprecated
public class ComponentBuilderGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<ComponentBuilder> implements ComponentBuilderGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private ComponentBuilderClassGetter classGetter;
	private Class<? extends ComponentBuilder<?>> clazz;
	private Object object;
	private Method __method__;
	private SystemAction systemAction;
	
	@Override
	protected ComponentBuilder __execute__() throws Exception {
		ComponentBuilder builder = null;
		Object object = getObject();
		Field field = getField();
		Method method = getMethod();
		if(method == null && object!=null) {
			String methodName = getMethodName();
			if(StringHelper.isNotBlank(methodName))
				method = MethodUtils.getMatchingAccessibleMethod(object.getClass(), methodName);
		}
		__method__ = method;
		ComponentBuilderClassGetter classGetter = getClassGetter();
		
		if(field == null) {
			if(object!=null) {
				if(CollectionHelper.isNotEmpty(classGetter.getFieldNameStrings())) {
					field = FieldHelper.getByName(object.getClass(), (List<String>)classGetter.getFieldNameStrings().get());
					classGetter.setField(field);
					/*
					Collection<String> fieldNames = classGetter.getFieldNameStrings().get();
					Integer size = CollectionHelper.getSize(fieldNames);
					if(size == 1) {
						field = CollectionHelper.getFirst(__inject__(FieldsGetter.class).execute(object.getClass(), CollectionHelper
								.getElementAt(fieldNames, 0)).getOutput());
					}else {
						for(Integer index = 0 ; index < size - 1 ; index = index + 1) {
							String fieldName = CollectionHelper.getElementAt(fieldNames, index);
							object =  __inject__(FieldValueGetter.class).execute(object, fieldName).getOutput();
							field = CollectionHelper.getFirst(__inject__(FieldsGetter.class).execute(object.getClass(), CollectionHelper
									.getElementAt(fieldNames, index+1)).getOutput());
							classGetter.setField(field);
						}	
					}
					System.out.println("ComponentBuilderGetterImpl.__execute__() 0");
					*/
				}
			}
		}
		Class<? extends ComponentBuilder<?>> clazz = getClazz();
		if(clazz == null) {
			if(classGetter!=null) {
				if(classGetter.getClazz() == null && object!=null)
					classGetter.setClazz(object.getClass());
				clazz = classGetter.execute().getOutput();
			}
		}
		if(clazz!=null)
			builder = __inject__(clazz);
		
		if(builder == null) {
			System.err.println("Component builder not found. field="+field+" ::: methodd ="+method);
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
					//inputBuilder.getLabel(Boolean.TRUE).setValueInternalizationKeyValue(inputBuilder.getField().getName());
					inputBuilder.getLabel(Boolean.TRUE).getValueInternationalizationString(Boolean.TRUE).setKey(new InternationalizationKey().setValue(inputBuilder.getField().getName()));
				}
			}else if(builder instanceof CommandableBuilder) {
				org.cyk.utility.client.controller.component.annotation.Commandable commandableAnnotation = __method__.getAnnotation(org.cyk.utility.client.controller.component.annotation.Commandable.class);
				CommandableBuilder commandableBuilder = (CommandableBuilder) builder;
				
				SystemAction systemAction = getSystemAction();
				if(systemAction == null)
					commandableBuilder.setCommandFunctionActionClass(commandableAnnotation == null || SystemAction.class.equals(commandableAnnotation.systemActionClass())? null : commandableAnnotation.systemActionClass());
				else
					commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(systemAction);
				
				Object objectFinal = object;
				commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).addRunnables(new Runnable() {
					@Override
					public void run() {
						try {
							__method__.invoke(objectFinal);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				if(commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).getAction()==null) {
					commandableBuilder.setName(__method__.getName());
				}else {
					/*
					commandableBuilder.getNameInternalization(Boolean.TRUE).setKeyValue(commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).getAction());
					commandableBuilder.getNameInternalization(Boolean.TRUE).getKeyBuilder(Boolean.TRUE).setType(InternationalizationKeyStringType.VERB);
					commandableBuilder.getNameInternalization(Boolean.TRUE).setCase(Case.FIRST_CHARACTER_UPPER);
					*/
				}
			}
		}
		return builder;
	}
	
	@Override
	public ComponentBuilderClassGetter getClassGetter() {
		return classGetter;
	}
	
	@Override
	public ComponentBuilderClassGetter getClassGetter(Boolean injectIfNull) {
		return (ComponentBuilderClassGetter) __getInjectIfNull__(FIELD_CLASS_GETTER, injectIfNull);
	}
	
	@Override
	public ComponentBuilderGetter setClassGetter(ComponentBuilderClassGetter classGetter) {
		this.classGetter = classGetter;
		return this;
	}
	
	@Override
	public Class<? extends ComponentBuilder<?>> getClazz() {
		return clazz;
	}
	
	@Override
	public ComponentBuilderGetter setClazz(Class<? extends ComponentBuilder<?>> clazz) {
		this.clazz = clazz;
		return this;
	}
	
	@Override
	public Field getField() {
		ComponentBuilderClassGetter classGetter = getClassGetter();
		return classGetter == null ? null : classGetter.getField();
	}
	
	@Override
	public ComponentBuilderGetter setField(Field field) {
		getClassGetter(Boolean.TRUE).setField(field);
		return this;
	}
	
	@Override
	public Method getMethod() {
		ComponentBuilderClassGetter classGetter = getClassGetter();
		return classGetter == null ? null : classGetter.getMethod();
	}
	
	@Override
	public ComponentBuilderGetter setMethod(Method method) {
		getClassGetter(Boolean.TRUE).setMethod(method);
		return this;
	}
	
	@Override
	public String getMethodName() {
		ComponentBuilderClassGetter classGetter = getClassGetter();
		return classGetter == null ? null : classGetter.getMethodName();
	}
	
	@Override
	public ComponentBuilderGetter setMethodName(String methodName) {
		getClassGetter(Boolean.TRUE).setMethodName(methodName);
		return this;
	}
	
	@Override
	public Object getObject() {
		return object;
	}
	
	@Override
	public ComponentBuilderGetter setObject(Object object) {
		this.object = object;
		return this;
	}
	
	@Override
	public ComponentBuilderGetter addFieldNameStrings(String... fieldNameStrings) {
		getClassGetter(Boolean.TRUE).getFieldNameStrings(Boolean.TRUE).add(fieldNameStrings);
		return this;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}
	
	@Override
	public ComponentBuilderGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}
	
	/**/
	
	public static final String FIELD_CLASS_GETTER = "classGetter";
}
