package org.cyk.utility.client.controller.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderClassGetter;
import org.cyk.utility.client.controller.component.ComponentBuilderGetter;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.VisibleComponentBuilders;
import org.cyk.utility.client.controller.component.VisibleComponents;
import org.cyk.utility.client.controller.component.VisibleComponentsBuilder;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilders;
import org.cyk.utility.client.controller.component.command.CommandableButtonBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutBuilerItem;
import org.cyk.utility.client.controller.component.layout.LayoutWidthGetter;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public class ViewBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<View> implements ViewBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private VisibleComponentsBuilder visibleComponentsBuilder;
	private OutputStringTextBuilder nameOutputStringTextBuilder;
	private CommandableBuilders processingCommandableBuilders;
	private VisibleComponentBuilders componentBuilders;
	private ViewType type;
	private CommandableBuilder<?> submitCommandableBuilder,closeCommandableBuilder;
	
	@Override
	protected View __execute__() throws Exception {
		View view = __inject__(View.class);
		VisibleComponentsBuilder visibleComponentsBuilder = getVisibleComponentsBuilder();
		if(visibleComponentsBuilder == null)
			visibleComponentsBuilder = __inject__(VisibleComponentsBuilder.class);
		
		OutputStringTextBuilder nameOutputStringTextBuilder = getNameOutputStringTextBuilder();
		if(nameOutputStringTextBuilder!=null) {
			//Name first
			__inject__(CollectionHelper.class).addElementAt(visibleComponentsBuilder.getComponents(Boolean.TRUE), 0, nameOutputStringTextBuilder.execute().getOutput());
			//Name layout item
			visibleComponentsBuilder.getLayoutBuilder(Boolean.TRUE).getItems(Boolean.TRUE).addAt(__inject__(LayoutBuilerItem.class).setWidthForAll(null),0);
		}
		
		VisibleComponentBuilders componentBuilders = getComponentBuilders();
		Collection<ComponentBuilder<?>> finalComponentBuilders = null;
		if(__injectCollectionHelper__().isNotEmpty(componentBuilders)) {
			finalComponentBuilders = new ArrayList<>();
			for(ComponentBuilder<?> index : componentBuilders.get()) {
				if(index!=null) {
					//Derived builders
					if(index instanceof InputBuilder<?, ?>) {
						InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) index;
						if(inputBuilder.getLabelBuilder()!=null) {
							finalComponentBuilders.add(inputBuilder.getLabelBuilder());
						}
					}
					finalComponentBuilders.add(index);
					if(index instanceof InputBuilder<?, ?>) {
						InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) index;
						if(inputBuilder.getMessageBuilder()!=null) {
							finalComponentBuilders.add(inputBuilder.getMessageBuilder());
						}
					}
					
					//Width proportions
					if(index instanceof InputBuilder<?, ?>) {
						InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) index;
						if(inputBuilder.getLabelBuilder()==null)
							if(inputBuilder.getMessageBuilder()==null) {
								//Nothing to do
							} else {
								inputBuilder.setAreaWidthProportionsForNotPhone(10);
								inputBuilder.getMessageBuilder().setAreaWidthProportionsForNotPhone(2);
							}
						else {
							if(inputBuilder.getMessageBuilder()==null) {
								inputBuilder.getLabelBuilder().setAreaWidthProportionsForNotPhone(2);
								inputBuilder.setAreaWidthProportionsForNotPhone(10);
							} else {
								inputBuilder.getLabelBuilder().setAreaWidthProportionsForNotPhone(2);
								inputBuilder.setAreaWidthProportionsForNotPhone(8);
								inputBuilder.getMessageBuilder().setAreaWidthProportionsForNotPhone(2);
							}
						}								
					}
				}
			}
		}
		
		if(__injectCollectionHelper__().isNotEmpty(finalComponentBuilders)) {
			for(ComponentBuilder<?> index : finalComponentBuilders) {
				if(index instanceof CommandableBuilder<?>) {
					CommandableBuilder<?> commandableBuilder = (CommandableBuilder<?>) index;
					
					Collection<Runnable> runnables = commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).getRunnables();
					if( runnables == null ) {
						runnables = new ArrayList<>();
						commandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).setRunnables(runnables);
					}
					__injectCollectionHelper__().addElementAt(runnables, 0, new Runnable() {
						@Override
						public void run() {
							view.setInputOutputFieldValueFromValue();
						}
					});
				}
				VisibleComponent component = (VisibleComponent) index.execute().getOutput();
				if(component!=null)
					visibleComponentsBuilder.addComponents(component);
			}
		}
		
		ViewType type = getType();
		if(type instanceof ViewTypeForm) {
			/*CommandableBuilder<?> submitCommandableBuilder = getSubmitCommandableBuilder(Boolean.TRUE);
			submitCommandableBuilder.setOutputProperty(Properties.VALUE, "Submit");
			submitCommandableBuilder.setCommandFunctionActionClass(SystemActionCreate.class);
			Collection<Runnable> runnables = submitCommandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).getRunnables();
			if( runnables == null ) {
				runnables = new ArrayList<>();
				submitCommandableBuilder.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).setRunnables(runnables);
			}
			__injectCollectionHelper__().addElementAt(runnables, 0, new Runnable() {
				@Override
				public void run() {
					view.setInputOutputFieldValueFromValue();
				}
			});
			
			getProcessingCommandableBuilders(Boolean.TRUE).add(submitCommandableBuilder);
			*/
		}
		
		/*
		addProcessingCommandableButtonBuilder("Close", SystemActionCreate.class, new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
			}
		});
		*/
		//Processing commandables last
		CommandableBuilders processingCommandableBuilders = getProcessingCommandableBuilders();
		Collection<Commandable> processingCommandables = null;
		if(__injectCollectionHelper__().isNotEmpty(processingCommandableBuilders)) {
			processingCommandables = new ArrayList<>();
			for(CommandableBuilder<?> index : processingCommandableBuilders.get()) {
				Commandable commandable = index.execute().getOutput();
				visibleComponentsBuilder.getComponents(Boolean.TRUE).add(commandable);
				processingCommandables.add(commandable);
			}
			//Processing commandables layout item
			Integer layoutWidth = __inject__(LayoutWidthGetter.class).execute().getOutput().intValue();
			Integer size = processingCommandables.size();
			Integer width = layoutWidth / size;
			Integer count = 1;
			for(@SuppressWarnings("unused") Commandable index : processingCommandables) {
				if(count == size )
					width = width + layoutWidth % size;
				visibleComponentsBuilder.getLayoutBuilder(Boolean.TRUE).addItems(__inject__(LayoutBuilerItem.class).setWidthForAll(width).setWidthForPhone(layoutWidth));
				count++;
			}
		}
		
		VisibleComponents visibleComponents = visibleComponentsBuilder.execute().getOutput();

		view.setVisibleComponents(visibleComponents);
		return view;
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
	public OutputStringTextBuilder getNameOutputStringTextBuilder() {
		return nameOutputStringTextBuilder;
	}
	
	@Override
	public OutputStringTextBuilder getNameOutputStringTextBuilder(Boolean injectIfNull) {
		return (OutputStringTextBuilder) __getInjectIfNull__(FIELD_NAME_OUTPUT_STRING_TEXT_BUILDER, injectIfNull);
	}

	@Override
	public ViewBuilder setNameOutputStringTextBuilder(OutputStringTextBuilder nameOutputStringTextBuilder) {
		this.nameOutputStringTextBuilder = nameOutputStringTextBuilder;
		return this;
	}
	
	@Override
	public ViewBuilder setNameOutputPropertyValue(Object value) {
		getNameOutputStringTextBuilder(Boolean.TRUE).setOutputPropertyValue(value);
		return this;
	}
	
	@Override
	public ViewBuilder addInputBuilder(InputBuilder<?,?> inputBuilder) {
		VisibleComponentBuilders builders = getComponentBuilders(Boolean.TRUE);
		if(inputBuilder!=null) {
			builders.add(inputBuilder);
		}
		return this;
	}

	@Override
	public <T extends InputBuilder<?, ?>> T addInputBuilder(Class<T> inputBuilderClass,Object outputPropertyRequired,Object labelBuilderOutputPropertyValue) {
		T inputBuilder = __inject__(inputBuilderClass);
		if(outputPropertyRequired!=null)
			inputBuilder.setOutputPropertyRequired(outputPropertyRequired);
		if(labelBuilderOutputPropertyValue!=null)
			inputBuilder.getLabelBuilder(Boolean.TRUE).setOutputPropertyValue(labelBuilderOutputPropertyValue);
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
		inputBuilder.getLabelBuilder(Boolean.TRUE).setOutputPropertyValue(inputBuilder.getFieldNameStrings().get().toString());
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
	public <T extends ComponentBuilder<?>> T addComponentBuilderByFieldName(Class<T> componentBuilderClass, Object object,String... fieldNames) {
		T builder = (T) __inject__(ComponentBuilderGetter.class).setClazz(componentBuilderClass).setObject(object).addFielNameStrings(fieldNames).execute().getOutput();
		addComponentBuilder((VisibleComponentBuilder<?>) builder);
		return builder;
	}
	
	@Override
	public ComponentBuilder<?> addComponentBuilderByFieldName(Object object, String... fieldNames) {
		Class<? extends ComponentBuilder<?>> builderClass =  __inject__(ComponentBuilderClassGetter.class).setField(__injectCollectionHelper__().getFirst(__inject__(FieldGetter.class)
				.execute(object.getClass(),  __injectFieldHelper__().concatenate(fieldNames)).getOutput())).execute().getOutput();
		return addComponentBuilderByFieldName(builderClass, object, fieldNames);
	}
	
	@Override
	public <T extends ComponentBuilder<?>> T addComponentBuilderByMethodName(Class<T> componentBuilderClass,Object object, String methodName) {
		T builder = (T) __inject__(ComponentBuilderGetter.class).setClazz(componentBuilderClass).setObject(object).setMethodName(methodName).execute().getOutput();
		addComponentBuilder((VisibleComponentBuilder<?>) builder);
		return builder;
	}
	
	@Override
	public ComponentBuilder<?> addComponentBuilderByMethodName(Object object, String methodName) {
		Class<? extends ComponentBuilder<?>> builderClass =  __inject__(ComponentBuilderClassGetter.class).setClazz(object.getClass()).setMethodName(methodName).getOutput();
		return addComponentBuilderByMethodName(builderClass, object, methodName);
	}
	
	@Override
	public ViewBuilder addComponentBuilder(VisibleComponentBuilder<?> componentBuilder) {
		VisibleComponentBuilders builders = getComponentBuilders(Boolean.TRUE);
		if(componentBuilder!=null) {
			builders.add(componentBuilder);
		}
		return this;
	}
	
	@Override
	public VisibleComponentsBuilder getVisibleComponentsBuilder() {
		return visibleComponentsBuilder;
	}

	@Override
	public VisibleComponentsBuilder getVisibleComponentsBuilder(Boolean injectIfNull) {
		return (VisibleComponentsBuilder) __getInjectIfNull__(FIELD_VISIBLE_COMPONENTS_BUILDER, injectIfNull);
	}

	@Override
	public ViewBuilder setVisibleComponentsBuilder(VisibleComponentsBuilder visibleComponentsBuilder) {
		this.visibleComponentsBuilder = visibleComponentsBuilder;
		return this;
	}

	@Override
	public VisibleComponentBuilders getComponentBuilders() {
		return componentBuilders;
	}

	@Override
	public VisibleComponentBuilders getComponentBuilders(Boolean injectIfNull) {
		return (VisibleComponentBuilders) __getInjectIfNull__(FIELD_COMPONENT_BUILDERS, injectIfNull);
	}

	@Override
	public ViewBuilder setComponentBuilders(VisibleComponentBuilders componentBuilders) {
		this.componentBuilders = componentBuilders;
		return this;
	}
	
	@Override
	public CommandableBuilders getProcessingCommandableBuilders() {
		return processingCommandableBuilders;
	}
	
	@Override
	public CommandableBuilders getProcessingCommandableBuilders(Boolean injectIfNull) {
		return (CommandableBuilders) __getInjectIfNull__(FIELD_PROCESSING_COMMANDABLE_BUILDERS, injectIfNull);
	}
	
	@Override
	public ViewBuilder setProcessingCommandableBuilders(CommandableBuilders processingCommandableBuilders) {
		this.processingCommandableBuilders = processingCommandableBuilders;
		return this;
	}
	
	@Override
	public <T extends CommandableBuilder<?>> T addProcessingCommandableBuilder(Class<T> aClass,Object commandableOutputPropertyValue,Class<? extends SystemAction> systemActionClass,Runnable...runnables) {
		T commandableBuilder = __inject__(aClass);
		getProcessingCommandableBuilders(Boolean.TRUE).add(commandableBuilder);
		commandableBuilder.setOutputProperty(Properties.VALUE, commandableOutputPropertyValue);
		commandableBuilder.addCommandFunctionTryRunRunnable(runnables);
		commandableBuilder.setCommandFunctionActionClass(systemActionClass);
		return commandableBuilder;
	}
	
	@Override
	public CommandableButtonBuilder addProcessingCommandableButtonBuilder(Object commandableOutputPropertyValue, Class<? extends SystemAction> systemActionClass, Runnable...runnables) {
		return addProcessingCommandableBuilder(CommandableButtonBuilder.class, commandableOutputPropertyValue, systemActionClass, runnables);
	}
	
	@Override
	public CommandableBuilder<?> getSubmitCommandableBuilder() {
		return submitCommandableBuilder;
	}
	
	@Override
	public CommandableBuilder<?> getSubmitCommandableBuilder(Boolean injectIfNull) {
		CommandableBuilder<?> submitCommandableBuilder = getSubmitCommandableBuilder();
		if(submitCommandableBuilder==null)
			setSubmitCommandableBuilder(submitCommandableBuilder = __inject__(CommandableButtonBuilder.class));
		return submitCommandableBuilder;
	}
	
	@Override
	public ViewBuilder setSubmitCommandableBuilder(CommandableBuilder<?> submitCommandableBuilder) {
		this.submitCommandableBuilder = (CommandableButtonBuilder) submitCommandableBuilder;
		return this;
	}
	
	@Override
	public CommandableBuilder<?> getCloseCommandableBuilder() {
		return closeCommandableBuilder;
	}
	
	@Override
	public ViewBuilder setCloseCommandableBuilder(CommandableBuilder<?> closeCommandableBuilder) {
		this.closeCommandableBuilder = (CommandableButtonBuilder) closeCommandableBuilder;
		return this;
	}

	/**/
	
	public static final String FIELD_NAME_OUTPUT_STRING_TEXT_BUILDER = "nameOutputStringTextBuilder";
	public static final String FIELD_VISIBLE_COMPONENTS_BUILDER = "visibleComponentsBuilder";
	public static final String FIELD_PROCESSING_COMMANDABLE_BUILDERS = "processingCommandableBuilders";
	public static final String FIELD_SUBMIT_COMMANDABLE_BUILDER = "submitCommandableBuilder";
	public static final String FIELD_COMPONENT_BUILDERS = "componentBuilders";

}
