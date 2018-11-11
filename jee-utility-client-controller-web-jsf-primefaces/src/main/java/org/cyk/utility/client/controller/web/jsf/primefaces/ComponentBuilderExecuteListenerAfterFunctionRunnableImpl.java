package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerAfter;
import org.cyk.utility.client.controller.component.InputOutput;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.layout.Insert;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.output.OutputString;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringLabelBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;
import org.cyk.utility.client.controller.component.output.OutputStringMessageBuilder;
import org.cyk.utility.css.Style;

public class ComponentBuilderExecuteListenerAfterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentBuilderExecuteListenerAfter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentBuilderExecuteListenerAfterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				ComponentBuilder<?> componentBuilder = (ComponentBuilder<?>) getFunction().getObject();
				Component component = getFunction().getComponent();
				
				if(component instanceof VisibleComponent) {
					//VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>)componentBuilder;
					VisibleComponent visibleComponent = (VisibleComponent) component;
					Style style = visibleComponent.getStyle();
					if(style!=null) {
						visibleComponent.getProperties().setStyleClass(style.getClassesAsString());
						visibleComponent.getProperties().setStyle(style.getValuesAsString());
					}
					
					if(component instanceof InputOutput<?>) {
						InputOutput<?> inputOutput = (InputOutput<?>) component;
						inputOutput.setPropertyValue(inputOutput.getValue());
						
						if(inputOutput instanceof OutputString) {
							OutputString outputString = (OutputString) inputOutput;
							
							if(outputString instanceof OutputStringLabel) {
								OutputStringLabel outputStringLabel = (OutputStringLabel) outputString;
								outputStringLabel.getProperties().setFor( ((OutputStringLabelBuilder)componentBuilder).getInputBuilder().getOutputProperties().getIdentifier() );	
							}
							
							if(outputString instanceof OutputStringMessage) {
								OutputStringMessage outputStringMessage = (OutputStringMessage) outputString;
								outputStringMessage.getProperties().setFor( ((OutputStringMessageBuilder)componentBuilder).getInputBuilder().getOutputProperties().getIdentifier() );
								//outputStringMessage.getProperties().setDisplay("tooltip");
							}		
						}
						
						if(inputOutput instanceof Input<?>) {
							
						}
					}else if(component instanceof Menu) {
						componentBuilder.setIsTargetModelToBeBuilt(Boolean.TRUE);
					}else if(component instanceof Grid) {
						componentBuilder.setIsTargetModelToBeBuilt(Boolean.TRUE);
					}else if(component instanceof Commandable) {
						//Commandable commandable = (Commandable)component;
						componentBuilder.setIsTargetModelToBeBuilt(Boolean.TRUE);
						/*
						commandable.getProperties().setValue(((Commandable)component).getName());
						String type = null;
						if(commandable.getCommand() == null)
							type = "button";
						else
							type = "submit";
						commandable.getProperties().setType(type);
						String onClick = null;
						if(commandable.getNavigation()!=null) {
							onClick = "window.open('"+commandable.getNavigation().getUniformResourceLocator()+"','_self');return false";
						}
						commandable.getProperties().setOnClick(onClick);
						*/
					}
				}else if(component instanceof Insert) {
					((Insert)component).getProperties().setName(((Insert)component).getName());
				}
				
				//if(Boolean.TRUE.equals(componentBuilder.getIsTargetModelToBeBuilt()))
				//	component.setTargetModel(__inject__(ComponentTargetModelBuilder.class).setComponent(component).execute().getOutput());
			}
		});
	}
	
}