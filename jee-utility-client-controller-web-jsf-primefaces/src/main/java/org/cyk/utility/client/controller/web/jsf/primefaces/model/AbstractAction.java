package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.runnable.Runner;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.Severity;
import org.cyk.utility.client.controller.web.ComponentHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractAction extends AbstractObject implements Serializable {

	protected String process,update;
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
		
	}
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<ACTION extends AbstractAction> extends AbstractObject.AbstractConfiguratorImpl<ACTION> implements Serializable {

		@Override
		public void configure(ACTION action, Map<Object, Object> arguments) {
			super.configure(action, arguments);
			if(action.runnerArguments == null) {
				action.runnerArguments = new Runner.Arguments().assignDefaultMessageArguments();
			}
			
			//we need to update any messages after processing
			// global
			action.addUpdates(__inject__(ComponentHelper.class).getGlobalMessagesTargetInlineComponentClientIdentifier()
					,__inject__(ComponentHelper.class).getGlobalMessagesTargetDialogComponentClientIdentifier()
					,":form:"+__inject__(ComponentHelper.class).getGlobalMessagesTargetGrowlComponentIdentifier());
		}
	}
}