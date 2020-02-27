package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.runnable.Runner;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.AbstractCollection;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.AbstractInput;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractAction extends AbstractObject implements Serializable {

	protected String process,update;
	protected Boolean global;
	protected Runner.Arguments runnerArguments;
	
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
	
	/**/
	
	public static interface Listener {
		
		void listenAction(Object argument);
		
		/**/
		
		public static abstract class AbstractImpl extends org.cyk.utility.__kernel__.object.AbstractObject implements Listener,Serializable {			
			
		}
	}
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<ACTION extends AbstractAction> extends AbstractObject.AbstractConfiguratorImpl<ACTION> implements Serializable {

		@SuppressWarnings("unchecked")
		@Override
		public void configure(ACTION action, Map<Object, Object> arguments) {
			super.configure(action, arguments);			
			Collection<AbstractInput<?>> inputs = (Collection<AbstractInput<?>>) MapHelper.readByKey(arguments, FIELD_INPUTS);			
			if(action.listener == null) {
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
								public void listenAction(Object argument) {
									if(CollectionHelper.isNotEmpty(inputs))
										for(AbstractInput<?> input : inputs)
											input.writeValueToObjectField();
									try {
										method.invoke(object);
									} catch (Exception exception) {
										throw new RuntimeException(exception);
									}
								}
							};
						}						
					}
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
			
			AbstractCollection collection = (AbstractCollection) MapHelper.readByKey(arguments, FIELD_COLLECTION);
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
		
		public static final String FIELD_INPUTS = "inputs";
		public static final String FIELD_COLLECTION = "collection";
		public static final String FIELD_COLLECTION_UPDATABLE = "collectionUpdatable";
		public static final String FIELD_DATA_TABLE = "dataTable";
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