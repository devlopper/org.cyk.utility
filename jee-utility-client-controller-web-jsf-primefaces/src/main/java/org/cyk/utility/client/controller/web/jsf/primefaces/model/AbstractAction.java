package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.identifier.resource.ParameterName;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.runnable.Runner;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.OutcomeGetter;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.dialog.DialogOpener;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction.Listener.Action;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction.Listener.OpenViewInDialogArgumentsGetter;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractCollection;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;
import org.omnifaces.util.Faces;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractAction extends AbstractObjectAjaxable implements Serializable {

	protected String process,update;
	protected Boolean global;
	protected Runner.Arguments runnerArguments;
	protected String __actionArgumentIdentifierParameterName__;
	
	public void action(Object argument) {
		if(runnerArguments == null) {
			MessageRenderer.getInstance().render("No runner arguments found.",Severity.WARNING);
		}else {			
			runnerArguments.setRunnables(null);//free previous one
			runnerArguments.addRunnables(new Runnable() {			
				@Override
				public void run() {
					__action__(argument);
					if(listener instanceof AbstractAction.Listener)
						((AbstractAction.Listener)listener).listenAction(argument);
				}
			});
			Runner.getInstance().run(runnerArguments);	
		}
	}
	
	protected void __action__(Object argument) {}
	
	public AbstractAction addUpdates(Collection<String> updates) {
		if(CollectionHelper.isEmpty(updates))
			return this;
		Collection<String> __updates__ = CollectionHelper.setOf(StringUtils.split(update,","));
		if(__updates__ == null)
			__updates__ = new LinkedHashSet<>();
		__updates__.addAll(updates);
		update = StringHelper.concatenate(__updates__, ",");
		return this;
	}
	
	public AbstractAction addUpdates(String...updates) {
		if(ArrayHelper.isEmpty(updates))
			return this;
		return addUpdates(CollectionHelper.listOf(updates));
	}
	
	public AbstractAction addUpdatables(Collection<AbstractObject> updatables) {
		if(CollectionHelper.isEmpty(updatables))
			return this;
		Collection<String> updates = updatables.stream().map(AbstractObject::getIdentifier).collect(Collectors.toSet());
		if(CollectionHelper.isEmpty(updates))
			return this;
		return addUpdates(updates);
	}
	
	public AbstractAction addUpdatables(AbstractObject...updatables) {
		if(ArrayHelper.isEmpty(updatables))
			return this;
		return addUpdatables(CollectionHelper.listOf(updatables));
	}
	
	/**/
	
	public static final String FIELD_PROCESS = "process";
	public static final String FIELD_UPDATE = "update";
	public static final String FIELD_RUNNER_ARGUMENTS = "runnerArguments";
	public static final String FIELD___ACTION_ARGUMENT_IDENTIFIER_PARAMETER_NAME__ = "__actionArgumentIdentifierParameterName__";
	
	/**/
	
	public static interface Listener {
		
		public static enum Action {SHOW_DIALOG,NAVIGATE_TO_VIEW,OPEN_VIEW_IN_DIALOG,RETURN_FROM_VIEW_IN_DIALOG,EXECUTE_FUNCTION}
		
		void listenAction(Object argument);
		
		AbstractCollection getCollection();
		Listener setCollection(AbstractCollection collection);
		
		Integer getMinimumSelectionSize();
		Listener setMinimumSelectionSize(Integer minimumSelectionSize);
		
		Boolean getIsSelectionShowable();
		Listener setIsSelectionShowable(Boolean isSelectionShowable);
		
		Listener setAction(Action action);
		Action getAction();
		
		org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog getDialog();
		Listener setDialog(org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog dialog);
		
		OutputPanel getDialogOutputPanel();
		Listener setDialogOutputPanel(OutputPanel dialogOutputPanel);
		
		Listener setOpenViewInDialogArgumentsGetter(OpenViewInDialogArgumentsGetter openViewInDialogArgumentsGetter);
		OpenViewInDialogArgumentsGetter getOpenViewInDialogArgumentsGetter();
		
		Listener setOutcome(String outcome);
		String getOutcome();
		
		Boolean getIsWindowContainerRenderedAsDialog();
		Listener setIsWindowContainerRenderedAsDialog(Boolean isWindowContainerRenderedAsDialog);
		
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements Listener,Serializable {			
			
			protected OpenViewInDialogArgumentsGetter openViewInDialogArgumentsGetter;
			protected String outcome;
			protected Action action;
			protected AbstractCollection collection;
			protected Integer minimumSelectionSize,maximumSelectionSize;
			protected Boolean isSelectionShowable;
			protected org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog dialog;
			protected OutputPanel dialogOutputPanel;
			protected Object commandIdentifier;
			protected Boolean isWindowContainerRenderedAsDialog;
			
			@Override
			public void listenAction(Object argument) {
				if(Action.SHOW_DIALOG.equals(action)) {
					if(Boolean.TRUE.equals(__isDialogShowable__())) {
						__showDialog__(argument);
					}else {
						MessageRenderer.getInstance().render(__getDialogNotShowableMessageSummary__(), Severity.WARNING, RenderType.GROWL);
					}
				}else if(Action.NAVIGATE_TO_VIEW.equals(action)) {
					__navigateToView__(argument);
				}else if(Action.EXECUTE_FUNCTION.equals(action)) {
					LogHelper.logInfo("execute function", getClass());
					Object object = __executeFunction__(argument);
					LogHelper.logInfo("function executed", getClass());
					if(Boolean.TRUE.equals(getIsWindowContainerRenderedAsDialog())) {
						LogHelper.logInfo("closing dialog with object "+object, getClass());
						PrimeFaces.current().dialog().closeDynamic(object);
						LogHelper.logInfo("dialog closed with object "+object, getClass());
					}
				}else if(Action.OPEN_VIEW_IN_DIALOG.equals(action)) {
					__openViewInDialog__(argument);
				}else if(Action.RETURN_FROM_VIEW_IN_DIALOG.equals(action)) {
					__returnFromViewInDialog__(argument);
				}else {
					throw new RuntimeException("One of the following action need to be defined for the listener in order to process : "+Arrays.toString(Action.values()));
				}
			}
			
			protected void __showDialog__(Object argument) {
				String widgetVar = __getDialogWidgetVar__();
				if(StringHelper.isBlank(widgetVar))
					throw new RuntimeException("Dialog widget var is required");
				if(collection != null) {
					collection.setSelectedCommandIdentifier(commandIdentifier);
					collection.setIsSelectionShowableInDialog(isSelectionShowable);			
				}
				org.omnifaces.util.Ajax.oncomplete("PF('"+widgetVar+"').show();");		
			}
			
			protected String __getDialogWidgetVar__() {
				if(dialog!=null && StringHelper.isNotBlank(dialog.widgetVar))
					return dialog.widgetVar;
				if(collection != null)
					return collection.getDialog().getWidgetVar();
				return null;
			}
			
			protected void __navigateToView__(Object argument) {
				String __outcome__ = __getOutcome__(argument,outcome);
				if(StringHelper.isBlank(__outcome__))
					throw new RuntimeException("View outcome is required in order to navigate to");
				UniformResourceIdentifierAsFunctionParameter parameter = new UniformResourceIdentifierAsFunctionParameter();
				parameter.getPath(Boolean.TRUE).setIdentifier(__outcome__);
				parameter.getQuery(Boolean.TRUE).setValue(ParameterName.ENTITY_IDENTIFIER.getValue()+"="+FieldHelper.readSystemIdentifier(argument));
				String url = UniformResourceIdentifierHelper.build(parameter);
				if(StringHelper.isBlank(url))
					throw new RuntimeException("Uniform resource identifier is required in order to navigate to");
				Faces.redirect(url);
			}
			
			protected void __openViewInDialog__(Object argument) {
				OpenViewInDialogArgumentsGetter openViewInDialogArgumentsGetter = getOpenViewInDialogArgumentsGetter();
				if(openViewInDialogArgumentsGetter == null)
					throw new RuntimeException("arguments getter is required to open view in dialog");
				String outcome = openViewInDialogArgumentsGetter.getOutcome(argument,getOutcome());
				if(StringHelper.isBlank(outcome))
					throw new RuntimeException("Outcome is required to open view in dialog");
				DialogOpener.getInstance().open(outcome, openViewInDialogArgumentsGetter.getParameters(argument, null), openViewInDialogArgumentsGetter.getOptions(argument, null));
			}
			
			protected void __returnFromViewInDialog__(Object argument) {
				LogHelper.logInfo("return from view in dialog with object "+argument, getClass());
				AbstractCollection collection = getCollection();
				if(collection != null) {
					PrimefacesHelper.updateOnComplete(":form:"+collection.getIdentifier());
					LogHelper.logInfo("collection updated", getClass());
				}
				
				//Notifications
				MessageRenderer.getInstance().clear();
				MessageRenderer.getInstance().render("Opération bien éffectuée",RenderType.GROWL);
				if(argument instanceof SelectEvent) {
					argument = ((SelectEvent)argument).getObject();
					if(argument instanceof String) {
						String message = (String) argument;
						if(StringHelper.isNotBlank(message)) {
							MessageRenderer.getInstance().render(message,RenderType.GROWL);
							LogHelper.logInfo("message rendered", getClass());
						}
					}
				}
				LogHelper.logInfo("returned from view in dialog with object "+argument, getClass());
			}
			
			protected Object __executeFunction__(Object argument) {
				return null;
			}
			
			protected Boolean __isDialogShowable__() {
				if(minimumSelectionSize == null && maximumSelectionSize == null)
					return Boolean.TRUE;
				if(minimumSelectionSize != null && collection != null)
					return CollectionHelper.getSize(collection.getSelection()) >= minimumSelectionSize;
				return Boolean.TRUE;
			}
			
			protected String __getDialogNotShowableMessageSummary__() {
				if(minimumSelectionSize != null)
					return "Sélectionner au moins "+minimumSelectionSize;
				return "Impossible d'ouvrir la boite de dialogue";
			}
			
			/**/
			
			protected String __getOutcome__(Object argument,String outcome) {
				return outcome;
			}
		}
		
		public static interface OpenViewInDialogArgumentsGetter {
			
			org.cyk.utility.__kernel__.enumeration.Action getAction();
			OpenViewInDialogArgumentsGetter setAction(org.cyk.utility.__kernel__.enumeration.Action action);
			
			Boolean getIsCollectionable();
			OpenViewInDialogArgumentsGetter setIsCollectionable(Boolean isCollectionable);
			
			AbstractCollection getCollection();
			OpenViewInDialogArgumentsGetter setCollection(AbstractCollection collection);
			
			Boolean getIsCollectionSessionable();
			OpenViewInDialogArgumentsGetter setIsCollectionSessionable(Boolean isCollectionSessionable);
			
			Object getCollectionSessionIdentifier();
			OpenViewInDialogArgumentsGetter setCollectionSessionIdentifier(Object identifier);
			
			String getOutcome(Object argument,String outcome);
			Map<String,List<String>> getParameters(Object argument,Map<String,List<String>> parameters);
			Map<String,Object> getOptions(Object argument,Map<String,Object> options);
		
			@Getter @Setter @Accessors(chain=true)
			public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements OpenViewInDialogArgumentsGetter,Serializable {
				
				private org.cyk.utility.__kernel__.enumeration.Action action;
				private AbstractCollection collection;
				private Boolean isCollectionSessionable,isCollectionable;
				private Object collectionSessionIdentifier;
				
				@Override
				public String getOutcome(Object argument, String outcome) {
					return outcome;
				}
				
				@Override
				public Map<String, List<String>> getParameters(Object argument, Map<String, List<String>> parameters) {
					return parameters;
				}
				
				@Override
				public Map<String, Object> getOptions(Object argument, Map<String, Object> options) {
					return options;
				}
			}
		}
	}
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<ACTION extends AbstractAction> extends AbstractObject.AbstractConfiguratorImpl<ACTION> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(ACTION action, Map<Object, Object> arguments) {
			super.configure(action, arguments);
			AbstractCollection collection = (AbstractCollection) MapHelper.readByKey(arguments, FIELD_COLLECTION);
			
			Action listenerAction = (Action) MapHelper.readByKey(arguments, FIELD_LISTENER_ACTION);
			if(listenerAction == null) {
				if(MapHelper.readByKey(arguments, FIELD_OBJECT) != null && MapHelper.readByKey(arguments, FIELD_METHOD_NAME) != null)
					listenerAction = Action.EXECUTE_FUNCTION;
			}
			
			if(Action.OPEN_VIEW_IN_DIALOG.equals(listenerAction))
				__configureOpenViewInDialog__(action, arguments);
			else if(Action.EXECUTE_FUNCTION.equals(listenerAction))
				__configureExecuteFunction__(action, arguments);
			
			if(action.listener == null) {				
				org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog dialog = (org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog) MapHelper.readByKey(arguments, FIELD_DIALOG);
				if(dialog != null) {
					action.listener = new AbstractAction.Listener.AbstractImpl() {
						@Override
						public void listenAction(Object argument) {
							__showDialog__(argument);
						}
					}.setAction(Listener.Action.SHOW_DIALOG).setDialog(dialog);
				}				
			}
			
			if(action.runnerArguments == null)
				action.runnerArguments = new Runner.Arguments().assignDefaultMessageArguments();
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE)))
				action.getRunnerArguments().setSuccessMessageArguments(null);
			if(action.getRunnerArguments().getSuccessMessageArguments() != null) {
				Collection<RenderType> renderTypes = (Collection<RenderType>) MapHelper.readByKey(arguments, FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES);
				if(renderTypes != null) {
					action.getRunnerArguments().getSuccessMessageArguments().setRenderTypes(renderTypes);
				}
			}
			//we need to update any messages after processing
			// global
			action.addUpdates(__inject__(ComponentHelper.class).getGlobalMessagesTargetInlineComponentClientIdentifier()
					,__inject__(ComponentHelper.class).getGlobalMessagesTargetDialogComponentClientIdentifier()
					,":form:"+__inject__(ComponentHelper.class).getGlobalMessagesTargetGrowlComponentIdentifier());
			
			//we need to update other component
			Collection<AbstractObject> updatables = (Collection<AbstractObject>) MapHelper.readByKey(arguments, FIELD_UPDATABLES);
			if(CollectionHelper.isNotEmpty(updatables)) {
				action.addUpdatables(updatables);
			}
			
			BlockUI blockUI = (BlockUI) MapHelper.readByKey(arguments, FIELD_BLOCK_UI);
			if(blockUI != null) {
				if(action.global == null)
					action.global = Boolean.FALSE;
				action.getEventScripts(Boolean.TRUE).write(Event.START, "PF('"+blockUI.getWidgetVar()+"').show();");
				action.getEventScripts(Boolean.TRUE).write(Event.COMPLETE, "PF('"+blockUI.getWidgetVar()+"').hide();");
			}
			
			if(action.global == null)
				action.global = Boolean.TRUE;
	
			if(action.listener != null) {
				Listener listener = (Listener) action.listener;
				if(listener.getIsWindowContainerRenderedAsDialog() == null)
					listener.setIsWindowContainerRenderedAsDialog((Boolean) MapHelper.readByKey(arguments, FIELD_LISTENER_IS_WINDOW_RENDERED_AS_DIALOG));
				if(listener.getIsWindowContainerRenderedAsDialog() == null)
					listener.setIsWindowContainerRenderedAsDialog(ValueHelper.convertToBoolean("windowrendertypedialog".equalsIgnoreCase(Faces.getRequestParameter("windowrendertype"))));
				
				if(listener.getCollection() == null)
					listener.setCollection(collection);
				
				if(listener.getMinimumSelectionSize() == null) {
					listener.setMinimumSelectionSize(0);
				}
				
				if(listener.getIsSelectionShowable() == null)
					listener.setIsSelectionShowable(listener.getMinimumSelectionSize() > 0);
				
				if(listener.getAction() == null && listener.getCollection() != null)
					listener.setAction(Listener.Action.SHOW_DIALOG);
				
				if(listener.getDialog() == null && listener.getCollection() != null)
					listener.setDialog(listener.getCollection().getDialog());
				
				if(listener.getDialogOutputPanel() == null && listener.getCollection() != null)
					listener.setDialogOutputPanel(listener.getCollection().getDialogOutputPanel());
				
				if(Listener.Action.SHOW_DIALOG.equals(listener.getAction()) && listener.getDialogOutputPanel() != null) {
					action.addUpdates(":form:"+listener.getDialogOutputPanel().getIdentifier());
				}
				
				if(Listener.Action.EXECUTE_FUNCTION.equals(listener.getAction())) {
					if(Boolean.TRUE.equals(listener.getIsWindowContainerRenderedAsDialog()))
						action.runnerArguments.setSuccessMessageArguments(null);
				}else {
					action.runnerArguments.setSuccessMessageArguments(null);
				}
			}
						
			if(collection == null) {
				
			}else {
				//is collection updatable ?
				Boolean collectionUpdatable = (Boolean) MapHelper.readByKey(arguments, FIELD_COLLECTION_UPDATABLE);
				if(collectionUpdatable == null)
					collectionUpdatable = MapHelper.readByKey(arguments, FIELD_LISTENER) != null;
				if(Boolean.TRUE.equals(collectionUpdatable)) {
					action.addUpdatables(collection);
				}else {
					//action does not impact collection so no need to notify success
					action.runnerArguments.setSuccessMessageArguments(null);
				}
					
				if(collection.getDialogOutputPanel() == null) {
					
				}else {
					action.update += " :form:"+collection.getDialogOutputPanel().getIdentifier();						
				}
			}
		}
		
		/**/
		
		@SuppressWarnings("unchecked")
		private void __configureOpenViewInDialog__(AbstractAction action, Map<Object, Object> arguments) {
			LogHelper.logInfo("configure open view in dialog", getClass());
			AbstractCollection collection = (AbstractCollection) MapHelper.readByKey(arguments, FIELD_COLLECTION);
			OpenViewInDialogArgumentsGetter openViewInDialogArgumentsGetter = (OpenViewInDialogArgumentsGetter) MapHelper.readByKey(arguments, FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER);
			org.cyk.utility.__kernel__.enumeration.Action __action__ = (org.cyk.utility.__kernel__.enumeration.Action) MapHelper.readByKey(arguments, FIELD_ACTION);
			if(openViewInDialogArgumentsGetter == null) {
				String __outcome__ = (String) MapHelper.readByKey(arguments, FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_OUTCOME);
				if(StringHelper.isBlank(__outcome__)) {				
					if(__action__ != null) {
						Class<?> actionOnClass = (Class<?>) MapHelper.readByKey(arguments, FIELD_ACTION_ON_CLASS);	
						if(actionOnClass == null && collection != null)
							actionOnClass = collection.getElementClass();
						if(actionOnClass != null) {
							__outcome__ = OutcomeGetter.getInstance().get(actionOnClass, __action__);
						}
					}				
				}
				if(StringHelper.isNotBlank(__outcome__)) {
					final String finalOutcome = __outcome__;
					Map<String, List<String>> __parameters__ = (Map<String, List<String>>) MapHelper.readByKey(arguments, FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_PARAMETERS);
					openViewInDialogArgumentsGetter = new OpenViewInDialogArgumentsGetter.AbstractImpl() {
						@Override
						public String getOutcome(Object argument, String outcome) {
							return finalOutcome;
						}
						
						@Override
						public Map<String, List<String>> getParameters(Object argument,Map<String, List<String>> parameters) {
							if(MapHelper.isNotEmpty(__parameters__)) {
								if(parameters == null)
									parameters = new HashMap<>();
								parameters.putAll(__parameters__);
							}
							
							if(getAction()!=null) {
								if(parameters == null)
									parameters = new HashMap<>();
								MapHelper.writeByKey(parameters,ParameterName.ACTION_IDENTIFIER.getValue(),List.of(getAction().toString()),Boolean.FALSE);
							}
							
							if(Boolean.TRUE.equals(getIsCollectionable())) {
								if(parameters == null)
									parameters = new HashMap<>();
								MapHelper.writeByKey(parameters,ParameterName.MULTIPLE.getValue(),List.of(Boolean.TRUE.toString()),Boolean.FALSE);
							}
							
							if(Boolean.TRUE.equals(getIsCollectionSessionable())) {
								Collection<Object> sessionCollectionSelection = null;
								AbstractCollection collection = getCollection();
								if(collection != null && CollectionHelper.isNotEmpty(collection.getSelection())) {
									if(sessionCollectionSelection == null)
										sessionCollectionSelection = new ArrayList<>();
									for(Object object : collection.getSelection())
										sessionCollectionSelection.add(object);		
								}				
								
								if(argument != null) {
									if(sessionCollectionSelection == null)
										sessionCollectionSelection = new ArrayList<>();
									sessionCollectionSelection.add(argument);
								}
									
								if(CollectionHelper.isNotEmpty(sessionCollectionSelection)) {
									Object sessionIdentifier = getCollectionSessionIdentifier();
									if(sessionIdentifier != null) {
										SessionHelper.setAttributeValue(sessionIdentifier, sessionCollectionSelection);
										if(parameters == null)
											parameters = new HashMap<>();
										MapHelper.writeByKey(parameters,ParameterName.SESSION_IDENTIFIER.getValue(),List.of(sessionIdentifier.toString()),Boolean.FALSE);
									}
								}				
							}else {
								Object identifier = FieldHelper.readSystemIdentifier(argument);
								if(identifier != null) {
									String identifierAsString = identifier.toString();
									if(StringHelper.isBlank(identifierAsString))
										return parameters;
									if(parameters == null)
										parameters = new HashMap<>();
									if(MapHelper.isNotEmpty(__parameters__))
										parameters.putAll(__parameters__);
									MapHelper.writeByKey(parameters,action.__actionArgumentIdentifierParameterName__,List.of(identifierAsString),Boolean.FALSE);
								}
							}
							return parameters;
						}
					};
					openViewInDialogArgumentsGetter.setAction(__action__).setCollection(collection)
						.setIsCollectionSessionable((Boolean) MapHelper.readByKey(arguments, FIELD_COLLECTION_SELECTION_SESSIONABLE))
						.setCollectionSessionIdentifier(MapHelper.readByKey(arguments, FIELD_COLLECTION_SELECTION_SESSION_IDENTIFIER))
						.setIsCollectionable((Boolean) MapHelper.readByKey(arguments, FIELD_COLLECTIONABLE))
						;
					if(openViewInDialogArgumentsGetter.getIsCollectionable() == null)
						openViewInDialogArgumentsGetter.setIsCollectionable(Boolean.TRUE);
					if(Boolean.TRUE.equals(openViewInDialogArgumentsGetter.getIsCollectionSessionable()) && openViewInDialogArgumentsGetter.getCollectionSessionIdentifier() == null)
						openViewInDialogArgumentsGetter.setCollectionSessionIdentifier(RandomHelper.getAlphanumeric(10));
				}
			}
			
			action.listener = new AbstractAction.Listener.AbstractImpl() {
				@Override
				public void listenAction(Object argument) {
					__openViewInDialog__(argument);
				}
			}.setAction(Listener.Action.OPEN_VIEW_IN_DIALOG).setOpenViewInDialogArgumentsGetter(openViewInDialogArgumentsGetter).setCollection(collection);

			//register return event
			if(action.getAjaxes() == null || action.getAjaxes().get("dialogReturn") == null) {
				Ajax ajax = Ajax.build(Ajax.FIELD_EVENT,"dialogReturn",Ajax.FIELD_DISABLED,Boolean.FALSE);
				ajax.setListener(new AbstractAction.Listener.AbstractImpl() {
					
				}.setAction(Listener.Action.RETURN_FROM_VIEW_IN_DIALOG).setCollection(((Listener)action.listener).getCollection()));
				action.getAjaxes(Boolean.TRUE).put("dialogReturn", ajax);
			}
			LogHelper.logInfo("Dialog return ajax register", getClass());
		}

		@SuppressWarnings("unchecked")
		private void __configureExecuteFunction__(AbstractAction action, Map<Object, Object> arguments) {
			Object object = MapHelper.readByKey(arguments, FIELD_OBJECT);
			if(object != null) {
				String methodName = (String) MapHelper.readByKey(arguments, FIELD_METHOD_NAME);
				if(StringHelper.isNotBlank(methodName)) {
					Method method = MethodUtils.getMatchingAccessibleMethod(object.getClass(), methodName);
					if(method == null) {
						LogHelper.logWarning(String.format("Method %s.%s() does not exist",object.getClass().getName(),methodName), getClass());
					}else {
						action.listener = new AbstractAction.Listener.AbstractImpl() {
							@Override
							public Object __executeFunction__(Object argument) {
								Collection<AbstractInput<?>> inputs = (Collection<AbstractInput<?>>) MapHelper.readByKey(arguments, FIELD_INPUTS);	
								if(CollectionHelper.isNotEmpty(inputs))
									for(AbstractInput<?> input : inputs)
										input.writeValueToObjectField();
								try {
									return method.invoke(object);
								} catch (Exception exception) {
									throw new RuntimeException(exception);
								}
							}
						}.setAction(Listener.Action.EXECUTE_FUNCTION);
						//((AbstractAction.Listener)action.listener)
						//.setIsWindowContainerRenderedAsDialog((Boolean) MapHelper.readByKey(arguments, FIELD_LISTENER_IS_WINDOW_RENDERED_AS_DIALOG))
						;
					}						
				}
			}
		}
		
		/**/
		public static final String FIELD_ACTION = "action";
		public static final String FIELD_ACTION_ON_CLASS = "actionOnClass";
		public static final String FIELD_LISTENER_ACTION = "listenerAction";
		public static final String FIELD_LISTENER_IS_WINDOW_RENDERED_AS_DIALOG = "listenerIsWindowRenderedAsDialog";
		public static final String FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER = "openViewInDialogArgumentsGetter";
		public static final String FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_OUTCOME = "openViewInDialogArgumentsGetterOutcome";
		public static final String FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_PARAMETERS = "openViewInDialogArgumentsGetterParameters";
		public static final String FIELD_INPUTS = "inputs";
		public static final String FIELD_DIALOG = "dialog";
		public static final String FIELD_COLLECTIONABLE = "collectionable";
		public static final String FIELD_COLLECTION = "collection";
		public static final String FIELD_COLLECTION_UPDATABLE = "collectionUpdatable";
		public static final String FIELD_COLLECTION_SELECTION_SESSIONABLE = "collectionSelectionSessionable";
		public static final String FIELD_COLLECTION_SELECTION_SESSION_IDENTIFIER = "collectionSelectionSessionIdentifier";
		public static final String FIELD_CLASS = "class";
		public static final String FIELD_OBJECT = "object";
		public static final String FIELD_METHOD_NAME = "methodName";
		public static final String FIELD_UPDATABLES = "updatables";
		
		public static final String FIELD_LISTENER_NULLABLE = "LISTENER_NULLABLE";
		public static final String FIELD_BLOCK_UI = "BLOCK_UI";
		public static final String FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE = "RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE";
		public static final String FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES = "RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES";
	}	
}