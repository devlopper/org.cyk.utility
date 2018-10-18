package org.cyk.utility.client.controller.view;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.VisibleComponentBuilders;
import org.cyk.utility.client.controller.component.VisibleComponents;
import org.cyk.utility.client.controller.component.VisibleComponentsBuilder;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.Commandables;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutBuilerItem;
import org.cyk.utility.client.controller.component.layout.LayoutWidthGetter;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class ViewBuilderImpl extends AbstractFunctionWithPropertiesAsInputImpl<View> implements ViewBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private VisibleComponentsBuilder visibleComponentsBuilder;
	private OutputStringTextBuilder nameOutputStringTextBuilder;
	private Commandables processingCommandables;
	private VisibleComponentBuilders componentBuilders;
	
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
		if(__injectCollectionHelper__().isNotEmpty(componentBuilders)) {
			for(ComponentBuilder<?> index : componentBuilders.get()) {
				VisibleComponent component = (VisibleComponent) index.execute().getOutput();
				if(component!=null)
					visibleComponentsBuilder.addComponents(component);
			}
		}
		
		//Processing commandables last
		Commandables processingCommandables = getProcessingCommandables();
		if(__injectCollectionHelper__().isNotEmpty(processingCommandables)) {
			for(Commandable index : processingCommandables.get())
				visibleComponentsBuilder.getComponents(Boolean.TRUE).add(index);
			//Processing commandables layout item
			Integer layoutWidth = __inject__(LayoutWidthGetter.class).execute().getOutput().intValue();
			Integer size = processingCommandables.get().size();
			Integer width = layoutWidth / size;
			Integer count = 1;
			for(@SuppressWarnings("unused") Commandable index : processingCommandables.get()) {
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
	public ViewBuilder addInputBuilder(InputBuilder<?,?> inputBuilder) {
		VisibleComponentBuilders builders = getComponentBuilders(Boolean.TRUE);
		if(inputBuilder!=null) {
			if(inputBuilder.getLabelBuilder()!=null)
				builders.add(inputBuilder.getLabelBuilder());
			builders.add(inputBuilder);
			if(inputBuilder.getMessageBuilder()!=null)
				builders.add(inputBuilder.getMessageBuilder());	
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
	public Commandables getProcessingCommandables() {
		return processingCommandables;
	}
	
	@Override
	public Commandables getProcessingCommandables(Boolean injectIfNull) {
		return (Commandables) __getInjectIfNull__(FIELD_PROCESSING_COMMANDABLES, injectIfNull);
	}
	
	@Override
	public ViewBuilder setProcessingCommandables(Commandables processingCommandables) {
		this.processingCommandables = processingCommandables;
		return this;
	}

	/**/
	
	public static final String FIELD_NAME_OUTPUT_STRING_TEXT_BUILDER = "nameOutputStringTextBuilder";
	public static final String FIELD_VISIBLE_COMPONENTS_BUILDER = "visibleComponentsBuilder";
	public static final String FIELD_PROCESSING_COMMANDABLES = "processingCommandables";
	public static final String FIELD_COMPONENT_BUILDERS = "componentBuilders";

	
}
