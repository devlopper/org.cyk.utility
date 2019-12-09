package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.icon.IconHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.AbstractObjectLifeCycleListenerImpl;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.InputOutput;
import org.cyk.utility.client.controller.component.InputOutputBuilder;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.dialog.Dialog;
import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.FileImage;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.input.Input;
import org.cyk.utility.client.controller.component.input.InputBoolean;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.input.InputDate;
import org.cyk.utility.client.controller.component.input.InputDateTime;
import org.cyk.utility.client.controller.component.input.InputFile;
import org.cyk.utility.client.controller.component.input.InputFileBuilder;
import org.cyk.utility.client.controller.component.input.InputNumber;
import org.cyk.utility.client.controller.component.input.choice.ChoicesLayout;
import org.cyk.utility.client.controller.component.input.choice.ChoicesLayoutResponsive;
import org.cyk.utility.client.controller.component.input.choice.InputChoice;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceMany;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyAutoComplete;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceManyCheckBox;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOne;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneAutoComplete;
import org.cyk.utility.client.controller.component.layout.Insert;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
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
import org.cyk.utility.client.controller.component.tree.TreeBuilder;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.client.controller.event.EventName;
import org.cyk.utility.client.controller.event.Events;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.converter.DateConverter;
import org.cyk.utility.client.controller.web.jsf.converter.NumberConverter;
import org.cyk.utility.client.controller.web.jsf.converter.ObjectConverter;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.cyk.utility.css.Style;
import org.cyk.utility.random.RandomHelper;
import org.primefaces.model.DefaultStreamedContent;

@ApplicationScoped @Primefaces
public class ObjectLifeCycleListenerImpl extends AbstractObjectLifeCycleListenerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String IDENTIFIER_FORMAT = "%s_%s";
	
	/**/
	
	protected void listenPostConstructComponentBuilder(ComponentBuilder<?> componentBuilder) {
		super.listenPostConstructComponentBuilder(componentBuilder);
		Properties outputProperties = componentBuilder.getOutputProperties(Boolean.TRUE);
		
		outputProperties.setIdentifier(String.format(IDENTIFIER_FORMAT,componentBuilder.getComponentClass().getSimpleName(),RandomHelper.getAlphabetic(3)));
		outputProperties.setIdentifierAsStyleClass(outputProperties.getIdentifier().toString());
		outputProperties.setWidgetVar(outputProperties.getIdentifier());
		outputProperties.setRendered(Boolean.TRUE);						
		
		if(componentBuilder instanceof VisibleComponentBuilder<?>) {
			VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>)componentBuilder;
			
			if(visibleComponentBuilder instanceof InputBuilder<?, ?>) {
				InputBuilder<?, ?> inputBuilder = (InputBuilder<?, ?>) visibleComponentBuilder;
				inputBuilder.getMessage(Boolean.TRUE);
				if(visibleComponentBuilder instanceof InputFileBuilder) {
					InputFileBuilder inputFileBuilder = (InputFileBuilder) visibleComponentBuilder;
					inputFileBuilder.addQualifiers(Primefaces.class);
				}
			}
			
			if(visibleComponentBuilder instanceof TreeBuilder) {
				TreeBuilder treeBuilder = (TreeBuilder) visibleComponentBuilder;
				treeBuilder.getDefaultNodeFamilies(Boolean.TRUE).add("default");
				treeBuilder.addQualifiers(Primefaces.class);
			}
		}
	}

	protected void listenExecuteComponentBuilderBefore(ComponentBuilder<?> componentBuilder) {
		super.listenExecuteComponentBuilderBefore(componentBuilder);
		//TODO style class string must be replaced by its equivalent role
		Component component = componentBuilder.getComponent();
		
		if(component instanceof VisibleComponent) {
			VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>)componentBuilder;
			VisibleComponent visibleComponent = (VisibleComponent) component;
			//visibleComponentBuilder.getStyle(Boolean.TRUE).addClasses("cyk_component");
			
			//String identifierAsStyleClass = RandomHelper.
			//visibleComponentBuilder.getStyle(Boolean.TRUE).addClasses("cyk_component");
			
			//String identifierAsStyleClass = visibleComponentBuilder.getIdentifier().toString();
			String identifierAsStyleClass = (String) visibleComponentBuilder.getOutputProperty(Properties.IDENTIFIER_AS_STYLE_CLASS);//.getIdentifierAsStyleClass().toString();
			//visibleComponentBuilder.getOutputProperties().setIdentifierAsStyleClass(identifierAsStyleClass);
			//System.out.println("CID : "+identifierAsStyleClass);
			visibleComponentBuilder.addStyleClasses(identifierAsStyleClass);
			//visibleComponentBuilder.addStyleClasses("AZERTY");
			/*
			ComponentRoles roles = visibleComponentBuilder.getRoles();
			if(CollectionHelper.isNotEmpty(roles)) {
				for(ComponentRole index : roles.get()) {
					String styleClass = __inject__(ComponentRoleStyleClassGetter.class).setRole(index).execute().getOutput();
					if(StringHelper.isNotBlank(styleClass))
						visibleComponentBuilder.addStyleClasses(styleClass);
				}
			}
			*/
			/*
			if(CollectionHelper.contains(visibleComponentBuilder.getRoles(), ComponentRole.TITLE))
				visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_view_title");
			if(CollectionHelper.contains(visibleComponentBuilder.getRoles(), ComponentRole.GRID))
				visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_table");
			if(CollectionHelper.contains(visibleComponentBuilder.getRoles(), ComponentRole.ROW))
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
				layoutBuilder.getStyle(Boolean.TRUE).getClasses(Boolean.TRUE).add("cyk_layout");
				
				// TODO ui-g must be get from function
				layoutBuilder.getStyle(Boolean.TRUE).getClasses(Boolean.TRUE).add("ui-g");
				
				if(CollectionHelper.contains(layoutBuilder.getRoles(), ComponentRole.GRID))
					layoutBuilder.addStyleClasses("cyk_layout_grid");
				
				layoutBuilder.setOutputProperty(Properties.LAYOUT,"block");//.setLayout("block");
			}
			
			if(visibleComponentBuilder instanceof LayoutItemBuilder) {
				LayoutItemBuilder layoutItemBuilder = (LayoutItemBuilder) visibleComponentBuilder;
				layoutItemBuilder.getStyle(Boolean.TRUE).getClasses(Boolean.TRUE).add("cyk_layout_item");
				
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void listenExecuteComponentBuilderAfter(ComponentBuilder<?> componentBuilder) {
		super.listenExecuteComponentBuilderAfter(componentBuilder);
		Component component = componentBuilder.getComponent();
		
		Events events = component.getEvents();
		if(events!=null) {
			for(Event index : events.get()) {
				index.getProperties().setManyIfNull(new Object[] {Properties.IMMEDIATE,Properties.ASYNC,Properties.DISABLED,Properties.PARTIAL_SUBMIT
						,Properties.RESET_VALUES,Properties.IGNORE_AUTO_UPDATE}, Boolean.FALSE);
				index.getProperties().setManyIfNull(new Object[] {Properties.GLOBAL,Properties.SKIP_CHILDREN}, Boolean.TRUE);
				index.getProperties().setIfNull(Properties.EVENT,StringUtils.remove(index.getName().name().toLowerCase(), "_") );
				
				index.getProperties().setIfNull(Properties.FUNCTION,index.getFunction());
				
				String scriptCodeSource = index.getScript() == null ? null :index.getScript().getCodeSource();
				if(StringHelper.isNotBlank(scriptCodeSource)) {
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
					((Commandable)visibleComponent).setProperty(Properties.ICON, IconHelper.getIdentifier(icon));
			}
			
			if(component instanceof InputOutput<?>) {
				InputOutput<?> inputOutput = (InputOutput<?>) component;
				inputOutput.setPropertyValue(inputOutput.getValue());
				
				if(inputOutput instanceof Output<?>) {
					if(inputOutput instanceof OutputString) {
						OutputString outputString = (OutputString) inputOutput;
						
						if(outputString instanceof OutputStringLabel) {
							OutputStringLabel outputStringLabel = (OutputStringLabel) outputString;
							outputStringLabel.getProperties().setFor(((OutputStringLabelBuilder)componentBuilder).getInputBuilder().getOutputProperties().getIdentifier() );	
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
						ChoicesLayout choicesLayout = ((InputChoice<?>)component).getChoicesLayout();
						component.getProperties().setConverter(DependencyInjection.inject(ObjectConverter.class));
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
								if(choicesLayout == null)
									choicesLayout = DependencyInjection.inject(ChoicesLayoutResponsive.class).setNumberOfColumns(3);
								if(choicesLayout instanceof ChoicesLayoutResponsive) {
									component.getProperties().setLayout("responsive");
									component.getProperties().setColumns( ValueHelper.defaultToIfNull(((ChoicesLayoutResponsive)choicesLayout).getNumberOfColumns(),3));
								}else {
									component.getProperties().setLayout("responsive");
									component.getProperties().setColumns(3);
								}
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
						inputDate.getProperties().setConverter(DependencyInjection.inject(DateConverter.class));
						inputDate.getProperties().setLocale(Locale.FRENCH);
						inputDate.getProperties().setTimeZone(TimeZone.getDefault());
					}else if(component instanceof InputDateTime) {
						InputDateTime inputDateTime = (InputDateTime) inputOutput;
						inputDateTime.getProperties().setPattern("d/M/y H:m");
						inputDateTime.getProperties().setConverter(DependencyInjection.inject(DateConverter.class));
						inputDateTime.getProperties().setLocale(Locale.FRENCH);
						inputDateTime.getProperties().setTimeZone(TimeZone.getDefault());
					}else if(component instanceof InputNumber) {
						InputNumber inputNumber = (InputNumber) inputOutput;
						Class<? extends Number> numberClass = (Class<? extends Number>)FieldHelper.getType(inputNumber.getField(),null);
						inputNumber.getProperties().setConverter(new NumberConverter(numberClass));
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
				dialog.setIdentifier(DependencyInjection.inject(ComponentHelper.class).getGlobalMessagesTargetDialogComponentIdentifier());
				
				
				dialog.getProperties().setResponsive(Boolean.TRUE);
				dialog.getProperties().setHeader("Messages");
				//dialog.getProperties().setHeader(DependencyInjection.inject(Application.class).getName());
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
						String clientId = DependencyInjection.inject(ComponentHelper.class).getGlobalMessagesOwnerDialogComponentClientIdentifier();
						return FacesContext.getCurrentInstance().getMessages(clientId).hasNext();
					}
				});
			}else if(component instanceof File) {
				File file = (File) component;
				org.cyk.utility.__kernel__.file.File __file__ = file.getValue();
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
	
	
}
