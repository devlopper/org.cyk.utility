package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerAfter;
import org.cyk.utility.client.controller.component.InputOutput;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.dialog.Dialog;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.input.InputBoolean;
import org.cyk.utility.client.controller.component.input.choice.InputChoice;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyCheckBox;
import org.cyk.utility.client.controller.component.layout.Insert;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.output.OutputString;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringLabelBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;
import org.cyk.utility.client.controller.component.output.OutputStringMessageBuilder;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.event.Events;
import org.cyk.utility.client.controller.icon.Icon;
import org.cyk.utility.client.controller.icon.IconIdentifierGetter;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.converter.ObjectConverter;
import org.cyk.utility.css.Style;
import org.cyk.utility.string.StringHelper;

public class ComponentBuilderExecuteListenerAfterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<ComponentBuilderExecuteListenerAfter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ComponentBuilderExecuteListenerAfterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				ComponentBuilder<?> componentBuilder = (ComponentBuilder<?>) getFunction().getObject();
				Component component = getFunction().getComponent();
				
				Events events = component.getEvents();
				if(events!=null) {
					for(Event index : events.get()) {
						String scriptCodeSource = index.getScript().getCodeSource();
						if(EventName.CLICK.equals(index.getName()) && __inject__(StringHelper.class).isNotBlank(scriptCodeSource))
							component.getProperties().setOnClick(scriptCodeSource);
					}
				}
				
				if(component instanceof VisibleComponent) {
					//VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>)componentBuilder;
					VisibleComponent visibleComponent = (VisibleComponent) component;
					visibleComponent.getProperties().setWidgetVar(componentBuilder.getOutputProperties().getWidgetVar());
					
					Style style = visibleComponent.getStyle();
					if(style!=null) {
						visibleComponent.getProperties().setStyleClass(style.getClassesAsString());
						visibleComponent.getProperties().setStyle(style.getValuesAsString());
					}
					
					if(component instanceof Commandable) {
						Icon icon = ((CommandableBuilder)componentBuilder).getIcon();
						if(icon != null)
							((Commandable)visibleComponent).setProperty(Properties.ICON, __inject__(IconIdentifierGetter.class).setIcon(icon).execute().getOutput());
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
							if(component instanceof InputChoice<?>) {
								component.getProperties().setConverter(__inject__(ObjectConverter.class));
								if(component instanceof InputChoiceManyCheckBox) {
									component.getProperties().setLayout("responsive");
									component.getProperties().setColumns(3);
								}
									
							}else if(component instanceof InputBoolean) {
								InputBoolean inputBoolean = (InputBoolean) inputOutput;
								component.getProperties().setOffLabel(inputBoolean.getFalseValue().getLabel());
								component.getProperties().setOnLabel(inputBoolean.getTrueValue().getLabel());
							}
						}
					}else if(component instanceof Menu) {
						componentBuilder.setIsTargetModelToBeBuilt(Boolean.TRUE);
					}else if(component instanceof Grid) {
						componentBuilder.setIsTargetModelToBeBuilt(Boolean.TRUE);
					}else if(component instanceof Commandable) {
						componentBuilder.setIsTargetModelToBeBuilt(Boolean.TRUE);
					}else if(component instanceof Dialog) {
						Dialog dialog = (Dialog) component;
						dialog.getProperties().setIdentifier(__inject__(ComponentHelper.class).getGlobalMessagesTargetDialogComponentIdentifier());
						
						
						dialog.getProperties().setResponsive(Boolean.TRUE);
						dialog.getProperties().setHeader("Messages");
						//dialog.getProperties().setHeader(__inject__(Application.class).getName());
						dialog.getProperties().setShowHeader(Boolean.TRUE);
						dialog.getProperties().setModal("true");
						dialog.getProperties().setAppendTo("@(body)");
						dialog.getProperties().setRendered(Boolean.TRUE);
						dialog.getProperties().setMinHeight("100");
						dialog.getProperties().setMinWidth("400");
						dialog.getProperties().setHeight("150");
						dialog.getProperties().setWidth("770");
						dialog.getProperties().setMaxHeight("550");
						dialog.getProperties().setMaxWidth("900");
						dialog.getProperties().setGetter(Properties.VISIBLE, new Properties.Getter() {
							@Override
							public Object execute(Properties properties, Object key, Object value, Object nullValue) {
								String clientId = __inject__(ComponentHelper.class).getGlobalMessagesOwnerDialogComponentClientIdentifier();
								return FacesContext.getCurrentInstance().getMessages(clientId).hasNext();
							}
						});
					}
				}else if(component instanceof Insert) {
					((Insert)component).getProperties().setName(((Insert)component).getName());
				}
			}
		});
	}
	
}