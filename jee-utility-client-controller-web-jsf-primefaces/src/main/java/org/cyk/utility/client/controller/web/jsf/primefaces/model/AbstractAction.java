package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

	protected Listener listener;
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
					if(listener != null)
						listener.listenAction(argument);
				}
			});
			Runner.getInstance().run(runnerArguments);	
		}
	}
	
	protected void __action__(Object argument) {}
	
	/**/
	
	public static final String FIELD_LISTENER = "listener";
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
			
			//we need to show messages after processing
			Collection<String> updatables = CollectionHelper.listOf(StringUtils.split(action.getUpdate(),","));			
			if(updatables == null)
				updatables = new ArrayList<>();
			updatables.add(__inject__(ComponentHelper.class).getGlobalMessagesTargetInlineComponentClientIdentifier());
			updatables.add(__inject__(ComponentHelper.class).getGlobalMessagesTargetDialogComponentClientIdentifier());
			updatables.add(":form:"+__inject__(ComponentHelper.class).getGlobalMessagesTargetGrowlComponentIdentifier());
			action.setUpdate(StringHelper.concatenate(updatables, ","));
		}
	}
}