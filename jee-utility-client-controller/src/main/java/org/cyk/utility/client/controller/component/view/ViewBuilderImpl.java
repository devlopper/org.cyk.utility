package org.cyk.utility.client.controller.component.view;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderClassGetter;
import org.cyk.utility.client.controller.component.ComponentBuilderGetter;
import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilderByClassMap;
import org.cyk.utility.client.controller.component.command.CommandableBuilders;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.output.OutputBuilder;
import org.cyk.utility.client.controller.data.FormData;
import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionUpdate;

public class ViewBuilderImpl extends AbstractVisibleComponentBuilderImpl<View> implements ViewBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private ComponentsBuilder componentsBuilder;
	private ViewType type;
	private CommandableBuilderByClassMap commandableByClassMap;
	private CommandableBuilders commandables;
	private SystemAction systemAction;
		
	@Override
	protected void __execute__(View view) {
		super.__execute__(view);
		ComponentsBuilder componentsBuilder = getComponentsBuilder();
		if(componentsBuilder!=null)
			view.setComponents(componentsBuilder.execute().getOutput());
		
		ViewType type = getType();
		if(type instanceof ViewTypeForm) {
			//view.getComponents().getLayout().getStyle(Boolean.TRUE).getClasses(Boolean.TRUE).add("AZERTY");
		}
		
		Components components = view.getComponents();
		if(__injectCollectionHelper__().isNotEmpty(components)) {
			for(Component index : components.get()) {
				if(index instanceof Commandable) {
					Commandable commandable = (Commandable) index;
					commandable.getUpdatables(Boolean.TRUE).add(view.getComponents().getLayout());
					commandable.addCommandFunctionTryRunRunnableAt(new Runnable() {
						@Override
						public void run() {
							view.setInputOutputFieldValueFromValue();
						}
					},0);
				}
			}
		}
	}
	
	@Override
	public ViewType getType() {
		return type;
	}
	
	@Override
	public ViewBuilder setType(ViewType type) {
		this.type = type;
		return this;
	}
	
	@Override
	public ViewBuilder addInputBuilder(InputBuilder<?,?> inputBuilder) {
		/*VisibleComponentBuilders builders = getComponentBuilders(Boolean.TRUE);
		if(inputBuilder!=null) {
			builders.add(inputBuilder);
		}*/
		return this;
	}

	@Override
	public <T extends InputBuilder<?, ?>> T addInputBuilder(Class<T> inputBuilderClass,Object outputPropertyRequired,Object labelBuilderOutputPropertyValue) {
		T inputBuilder = __inject__(inputBuilderClass);
		if(outputPropertyRequired!=null)
			inputBuilder.setOutputPropertyRequired(outputPropertyRequired);
		if(labelBuilderOutputPropertyValue!=null)
			inputBuilder.getLabel(Boolean.TRUE).setOutputPropertyValue(labelBuilderOutputPropertyValue);
		addInputBuilder(inputBuilder);
		return inputBuilder;
	}
	
	@Override
	public InputStringLineOneBuilder addInputStringLineOneBuilder(Object outputPropertyRequired,Object labelBuilderOutputPropertyValue) {
		return addInputBuilder(InputStringLineOneBuilder.class, outputPropertyRequired, labelBuilderOutputPropertyValue);
	}
	
	@Override
	public InputStringLineManyBuilder addInputStringLineManyBuilder(Object outputPropertyRequired,Object labelBuilderOutputPropertyValue) {
		return addInputBuilder(InputStringLineManyBuilder.class, outputPropertyRequired, labelBuilderOutputPropertyValue);
	}
	
	@Override
	public <T extends InputBuilder<?, ?>> T addInputBuilderByFieldName(Class<T> inputBuilderClass, Object object,String... fieldNames) {
		T inputBuilder = __inject__(inputBuilderClass);
		inputBuilder.setObject(object);
		inputBuilder.setFieldNameStrings(__inject__(Strings.class).add(fieldNames));
		inputBuilder.getLabel(Boolean.TRUE).setOutputPropertyValue(inputBuilder.getFieldNameStrings().get().toString());
		addInputBuilder(inputBuilder);
		return inputBuilder;
	}
	
	@Override
	public InputBuilder<?, ?> addInputBuilderByFieldName(Object object, String... fieldNames) {
		Class<? extends InputBuilder<?,?>> inputBuilderClass =  __inject__(ComponentBuilderClassGetter.class).setField(__injectCollectionHelper__().getFirst(__inject__(FieldGetter.class)
				.execute(object.getClass(),  __injectFieldHelper__().concatenate(fieldNames)).getOutput())).execute().getOutput();
		return addInputBuilderByFieldName(inputBuilderClass, object, fieldNames);
	}
	
	@Override
	public <T extends ComponentBuilder<?>> T addComponentBuilderByObjectByFieldNames(Class<T> componentBuilderClass, Object object,String... fieldNames) {
		T builder = (T) __inject__(ComponentBuilderGetter.class).setClazz(componentBuilderClass).setObject(object).addFieldNameStrings(fieldNames).execute().getOutput();
		addComponentBuilder(builder);
		return builder;
	}
	
	@Override
	public ComponentBuilder<?> addComponentBuilderByObjectByFieldNames(Object object,Class<?> componentBuilderBaseClass, String... fieldNames) {
		Class<? extends ComponentBuilder<?>> builderClass =  __inject__(ComponentBuilderClassGetter.class)
				.setClazz(object.getClass())
				.addFieldNameStrings(fieldNames).setBaseClass(componentBuilderBaseClass)
				.execute().getOutput();
				//.setField(__injectCollectionHelper__().getFirst(__inject__(FieldGetter.class).execute(object.getClass(),  __injectFieldHelper__().concatenate(fieldNames)).getOutput()))
		
		return addComponentBuilderByObjectByFieldNames(builderClass, object, fieldNames);
	}
	
	@Override
	public ComponentBuilder<?> addComponentBuilderByObjectByFieldNames(Object object,String... fieldNames) {
		return addComponentBuilderByObjectByFieldNames(object,null, fieldNames);
	}
	
	@Override
	public ComponentBuilder<?> addInputBuilderByObjectByFieldNames(Object object, Boolean isEditable,String... fieldNames) {
		Class<?> componentBuilderBaseClass = null;
		if(!Boolean.TRUE.equals(isEditable))
			componentBuilderBaseClass = OutputBuilder.class;
		return addComponentBuilderByObjectByFieldNames(object, componentBuilderBaseClass, fieldNames);
	}
	
	@Override
	public ComponentBuilder<?> addInputBuilderByObjectByFieldNames(Object object, SystemAction systemAction,String... fieldNames) {
		return addInputBuilderByObjectByFieldNames(object, systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionAdd, fieldNames);
	}
	
	@Override
	public <T extends ComponentBuilder<?>> T addComponentBuilderByObjectByMethodName(Class<T> componentBuilderClass,Object object, String methodName,SystemAction systemAction) {
		T builder = (T) __inject__(ComponentBuilderGetter.class).setClazz(componentBuilderClass).setObject(object).setMethodName(methodName).setSystemAction(systemAction).execute().getOutput();
		if(builder instanceof CommandableBuilder) {
			CommandableBuilder commandableBuilder = (CommandableBuilder) builder;
			Object data = object instanceof FormData ? ((FormData<?>)object).getData() : object;
			commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).getProperties().setData(data);
		}
		addComponentBuilder(builder);
		return builder;
	}
	
	@Override
	public <T extends ComponentBuilder<?>> T addComponentBuilderByObjectByMethodName(Class<T> componentBuilderClass,Object object, String methodName) {
		return addComponentBuilderByObjectByMethodName(componentBuilderClass, object, methodName, null);
	}
	
	@Override
	public ComponentBuilder<?> addComponentBuilderByObjectByMethodName(Object object, String methodName,SystemAction systemAction) {
		Class<? extends ComponentBuilder<?>> builderClass =  __inject__(ComponentBuilderClassGetter.class).setClazz(object.getClass()).setMethodName(methodName)
				.execute().getOutput();
		return addComponentBuilderByObjectByMethodName(builderClass, object, methodName,systemAction);
	}
	
	@Override
	public ComponentBuilder<?> addComponentBuilderByObjectByMethodName(Object object, String methodName) {
		return addComponentBuilderByObjectByMethodName(object, methodName, null);
	}
	
	@Override
	public ViewBuilder addComponentBuilder(ComponentBuilder<?> componentBuilder) {
		getComponentsBuilder(Boolean.TRUE).getComponents(Boolean.TRUE).add(componentBuilder);
		return this;
	}
	
	@Override
	public ComponentsBuilder getComponentsBuilder() {
		return componentsBuilder;
	}

	@Override
	public ComponentsBuilder getComponentsBuilder(Boolean injectIfNull) {
		return (ComponentsBuilder) __getInjectIfNull__(FIELD_COMPONENTS_BUILDER, injectIfNull);
	}

	@Override
	public ViewBuilder setComponentsBuilder(ComponentsBuilder componentsBuilder) {
		this.componentsBuilder = componentsBuilder;
		return this;
	}
	
	@Override
	public CommandableBuilderByClassMap getCommandableByClassMap() {
		return commandableByClassMap;
	}
	
	@Override
	public CommandableBuilderByClassMap getCommandableByClassMap(Boolean injectIfNull) {
		return (CommandableBuilderByClassMap) __getInjectIfNull__(FIELD_COMMANDABLE_BY_CLASS_MAP, injectIfNull);
	}
	
	@Override
	public ViewBuilder setCommandableByClassMap(CommandableBuilderByClassMap commandableByClassMap) {
		this.commandableByClassMap = commandableByClassMap;
		return this;
	}
	
	@Override
	public CommandableBuilders getCommandables() {
		return commandables;
	}
	
	@Override
	public CommandableBuilders getCommandables(Boolean injectIfNull) {
		return (CommandableBuilders) __getInjectIfNull__(FIELD_COMMANDABLES, injectIfNull);
	}
	
	@Override
	public ViewBuilder setCommandables(CommandableBuilders commandables) {
		this.commandables = commandables;
		return this;
	}
	
	@Override
	public ViewBuilder addNavigationCommandablesBySystemActionClasses(Collection<Class<? extends SystemAction>> systemActionClasses) {
		if(__injectCollectionHelper__().isNotEmpty(systemActionClasses)) {
			CommandableBuilders commandables = getCommandables(Boolean.TRUE);
			for(Class<? extends SystemAction> index : systemActionClasses)
				commandables.add(__inject__(CommandableBuilder.class).setNavigationSystemAction(__inject__(index)));
		}
		return this;
	}
	
	@Override
	public ViewBuilder addNavigationCommandablesBySystemActionClasses(Class<? extends SystemAction>... systemActionClasses) {
		addNavigationCommandablesBySystemActionClasses(__injectCollectionHelper__().instanciate(systemActionClasses));
		return this;
	}
	
	@Override
	public ViewBuilder addNavigationCommandableBySystemAction(SystemAction systemAction, Object... parameters) {
		CommandableBuilder commandable = __inject__(CommandableBuilder.class).setNavigationSystemAction(systemAction);
		if(__inject__(ArrayHelper.class).isNotEmpty(parameters))
			commandable.setNavigationParameters(parameters);
		getCommandables(Boolean.TRUE).add(commandable);
		return this;
	}
	
	@Override
	public ViewBuilder addNavigationCommandableBySystemActionClass(Class<? extends SystemAction> systemActionClass,Object... parameters) {
		return addNavigationCommandableBySystemAction(__inject__(systemActionClass),parameters);
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}
	
	@Override
	public ViewBuilder setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}
	
	/**/
	
	public static final String FIELD_COMPONENTS_BUILDER = "componentsBuilder";
	public static final String FIELD_COMMANDABLE_BY_CLASS_MAP = "commandableByClassMap";
	public static final String FIELD_COMMANDABLES = "commandables";
}
