package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.annotation.Default;
import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentBuilderExecuteListenerAfter;
import org.cyk.utility.client.controller.component.InputOutput;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.dialog.Dialog;
import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.FileImage;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.input.InputBoolean;
import org.cyk.utility.client.controller.component.input.InputDate;
import org.cyk.utility.client.controller.component.input.InputDateTime;
import org.cyk.utility.client.controller.component.input.InputFile;
import org.cyk.utility.client.controller.component.input.choice.InputChoice;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceMany;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyAutoComplete;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyCheckBox;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOne;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneAutoComplete;
import org.cyk.utility.client.controller.component.layout.Insert;
import org.cyk.utility.client.controller.component.link.Link;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeColumnContext;
import org.cyk.utility.client.controller.component.output.Output;
import org.cyk.utility.client.controller.component.output.OutputFile;
import org.cyk.utility.client.controller.component.output.OutputString;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringLabelBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;
import org.cyk.utility.client.controller.component.output.OutputStringMessageBuilder;
import org.cyk.utility.client.controller.component.text.Text;
import org.cyk.utility.client.controller.component.tree.Tree;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.event.Events;
import org.cyk.utility.client.controller.icon.Icon;
import org.cyk.utility.client.controller.icon.IconIdentifierGetter;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.converter.DateConverter;
import org.cyk.utility.client.controller.web.jsf.converter.ObjectConverter;
import org.cyk.utility.css.Style;
import org.cyk.utility.string.StringHelper;
import org.primefaces.model.DefaultStreamedContent;

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
						index.getProperties().setManyIfNull(new Object[] {Properties.IMMEDIATE,Properties.ASYNC,Properties.DISABLED,Properties.PARTIAL_SUBMIT
								,Properties.RESET_VALUES,Properties.IGNORE_AUTO_UPDATE}, Boolean.FALSE);
						index.getProperties().setManyIfNull(new Object[] {Properties.GLOBAL,Properties.SKIP_CHILDREN}, Boolean.TRUE);
						index.getProperties().setIfNull(Properties.EVENT,StringUtils.remove(index.getName().name().toLowerCase(), "_") );
						
						index.getProperties().setIfNull(Properties.FUNCTION,index.getFunction());
						
						String scriptCodeSource = index.getScript() == null ? null :index.getScript().getCodeSource();
						if(__inject__(StringHelper.class).isNotBlank(scriptCodeSource)) {
							if(EventName.CLICK.equals(index.getName()))
								component.getProperties().setOnClick(scriptCodeSource);
							else if(EventName.COMPLETE.equals(index.getName()))
								component.getProperties().setOnComplete(scriptCodeSource);
						}
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
						Icon icon = ((Commandable)component).getIcon();
						if(icon != null)
							((Commandable)visibleComponent).setProperty(Properties.ICON, __inject__(IconIdentifierGetter.class).setIcon(icon).execute().getOutput());
					}
					
					if(component instanceof InputOutput<?>) {
						InputOutput<?> inputOutput = (InputOutput<?>) component;
						inputOutput.setPropertyValue(inputOutput.getValue());
						
						if(inputOutput instanceof Output<?>) {
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
							}else if(inputOutput instanceof OutputFile) {
								
							}
						}
						
						if(inputOutput instanceof Input<?>) {
							component.getProperties().setRequired(Boolean.FALSE.equals(((Input<?>)inputOutput).getIsNullable()));	
							if(component instanceof InputChoice<?>) {
								component.getProperties().setConverter(__inject__(ObjectConverter.class));
								component.getProperties().setFilter("true");
								component.getProperties().setFilterMatchMode("contains");
								component.getProperties().setDynamic(Boolean.FALSE);
								
								if(component instanceof InputChoiceOne) {
									if(component instanceof InputChoiceOneAutoComplete) {
										component.getProperties().setMultiple("false");
										Integer maximumNumberOfChoice = ((InputChoice<?>)inputOutput).getMaximumNumberOfChoice();
										if(maximumNumberOfChoice != null && maximumNumberOfChoice>0)
											component.getProperties().setMaxResults(maximumNumberOfChoice);
										component.getProperties().setCache(Boolean.TRUE);
										component.getProperties().setCacheTimeout(300000);// 5 minutes
										component.getProperties().setForceSelection(Boolean.TRUE);
										component.getProperties().setDropDown(Boolean.TRUE);
										component.getProperties().setDropDownMode("blank");
									}
								}else if(component instanceof InputChoiceMany) {
									if(component instanceof InputChoiceManyCheckBox) {
										component.getProperties().setLayout("responsive");
										component.getProperties().setColumns(3);
									}else if(component instanceof InputChoiceManyAutoComplete) {
										component.getProperties().setMultiple("true");
										Integer maximumNumberOfChoice = ((InputChoice<?>)inputOutput).getMaximumNumberOfChoice();
										if(maximumNumberOfChoice != null && maximumNumberOfChoice>0)
											component.getProperties().setMaxResults(maximumNumberOfChoice);
										component.getProperties().setCache(Boolean.TRUE);
										component.getProperties().setCacheTimeout(300000);// 5 minutes
										component.getProperties().setForceSelection(Boolean.TRUE);
										component.getProperties().setDropDown(Boolean.TRUE);
										component.getProperties().setDropDownMode("blank");
									}
								}				
							}else if(component instanceof InputBoolean) {
								InputBoolean inputBoolean = (InputBoolean) inputOutput;
								component.getProperties().setOffLabel(inputBoolean.getFalseValue().getLabel());
								component.getProperties().setOnLabel(inputBoolean.getTrueValue().getLabel());
							}else if(component instanceof InputFile) {
								InputFile inputFile = (InputFile) inputOutput;
								inputFile.getProperties().setMode("simple");
							}else if(component instanceof InputDate) {
								InputDate inputDate = (InputDate) inputOutput;
								inputDate.getProperties().setPattern("d/M/y");								
								inputDate.getProperties().setConverter(__inject__(DateConverter.class));
								inputDate.getProperties().setLocale(Locale.FRENCH);
								inputDate.getProperties().setTimeZone(TimeZone.getDefault());
							}else if(component instanceof InputDateTime) {
								InputDateTime inputDateTime = (InputDateTime) inputOutput;
								inputDateTime.getProperties().setPattern("d/M/y H:m");
								inputDateTime.getProperties().setConverter(__inject__(DateConverter.class));
								inputDateTime.getProperties().setLocale(Locale.FRENCH);
								inputDateTime.getProperties().setTimeZone(TimeZone.getDefault());
							}
						}
					}else if(component instanceof Menu) {
						Menu menu = (Menu) component;
						setTargetModelIsDerivable(component);
						
						Object linkedTo = menu.getLinkedTo();
						if(linkedTo instanceof Component) {
							menu.getProperties().setFor(((Component)linkedTo).getIdentifier());	
						}
						if(menu.getRenderType() instanceof MenuRenderTypeColumnContext) {
							if(linkedTo instanceof Tree) {
								menu.getProperties().setNodeType("default");
							}
						}
					}else if(component instanceof Tree) {
						setTargetModelIsDerivable(component);
					}else if(component instanceof Grid) {
						setTargetBindingIsDerivable(component);
					}else if(component instanceof Commandable) {
						setTargetBindingIsDerivable(component);
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
					}else if(component instanceof File) {
						File file = (File) component;
						org.cyk.utility.file.File __file__ = file.getValue();
						if(Boolean.TRUE.equals(file.getIsEmbedded())) {
							byte[] bytes = __file__.getBytes();
							if(bytes == null) {
								
							}else{
								Object value = null;
								if(Boolean.TRUE.equals(__file__.isImage()))
									value = new DefaultStreamedContent(new ByteArrayInputStream(bytes), __file__.getMimeType());
								else if(Boolean.TRUE.equals(__file__.isText()))
									value = new String(bytes);
								file.getProperties().setValue(value);
							}
						}else {
							file.getProperties().setUniformResourceLocator(__file__.getUniformResourceLocator());
						}
					}else if(component instanceof FileImage) {
						FileImage image = (FileImage) component;
						image.getProperties().setWidth(image.getWidth());
						image.getProperties().setHeight(image.getHeight());
					}else if(component instanceof Text) {
						Text text = (Text) component;
						String characters = text.getCharacters();
						//TODO some processing must be donne for web
						characters = StringUtils.replace(characters, "\r\n", "<br/>");
						characters = StringUtils.replace(characters, "\n", "<br/>");
						text.getProperties().setValue(characters);
					}else if(component instanceof Link) {
						Link link = (Link) component;
						link.getProperties().setHref(link.getUniformResourceLocator());
					}
				}else if(component instanceof Insert) {
					((Insert)component).getProperties().setName(((Insert)component).getName());
				}
			}
		});
	}
	
	private static void setTargetModelIsDerivable(Component component) {
		component.getTargetModel(Boolean.TRUE).setIsDerivable(Boolean.TRUE).addValueGetterClassQualifiers(Default.class).setValue(component);
	}
	
	private static void setTargetBindingIsDerivable(Component component) {
		component.getTargetBinding(Boolean.TRUE).setIsDerivable(Boolean.TRUE).addValueGetterClassQualifiers(Default.class).setValue(component);
	}
	
}