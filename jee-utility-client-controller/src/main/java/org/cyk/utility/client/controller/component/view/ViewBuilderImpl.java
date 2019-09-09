package org.cyk.utility.client.controller.component.view;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.array.ArrayHelperImpl;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderHelperImpl;
import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilderByClassMap;
import org.cyk.utility.client.controller.component.command.CommandableBuilders;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.output.OutputBuilder;
import org.cyk.utility.client.controller.data.FormData;
import org.cyk.utility.collection.CollectionHelperImpl;
import org.cyk.utility.field.FieldHelperImpl;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionFind;
import org.cyk.utility.system.action.SystemActionRedirect;
import org.cyk.utility.system.action.SystemActionUpdate;

public class ViewBuilderImpl extends AbstractVisibleComponentBuilderImpl<View> implements ViewBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<String,Class<? extends ComponentBuilder<?>>> BUILDERS_CLASSES_MAP = new HashMap<>();
	
	private ComponentsBuilder componentsBuilder;
	private ViewType type;
	private CommandableBuilderByClassMap commandableByClassMap;
	private CommandableBuilders commandables;
	private SystemAction systemAction;
		
	@Override
	protected void __execute__(View view) {
		super.__execute__(view);
		Object request = getRequest();
		Object context = getContext();
		Object uniformResourceLocatorMap = getUniformResourceLocatorMap();
		ComponentsBuilder componentsBuilder = getComponentsBuilder();
		
		CommandableBuilders commandables = getCommandables();
		if(CollectionHelperImpl.__isNotEmpty__(commandables)) {
			for(CommandableBuilder index : commandables.get()) {
				componentsBuilder.addComponents(index);
			}
		}
		if(componentsBuilder!=null && CollectionHelperImpl.__isNotEmpty__(componentsBuilder.getComponents())) {
			for(Object index : componentsBuilder.getComponents().get())
				if(index instanceof ComponentBuilder<?>) {
					if( ((ComponentBuilder<?>)index).getRequest() == null )
						((ComponentBuilder<?>)index).setRequest(request);
					if( ((ComponentBuilder<?>)index).getContext() == null )
						((ComponentBuilder<?>)index).setContext(context);
					if( ((ComponentBuilder<?>)index).getUniformResourceLocatorMap() == null )
						((ComponentBuilder<?>)index).setUniformResourceLocatorMap(uniformResourceLocatorMap);
				}
			if(componentsBuilder.getRequest() == null)
				componentsBuilder.setRequest(request);
			view.setComponents(componentsBuilder.execute().getOutput());
		}
		ViewType type = getType();
		if(type instanceof ViewTypeForm) {
			//view.getComponents().getLayout().getStyle(Boolean.TRUE).getClasses(Boolean.TRUE).add("AZERTY");
		}
		
		Components components = view.getComponents();
		if(CollectionHelperImpl.__isNotEmpty__(components)) {
			for(Component index : components.get()) {
				if(index instanceof Commandable) {
					Commandable commandable = (Commandable) index;
					if(commandable.getCommand()==null) {
						
					}else {
						commandable.getUpdatables(Boolean.TRUE).add(view.getComponents().getLayout());
						commandable.addCommandFunctionTryRunRunnableAt(new Runnable() {
							@Override
							public void run() {
								view.setFieldValueFromValue(Input.class);
							}
						},0);	
					}
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
		//Class<? extends InputBuilder<?,?>> inputBuilderClass =  __inject__(ComponentBuilderClassGetter.class).setField(FieldHelperImpl.__getFieldByNames__(object.getClass(), fieldNames))
		//		.execute().getOutput();
		Class<? extends InputBuilder<?,?>> inputBuilderClass =  (Class<? extends InputBuilder<?, ?>>) ComponentBuilderHelperImpl
				.__getComponentBuilderClass__(null, null, FieldHelperImpl.__getFieldByNames__(object.getClass(), fieldNames), null, null, null, null);
		return addInputBuilderByFieldName(inputBuilderClass, object, fieldNames);
	}
	
	@Override
	public <T extends ComponentBuilder<?>> T addComponentBuilderByObjectByFieldNames(Class<T> componentBuilderClass, Object object,String... fieldNames) {
		//TODO : collect all add in collection and use parrallel procssing technique to gain speed
		//T builder = (T) __inject__(ComponentBuilderGetter.class).setClazz(componentBuilderClass).setObject(object).addFieldNameStrings(fieldNames).execute().getOutput();
		T builder = (T) ComponentBuilderHelperImpl.__getComponentBuilder__(componentBuilderClass, object, null, fieldNames, null, null, null);
		addComponentBuilder(builder);
		return builder;
	}
	
	@Override
	public ComponentBuilder<?> addComponentBuilderByObjectByFieldNames(Object object,Class<?> componentBuilderBaseClass, String... fieldNames) {
		String key = object.getClass().getName()+"."+(componentBuilderBaseClass == null ? "" : componentBuilderBaseClass.getName())+"."+FieldHelperImpl.__join__(fieldNames);
		Class<? extends ComponentBuilder<?>> builderClass =  BUILDERS_CLASSES_MAP.get(key);
		if(builderClass == null) {
			//TODO to be optimized
			//builderClass = __inject__(ComponentBuilderClassGetter.class).setClazz(object.getClass()).addFieldNameStrings(fieldNames).setBaseClass(componentBuilderBaseClass).execute().getOutput();
			builderClass = ComponentBuilderHelperImpl.__getComponentBuilderClass__(object.getClass(), componentBuilderBaseClass, null,fieldNames, null, null, null);
			BUILDERS_CLASSES_MAP.put(key, builderClass);
		}
		//TODO to be optimized
		ComponentBuilder<?> componentBuilder = addComponentBuilderByObjectByFieldNames(builderClass, object, fieldNames);
		return componentBuilder;
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
		Boolean isEditable = systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionAdd 
				|| systemAction instanceof SystemActionFind || systemAction instanceof SystemActionRedirect;
		return addInputBuilderByObjectByFieldNames(object, isEditable, fieldNames);
	}
	
	@Override
	public <T extends ComponentBuilder<?>> T addComponentBuilderByObjectByMethodName(Class<T> componentBuilderClass,Object object, String methodName,SystemAction systemAction) {
		//T builder = (T) __inject__(ComponentBuilderGetter.class).setClazz(componentBuilderClass).setObject(object).setMethodName(methodName).setSystemAction(systemAction).execute().getOutput();
		T builder = (T) ComponentBuilderHelperImpl.__getComponentBuilder__(componentBuilderClass, object, null, null, null, methodName, systemAction);
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
		//Class<? extends ComponentBuilder<?>> builderClass =  __inject__(ComponentBuilderClassGetter.class).setClazz(object.getClass()).setMethodName(methodName)
		//		.execute().getOutput();
		Class<? extends ComponentBuilder<?>> builderClass = ComponentBuilderHelperImpl.__getComponentBuilderClass__(object.getClass(), null, null, null, null, methodName, null);
		return addComponentBuilderByObjectByMethodName(builderClass, object, methodName,systemAction);
	}
	
	@Override
	public ComponentBuilder<?> addComponentBuilderByObjectByMethodName(Object object, String methodName) {
		return addComponentBuilderByObjectByMethodName(object, methodName, null);
	}
	
	@Override
	public ViewBuilder addComponentBuilder(ComponentBuilder<?> componentBuilder) {
		if(componentBuilder != null) {
			if(componentBuilder.getRequest() == null)
				componentBuilder.setRequest(getRequest());
			if(componentBuilder.getContext() == null)
				componentBuilder.setContext(getContext());
			if(componentBuilder.getUniformResourceLocatorMap() == null)
				componentBuilder.setUniformResourceLocatorMap(getUniformResourceLocatorMap());
			getComponentsBuilder(Boolean.TRUE).getComponents(Boolean.TRUE).add(componentBuilder);	
		}
		return this;
	}
	
	@Override
	public ComponentsBuilder getComponentsBuilder() {
		return componentsBuilder;
	}

	@Override
	public ComponentsBuilder getComponentsBuilder(Boolean injectIfNull) {
		if(componentsBuilder == null && Boolean.TRUE.equals(injectIfNull))
			componentsBuilder = __inject__(ComponentsBuilder.class);
		return componentsBuilder;
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
		if(commandableByClassMap == null && Boolean.TRUE.equals(injectIfNull))
			commandableByClassMap = __inject__(CommandableBuilderByClassMap.class);
		return commandableByClassMap;
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
		if(commandables == null && Boolean.TRUE.equals(injectIfNull))
			commandables = __inject__(CommandableBuilders.class);
		return commandables;
	}
	
	@Override
	public ViewBuilder setCommandables(CommandableBuilders commandables) {
		this.commandables = commandables;
		return this;
	}
	
	@Override
	public ViewBuilder addNavigationCommandablesBySystemActionClasses(Collection<Class<? extends SystemAction>> systemActionClasses) {
		if(CollectionHelperImpl.__isNotEmpty__(systemActionClasses)) {
			CommandableBuilders commandables = getCommandables(Boolean.TRUE);
			for(Class<? extends SystemAction> index : systemActionClasses)
				commandables.add(__inject__(CommandableBuilder.class).setNavigationSystemAction(__inject__(index)));
		}
		return this;
	}
	
	@Override
	public ViewBuilder addNavigationCommandablesBySystemActionClasses(Class<? extends SystemAction>... systemActionClasses) {
		addNavigationCommandablesBySystemActionClasses(CollectionHelperImpl.__instanciate__(systemActionClasses));
		return this;
	}
	
	@Override
	public ViewBuilder addNavigationCommandableBySystemAction(SystemAction systemAction, Object... parameters) {
		CommandableBuilder commandable = __inject__(CommandableBuilder.class).setNavigationSystemAction(systemAction);
		if(ArrayHelperImpl.__isNotEmpty__(parameters))
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
