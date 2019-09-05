package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.AbstractComponentBuilderExecuteListenerImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerBefore;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.InputOutput;
import org.cyk.utility.client.controller.component.InputOutputBuilder;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.cyk.utility.collection.CollectionHelper;

@Primefaces
public class ComponentBuilderExecuteListenerBeforeFunctionRunnableImpl extends AbstractComponentBuilderExecuteListenerImpl implements  ComponentBuilderExecuteListenerBefore,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void execute() {
		Component component = getComponent();
		
		if(component instanceof VisibleComponent) {
			VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>)getObject();
			VisibleComponent visibleComponent = (VisibleComponent) component;
			//visibleComponentBuilder.getStyle(Boolean.TRUE).addClasses("cyk_component");
			
			//String identifierAsStyleClass = __inject__(RandomHelper.class).
			//visibleComponentBuilder.getStyle(Boolean.TRUE).addClasses("cyk_component");
			
			//String identifierAsStyleClass = visibleComponentBuilder.getOutputProperties().getIdentifier().toString();
			String identifierAsStyleClass = (String) visibleComponentBuilder.getOutputProperty(Properties.IDENTIFIER_AS_STYLE_CLASS);//.getIdentifierAsStyleClass().toString();
			//visibleComponentBuilder.getOutputProperties().setIdentifierAsStyleClass(identifierAsStyleClass);
			//System.out.println("CID : "+identifierAsStyleClass);
			visibleComponentBuilder.addStyleClasses(identifierAsStyleClass);
			//visibleComponentBuilder.addStyleClasses("AZERTY");
			/*
			ComponentRoles roles = visibleComponentBuilder.getRoles();
			if(__inject__(CollectionHelper.class).isNotEmpty(roles)) {
				for(ComponentRole index : roles.get()) {
					String styleClass = __inject__(ComponentRoleStyleClassGetter.class).setRole(index).execute().getOutput();
					if(__inject__(StringHelper.class).isNotBlank(styleClass))
						visibleComponentBuilder.addStyleClasses(styleClass);
				}
			}
			*/
			/*
			if(__inject__(CollectionHelper.class).contains(visibleComponentBuilder.getRoles(), ComponentRole.TITLE))
				visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_view_title");
			if(__inject__(CollectionHelper.class).contains(visibleComponentBuilder.getRoles(), ComponentRole.GRID))
				visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_table");
			if(__inject__(CollectionHelper.class).contains(visibleComponentBuilder.getRoles(), ComponentRole.ROW))
				visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_row");
			*/
			if(visibleComponent instanceof InputOutput<?>) {
				InputOutputBuilder<?, ?> inputOutputBuilder = (InputOutputBuilder<?,?>) visibleComponentBuilder;
				
				if(inputOutputBuilder instanceof InputBuilder<?,?>) {
					//InputBuilder<?,?> inputBuilder = (InputBuilder<?,?>)componentBuilder;
					//inputBuilder.addRoles(ComponentRole.INPUT);
					
				}else if(inputOutputBuilder instanceof InputBuilder<?,?>) {
					//OutputBuilder<?,?> outputBuilder = (OutputBuilder<?,?>)componentBuilder;
					//outputBuilder.addRoles(ComponentRole.OUTPUT);
				}
			}
			
			if(visibleComponentBuilder instanceof LayoutBuilder) {
				LayoutBuilder layoutBuilder = (LayoutBuilder) visibleComponentBuilder;
				layoutBuilder.getStyle(Boolean.TRUE).addClasses("cyk_layout");
				
				// TODO ui-g must be get from function
				layoutBuilder.getStyle(Boolean.TRUE).addClasses("ui-g");
				
				if(DependencyInjection.inject(CollectionHelper.class).contains(layoutBuilder.getRoles(), ComponentRole.GRID))
					layoutBuilder.addStyleClasses("cyk_layout_grid");
				
				layoutBuilder.setOutputProperty(Properties.LAYOUT,"block");//.setLayout("block");
			}
			
			if(visibleComponentBuilder instanceof LayoutItemBuilder) {
				LayoutItemBuilder layoutItemBuilder = (LayoutItemBuilder) visibleComponentBuilder;
				layoutItemBuilder.getStyle(Boolean.TRUE).addClasses("cyk_layout_item");
				
			}
		}
	}
	
}