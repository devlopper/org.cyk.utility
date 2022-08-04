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
import org.cyk.utility.__kernel__.enumeration.Action;
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
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.OutcomeGetter;
import org.cyk.utility.client.controller.web.jsf.primefaces.dialog.DialogOpener;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction.Listener.OpenViewInDialogArgumentsGetter;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax.Ajax;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractCollection;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.OutputPanel;
import org.omnifaces.util.Faces;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractAction extends AbstractObjectAjaxable implements Serializable {

	protected String process,update;
	protected Boolean global,immediate;
	
	protected UserInterfaceAction userInterfaceAction;
	protected Runner.Arguments runnerArguments;
	protected Object __argument__;
	protected Action __action__;
	protected Class<?> __actionOnClass__;
	protected String __outcome__;
	protected Map<String,List<String>> __parameters__;
	protected Boolean __parametersSessionable__;
	protected String __actionArgumentIdentifierParameterName__;
	protected Boolean __isWindowContainerRenderedAsDialog__;
	protected Boolean __loggableAsInfo__;
	protected Boolean __runnable__;
	protected AbstractCollection __collection__;
	protected Dialog __dialog__;
	
	public Object readArgument() {
		Object argument = __argument__;
		if(argument == null && __collection__ != null && "single".equals(__collection__.getSelectionMode()) && !(__collection__.getSelection() instanceof Collection))
			argument = __collection__.getSelection();
		return argument;
	}
	
	public Object act(Object argument) {
		this.__argument__ = argument;
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).act(this);
	}

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
	
	public AbstractAction addUpdatablesUsingStyleClass(Collection<AbstractObject> updatables) {
		if(CollectionHelper.isEmpty(updatables))
			return this;
		Collection<String> updates = updatables.stream().map(x -> "@(."+x.getStyleClassAsIdentifier()+")").collect(Collectors.toSet());
		if(CollectionHelper.isEmpty(updates))
			return this;
		return addUpdates(updates);
	}
	
	public AbstractAction addUpdatablesUsingStyleClass(AbstractObject...updatables) {
		if(ArrayHelper.isEmpty(updatables))
			return this;
		return addUpdatablesUsingStyleClass(CollectionHelper.listOf(updatables));
	}
	
	/**/
	
	public static final String FIELD_PROCESS = "process";
	public static final String FIELD_IMMEDIATE = "immediate";
	public static final String FIELD_UPDATE = "update";
	public static final String FIELD_GLOBAL = "global";
	public static final String FIELD_USER_INTERFACE_ACTION = "userInterfaceAction";
	public static final String FIELD_RUNNER_ARGUMENTS = "runnerArguments";
	public static final String FIELD_RUNNER_ARGUMENTS_ACTION_ON_CLASS = "runnerArguments.actionOnClass";
	public static final String FIELD___ARGUMENT__ = "__argument__";
	public static final String FIELD___OUTCOME__ = "__outcome__";
	public static final String FIELD___PARAMETERS__ = "__parameters__";
	public static final String FIELD___ACTION_ARGUMENT_IDENTIFIER_PARAMETER_NAME__ = "__actionArgumentIdentifierParameterName__";
	public static final String FIELD___ACTION__ = "__action__";
	public static final String FIELD___ACTION_ON_CLASS__ = "__actionOnClass__";
	public static final String FIELD___IS_WINDOW_CONTAINER_RENDERED_AS_DIALOG__ = "__isWindowContainerRenderedAsDialog__";
	public static final String FIELD___RUNNABLE__ = "__runnable__";
	public static final String FIELD___COLLECTION__ = "__collection__";
	public static final String FIELD___DIALOG__ = "__dialog__";
	public static final String FIELD___LOGGABLE_AS_INFO__ = "__loggableAsInfo__";
	
	/**/
	
	public static interface Listener {
		
		Object act(AbstractAction action);
		
		Dialog getDialog();
		Listener setDialog(Dialog dialog);
		
		AbstractCollection getCollection();
		Listener setCollection(AbstractCollection collection);
		
		Integer getMinimumSelectionSize();
		Listener setMinimumSelectionSize(Integer minimumSelectionSize);
		
		Boolean getIsSelectionShowable();
		Listener setIsSelectionShowable(Boolean isSelectionShowable);
		
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
			protected AbstractCollection collection;
			protected Integer minimumSelectionSize,maximumSelectionSize;
			protected Boolean isSelectionShowable;
			protected Dialog dialog;
			protected OutputPanel dialogOutputPanel;
			protected Object commandIdentifier;
			protected Boolean isWindowContainerRenderedAsDialog;
			
			@Override
			public Object act(AbstractAction action) {
				if(Boolean.TRUE.equals(action.__runnable__)) {
					if(action.runnerArguments == null) {
						MessageRenderer.getInstance().render("No runner arguments found.",Severity.ERROR);
						return null;
					}
					Runnable runnable = instantiateRunnable(action);
					if(runnable == null) {
						MessageRenderer.getInstance().render("No runnable to run.",Severity.ERROR);
						return null;
					}
					action.runnerArguments.setRunnables(null);//free previous one
					action.runnerArguments.addRunnables(runnable);
					Runner.getInstance().run(action.runnerArguments);
				}				
				return getReturn(action);
			}
			
			protected Runnable instantiateRunnable(AbstractAction action) {
				return new Runnable() {			
					@Override
					public void run() {
						AbstractImpl.this.run(action);
					}
				};
			}
			
			protected void run(AbstractAction action) {
				//LogHelper.logInfo(String.format("User interface action=%s", action.userInterfaceAction), getClass());
				if(action.userInterfaceAction == null)
					throw new RuntimeException("One of the following user interface action must be defined : "+Arrays.toString(UserInterfaceAction.values()));
				if(UserInterfaceAction.SHOW_DIALOG.equals(action.userInterfaceAction))
					runShowDialog(action);
				else if(UserInterfaceAction.NAVIGATE_TO_VIEW.equals(action.userInterfaceAction))
					runNavigateToView(action);
				else if(UserInterfaceAction.EXECUTE_FUNCTION.equals(action.userInterfaceAction))
					runExecuteFunction(action);
				else if(UserInterfaceAction.OPEN_VIEW_IN_DIALOG.equals(action.userInterfaceAction))
					runOpenViewInDialog(action);
				else if(UserInterfaceAction.RETURN_FROM_VIEW_IN_DIALOG.equals(action.userInterfaceAction))
					runReturnFromViewInDialog(action);
				else
					throw new RuntimeException("user interface action "+action.userInterfaceAction+" not yet handled.");
			}
			
			protected void runShowDialog(AbstractAction action) {
				if(Boolean.TRUE.equals(__isDialogShowable__())) {
					runShowDialogWhenShowable(action);
				}else {
					MessageRenderer.getInstance().render(__getDialogNotShowableMessageSummary__(), Severity.WARNING, RenderType.GROWL);
				}
			}
			
			protected void runShowDialogWhenShowable(AbstractAction action) {
				String widgetVar = getDialogWidgetVar(action);
				if(StringHelper.isBlank(widgetVar))
					throw new RuntimeException("Dialog widget var is required");
				if(collection != null) {
					collection.setSelectedCommandIdentifier(commandIdentifier);
					collection.setIsSelectionShowableInDialog(isSelectionShowable);			
				}
				org.omnifaces.util.Ajax.oncomplete("PF('"+widgetVar+"').show();");
			}
			
			protected String getDialogWidgetVar(AbstractAction action) {
				String widgetVar = null;
				Dialog dialog = action.__dialog__;
				if(dialog == null && action.__collection__ != null)
					dialog = action.__collection__.getDialog();
				if(dialog != null)
					widgetVar = dialog.getWidgetVar();
				return widgetVar;
			}
			
			protected void runExecuteFunction(AbstractAction action) {
				logAsInfo(action, "executing function starts");
				Object object = __runExecuteFunction__(action);
				if(Boolean.TRUE.equals(action.get__isWindowContainerRenderedAsDialog__())) {
					PrimeFaces.current().dialog().closeDynamic(object);
					logAsInfo(action, "dynamic dialog has been closed with object "+object);
				}else {
					if(object instanceof String) {
						MessageRenderer.getInstance().render((String)object,RenderType.GROWL);
					}
						
				}
				logAsInfo(action, "function executed");
			}
			
			protected Object __runExecuteFunction__(AbstractAction action) {
				return null;
			}
			
			protected void runOpenViewInDialog(AbstractAction action) {
				String outcome = getOutcome(action);
				if(StringHelper.isBlank(outcome))
					throw new RuntimeException("Outcome is required to open view in dialog");
				Map<String,List<String>> parameters = getViewParameters(action);
				Map<String,Object> options = getDialogOptions(action);
				DialogOpener.getInstance().open(outcome, parameters, options);
			}
			
			protected String getOutcome(AbstractAction action) {
				if(StringHelper.isNotBlank(action.__outcome__))
					return action.__outcome__;
				return OutcomeGetter.getInstance().get(action.__actionOnClass__, action.__action__);									
			}
			
			protected Map<String,List<String>> getViewParameters(AbstractAction action) {
				Map<String,List<String>> parameters = null;
				if(MapHelper.isNotEmpty(action.__parameters__)) {
					if(parameters == null)
						parameters = new HashMap<>();
					parameters.putAll(action.__parameters__);
				}
				if(action.__action__ != null) {
					if(parameters == null)
						parameters = new HashMap<>();
					MapHelper.writeByKey(parameters,ParameterName.ACTION_IDENTIFIER.getValue(),List.of(action.__action__.toString()),Boolean.FALSE);
				}
				
				Object argument = action.readArgument();
				
				if(StringHelper.isNotBlank(action.__actionArgumentIdentifierParameterName__)) {					
					if(argument != null) {
						Object identifier = FieldHelper.readSystemIdentifier(argument);
						if(identifier != null) {
							String identifierAsString = identifier.toString();
							if(StringHelper.isNotBlank(identifierAsString)) {
								if(parameters == null)
									parameters = new HashMap<>();
								MapHelper.writeByKey(parameters,action.__actionArgumentIdentifierParameterName__,List.of(identifierAsString),Boolean.FALSE);
							}
						}
					}
				}
								
				/*
				if(Boolean.TRUE.equals(getIsCollectionable())) {
					if(parameters == null)
						parameters = new HashMap<>();
					MapHelper.writeByKey(parameters,ParameterName.MULTIPLE.getValue(),List.of(Boolean.TRUE.toString()),Boolean.FALSE);
				}
				*/
				if(Boolean.TRUE.equals(action.__parametersSessionable__)) {
					/*Collection<Object> sessionCollectionSelection = null;
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
					}	*/			
				}else {
					Object identifier = FieldHelper.readSystemIdentifier(argument);
					if(StringHelper.isNotBlank(action.__actionArgumentIdentifierParameterName__)) {	
						if(identifier != null) {
							String identifierAsString = identifier.toString();
							if(StringHelper.isBlank(identifierAsString))
								return parameters;
							if(parameters == null)
								parameters = new HashMap<>();
							//if(MapHelper.isNotEmpty(__parameters__))
							//	parameters.putAll(__parameters__);
							MapHelper.writeByKey(parameters,action.__actionArgumentIdentifierParameterName__,List.of(identifierAsString),Boolean.FALSE);
						}
					}				
				}
				return parameters;
			}
			
			protected Map<String,Object> getDialogOptions(AbstractAction action) {
				return null;
			}
			
			protected Object getReturn(AbstractAction action) {
				return null;
			}
			
			protected void runNavigateToView(AbstractAction action) {
				String outcome = getOutcome(action);
				if(StringHelper.isBlank(outcome))
					throw new RuntimeException("View outcome is required in order to navigate to");
				UniformResourceIdentifierAsFunctionParameter parameter = new UniformResourceIdentifierAsFunctionParameter();
				parameter.getPath(Boolean.TRUE).setIdentifier(outcome);
				Map<String,List<String>> parameters = getViewParameters(action);
				if(MapHelper.isNotEmpty(parameters))
					parameter.getQuery(Boolean.TRUE).setValue(parameters.entrySet().stream().map(entry -> entry.getKey()+"="+entry.getValue().get(0))
							.collect(Collectors.joining("&")));
				String url = UniformResourceIdentifierHelper.build(parameter);
				if(StringHelper.isBlank(url))
					throw new RuntimeException("Uniform resource identifier is required in order to navigate to");
				Faces.redirect(url);
			}
			
			protected void runReturnFromViewInDialog(AbstractAction action) {
				/*AbstractCollection collection = getCollection();
				if(collection != null) {
					PrimefacesHelper.updateOnComplete(":form:"+collection.getIdentifier());
				}*/
				//Notifications
				Object argument = action.readArgument();
				MessageRenderer.getInstance().clear();
				MessageRenderer.getInstance().render("Opération bien éffectuée",RenderType.GROWL);				
				if(argument instanceof SelectEvent) {
					argument = ((SelectEvent)argument).getObject();
					if(argument instanceof String) {
						String message = (String) argument;
						if(StringHelper.isNotBlank(message)) {
							MessageRenderer.getInstance().render(message,RenderType.GROWL);
						}
					}
				}
			}
			
			protected Boolean __isDialogShowable__() {
				if(minimumSelectionSize == null && maximumSelectionSize == null)
					return Boolean.TRUE;
				if(minimumSelectionSize != null && collection != null)
					return CollectionHelper.getSize(collection.getSelectionAsCollection()) >= minimumSelectionSize;
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
			
			protected void logAsInfo(AbstractAction action,String message) {
				if(Boolean.TRUE.equals(action.__loggableAsInfo__))
					LogHelper.logInfo(message, getClass());
			}
			
			/**/
			
			public static class DefaultImpl extends AbstractImpl implements Serializable {	
				public static final Listener INSTANCE = new DefaultImpl();
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
			Collection<RenderType> messagesRenderTypes = (Collection<RenderType>) MapHelper.readByKey(arguments, FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES);
			if(action.__actionOnClass__ == null && action.__collection__ != null)
				action.__actionOnClass__ = action.__collection__.getElementClass();
			
			if(action.__collection__ != null) {
				//collection
				if(UserInterfaceAction.OPEN_VIEW_IN_DIALOG.equals(action.userInterfaceAction) || UserInterfaceAction.EXECUTE_FUNCTION.equals(action.userInterfaceAction)) {
					//action.addUpdates(":form:"+action.__collection__.getIdentifier());
				}else {
					//action does not impact collection so no need to notify success
					if(CollectionHelper.isEmpty(messagesRenderTypes) && action.runnerArguments != null)
						action.runnerArguments.setSuccessMessageArguments(null);
				}
				//collection dialog
				if(action.__collection__.getDialogOutputPanel() == null) {
					
				}else {
					action.addUpdates(":form:"+action.__collection__.getDialogOutputPanel().getIdentifier());						
				}
			}
			
			if(action.__runnable__ == null)
				action.__runnable__ = Boolean.TRUE;
			if(action.__isWindowContainerRenderedAsDialog__ == null)
				action.__isWindowContainerRenderedAsDialog__ = ValueHelper.convertToBoolean("windowrendertypedialog".equalsIgnoreCase(WebController.getInstance()
						.getRequestParameter("windowrendertype")));
			
			if(action.runnerArguments == null)
				action.runnerArguments = new Runner.Arguments().assignDefaultMessageArguments();
			if(action.runnerArguments.getAction() == null)
				action.runnerArguments.setAction(action.__action__);
			if(action.userInterfaceAction == null && MapHelper.readByKey(arguments, FIELD_OBJECT) != null && MapHelper.readByKey(arguments, FIELD_METHOD_NAME) != null)			
				action.userInterfaceAction = UserInterfaceAction.EXECUTE_FUNCTION;
			
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE)))
				action.runnerArguments.setSuccessMessageArguments(null);
			if(action.runnerArguments.getSuccessMessageArguments() != null) {
				Collection<RenderType> renderTypes = (Collection<RenderType>) MapHelper.readByKey(arguments, FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES);
				if(renderTypes != null) {
					action.runnerArguments.getSuccessMessageArguments().setRenderTypes(renderTypes);
				}
			}
			
			if(UserInterfaceAction.OPEN_VIEW_IN_DIALOG.equals(action.userInterfaceAction)) {
				//register return event
				if(action.getAjaxes() == null || action.getAjaxes().get("dialogReturn") == null) {
					action.getAjaxes(Boolean.TRUE).put("dialogReturn", Ajax.build(Ajax.FIELD_EVENT,"dialogReturn",Ajax.FIELD_DISABLED,Boolean.FALSE
							,Ajax.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.RETURN_FROM_VIEW_IN_DIALOG));
					if(action.__collection__ != null) {
						action.getAjaxes(Boolean.TRUE).get("dialogReturn").addUpdates(":form:"+action.__collection__.getIdentifier());
					}
				}
			}else if(UserInterfaceAction.EXECUTE_FUNCTION.equals(action.userInterfaceAction)) {
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
								public Object __runExecuteFunction__(AbstractAction action) {
									Collection<AbstractInput<?>> inputs = (Collection<AbstractInput<?>>) MapHelper.readByKey(arguments, FIELD_INPUTS);	
									if(CollectionHelper.isNotEmpty(inputs))
										for(AbstractInput<?> input : inputs)
											input.writeValueToObjectField();
									try {
										return method.invoke(object);
									} catch (Exception exception) {
										Throwable cause = ThrowableHelper.getInstanceOf(exception, RuntimeException.class);
										if(cause == null)
											cause = exception;
										java.lang.RuntimeException runtimeException = null;
										if(cause instanceof RuntimeException)
											runtimeException = new java.lang.RuntimeException(((RuntimeException)cause).computeMessage());
										else
											runtimeException = new java.lang.RuntimeException(exception);
										throw runtimeException;
									}
								}
							};							
						}						
					}
				}
				if(action.__collection__ != null) {
					action.addUpdates(":form:"+action.__collection__.getIdentifier());
				}
			}else if(UserInterfaceAction.SHOW_DIALOG.equals(action.userInterfaceAction)) {
				if(action.__dialog__ != null) {
					action.addUpdates(":form:"+action.__dialog__.get__containerIdentifier__());
				}
			}
			/*		
			if(action.listener == null) {				
				Dialog dialog = (Dialog) MapHelper.readByKey(arguments, FIELD_DIALOG);
				if(dialog != null) {
					action.setUserInterfaceAction(UserInterfaceAction.SHOW_DIALOG);
					action.listener = new AbstractAction.Listener.AbstractImpl() {}.setDialog(dialog);
				}				
			}
			*/
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
				//if(listener.getIsWindowContainerRenderedAsDialog() == null)
				//	listener.setIsWindowContainerRenderedAsDialog((Boolean) MapHelper.readByKey(arguments, FIELD_LISTENER_IS_WINDOW_RENDERED_AS_DIALOG));
				//if(listener.getIsWindowContainerRenderedAsDialog() == null)
				//	listener.setIsWindowContainerRenderedAsDialog(ValueHelper.convertToBoolean("windowrendertypedialog".equalsIgnoreCase(Faces.getRequestParameter("windowrendertype"))));
				
				if(listener.getMinimumSelectionSize() == null) {
					listener.setMinimumSelectionSize(0);
				}
				
				if(listener.getIsSelectionShowable() == null)
					listener.setIsSelectionShowable(listener.getMinimumSelectionSize() > 0);
				
				if(action.userInterfaceAction == null && listener.getCollection() != null)
					action.userInterfaceAction = UserInterfaceAction.SHOW_DIALOG;
				
				if(listener.getDialog() == null && listener.getCollection() != null)
					listener.setDialog(listener.getCollection().getDialog());
				
				if(listener.getDialogOutputPanel() == null && listener.getCollection() != null)
					listener.setDialogOutputPanel(listener.getCollection().getDialogOutputPanel());
				
				if(UserInterfaceAction.SHOW_DIALOG.equals(action.userInterfaceAction) && listener.getDialogOutputPanel() != null)
					action.addUpdates(":form:"+listener.getDialogOutputPanel().getIdentifier());				
			}
			
			if(UserInterfaceAction.EXECUTE_FUNCTION.equals(action.userInterfaceAction)) {
				if(Boolean.TRUE.equals(action.__isWindowContainerRenderedAsDialog__))
					action.runnerArguments.setSuccessMessageArguments(null);
			}else {
				action.runnerArguments.setSuccessMessageArguments(null);
			}
		}
		
		/**/
		
		@SuppressWarnings("unchecked") @Deprecated
		private void __configureOpenViewInDialog__(AbstractAction action, Map<Object, Object> arguments) {
			LogHelper.logInfo("configure open view in dialog", getClass());
			AbstractCollection collection = null;//(AbstractCollection) MapHelper.readByKey(arguments, FIELD_COLLECTION);
			OpenViewInDialogArgumentsGetter openViewInDialogArgumentsGetter = (OpenViewInDialogArgumentsGetter) MapHelper.readByKey(arguments, FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER);
			if(openViewInDialogArgumentsGetter == null) {
				String __outcome__ = (String) MapHelper.readByKey(arguments, FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_OUTCOME);
				if(StringHelper.isBlank(__outcome__)) {				
					if(action.runnerArguments.getAction() != null) {
						Class<?> actionOnClass = null;//(Class<?>) MapHelper.readByKey(arguments, FIELD_ACTION_ON_CLASS);	
						if(actionOnClass == null && collection != null)
							actionOnClass = collection.getElementClass();
						if(actionOnClass != null) {
							__outcome__ = OutcomeGetter.getInstance().get(actionOnClass, action.runnerArguments.getAction());
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
								if(collection != null && CollectionHelper.isNotEmpty(collection.getSelectionAsCollection())) {
									if(sessionCollectionSelection == null)
										sessionCollectionSelection = new ArrayList<>();
									for(Object object : collection.getSelectionAsCollection())
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
					openViewInDialogArgumentsGetter.setAction(action.runnerArguments.getAction()).setCollection(collection)
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
			/*
			action.userInterfaceAction = UserInterfaceAction.OPEN_VIEW_IN_DIALOG;
			action.listener = new AbstractAction.Listener.AbstractImpl() {
				@Override
				public void run(AbstractAction action,Object argument) {
					__openViewInDialog__(argument);
				}
			}.setOpenViewInDialogArgumentsGetter(openViewInDialogArgumentsGetter).setCollection(collection);
			*/

			//register return event
			/*
			if(action.getAjaxes() == null || action.getAjaxes().get("dialogReturn") == null) {
				action.userInterfaceAction = UserInterfaceAction.RETURN_FROM_VIEW_IN_DIALOG;
				Ajax ajax = Ajax.build(Ajax.FIELD_EVENT,"dialogReturn",Ajax.FIELD_DISABLED,Boolean.FALSE);
				ajax.setListener(new AbstractAction.Listener.AbstractImpl() {
					
				}.setCollection(((Listener)action.listener).getCollection()));
				action.getAjaxes(Boolean.TRUE).put("dialogReturn", ajax);
			}
			*/
			LogHelper.logInfo("Dialog return ajax register", getClass());
			
		}

		@SuppressWarnings("unchecked")
		private void __configureExecuteFunction__(AbstractAction action, Map<Object, Object> arguments) {
			
		}
		
		/**/
		//public static final String FIELD_ACTION = "action";
		//public static final String FIELD_RUNNER_ARGUMENTS_ACTION = "runner.arguments.action";
		//public static final String FIELD_LISTENER_IS_WINDOW_RENDERED_AS_DIALOG = "listenerIsWindowRenderedAsDialog";
		
		@Deprecated public static final String FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER = "openViewInDialogArgumentsGetter";
		@Deprecated public static final String FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_OUTCOME = "openViewInDialogArgumentsGetterOutcome";
		@Deprecated public static final String FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_PARAMETERS = "openViewInDialogArgumentsGetterParameters";
		
		public static final String FIELD_ARGUMENT_SYSTEM_IDENTIFIER_FIELD_NAME = "argumentSystemIdentifierFieldName";
		public static final String FIELD_INPUTS = "inputs";
		//public static final String FIELD_DIALOG = "dialog";
		public static final String FIELD_COLLECTIONABLE = "collectionable";
		//public static final String FIELD_COLLECTION = "collection";
		public static final String FIELD_COLLECTION_UPDATABLE = "collectionUpdatable";
		public static final String FIELD_COLLECTION_SELECTION_SESSIONABLE = "collectionSelectionSessionable";
		public static final String FIELD_COLLECTION_SELECTION_SESSION_IDENTIFIER = "collectionSelectionSessionIdentifier";
		//public static final String FIELD_CLASS = "class";
		public static final String FIELD_OBJECT = "object";
		public static final String FIELD_METHOD_NAME = "methodName";
		public static final String FIELD_UPDATABLES = "updatables";
		
		public static final String FIELD_BLOCK_UI = "BLOCK_UI";
		public static final String FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE = "RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE";
		public static final String FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES = "RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES";
	}

	/**/
	
	public static interface ShowDialogExecutor {
		
	}
	
	public static interface OpenDialogExecutor {
		
	}
	
	public static interface ExecuteFunctionExecutor {
		
	}
}