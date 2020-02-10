package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.icon.IconIdentifierGetter;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RemoteRuntimeException;
import org.cyk.utility.__kernel__.user.interface_.message.Message;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.client.controller.web.ComponentHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.Confirm;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class CommandButton extends Command implements Serializable {
	
	private Listener listener;
	private MessageRenderer.Arguments successMessageArguments = new MessageRenderer.Arguments(MessageRenderer.Arguments.INFORMATION_INLINE_DIALOG);
	private MessageRenderer.Arguments throwableMessageArguments = new MessageRenderer.Arguments(MessageRenderer.Arguments.ERROR_INLINE_DIALOG);
	private Confirm confirm = new Confirm().setDisabled(Boolean.TRUE);
	private String type = "submit";
	private String icon;
	
	public void action() {
		try {
			__action__();
			if(listener != null)
				listener.listenAction();
			if(successMessageArguments != null)
				MessageRenderer.getInstance().render(new Message().setSummary("Opération bien éffectuée")
					.setSeverity(successMessageArguments.getSeverity()), successMessageArguments.getRenderTypes());
		} catch (Exception exception) {
			if(throwableMessageArguments != null) {
				Message message = new Message().setSeverity(throwableMessageArguments.getSeverity());
				if(exception instanceof RemoteRuntimeException) {
					RemoteRuntimeException remoteRuntimeException = (RemoteRuntimeException) exception;
					org.cyk.utility.__kernel__.throwable.Message remoteMessage = CollectionHelper.getFirst(remoteRuntimeException.getMessages());
					message.setSummary(remoteMessage.getSummary());
					message.setDetails(remoteMessage.getDetails());
				}else {
					message.setSummary("Erreur lors de l'exécution de l'opération : "+exception.toString());
				}
				MessageRenderer.getInstance().render(message, throwableMessageArguments.getRenderTypes());
			}
		}
	}
	
	protected void __action__() {}
	
	public CommandButton setIcon(Icon icon) {
		if(icon == null)
			this.icon = null;
		else
			this.icon = IconIdentifierGetter.FONT_AWSOME.get(icon);
		return this;
	}
	
	/**/
	
	public static interface Listener {
		
		void listenAction();
		
	}
	
	/**/
	
	public static class ConfiguratorImpl extends Configurator.AbstractImpl<CommandButton> implements Serializable {

		@Override
		public void configure(CommandButton commandButton, Map<Object, Object> arguments) {
			super.configure(commandButton, arguments);
			//we need to show messages
			Collection<String> updatables = CollectionHelper.listOf(StringUtils.split(commandButton.getUpdate(),","));			
			if(updatables == null)
				updatables = new ArrayList<>();
			updatables.add(__inject__(ComponentHelper.class).getGlobalMessagesTargetInlineComponentClientIdentifier());
			updatables.add(__inject__(ComponentHelper.class).getGlobalMessagesTargetDialogComponentClientIdentifier());
			updatables.add(":form:"+__inject__(ComponentHelper.class).getGlobalMessagesTargetGrowlComponentIdentifier());
			commandButton.setUpdate(StringHelper.concatenate(updatables, ","));
		}
		
		@Override
		protected Class<CommandButton> __getClass__() {
			return CommandButton.class;
		}
		
		@Override
		protected Collection<String> __getFieldsNames__() {
			return List.of("value","listener","update");
		}

	}

	static {
		Configurator.set(CommandButton.class, new ConfiguratorImpl());
	}
}
