package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.AbstractControllerFunctionImpl;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeDialog;
import org.cyk.utility.client.controller.message.MessagesBuilder;
import org.cyk.utility.notification.Notification;
import org.cyk.utility.notification.NotificationSeverityError;
import org.cyk.utility.system.action.SystemAction;

public class CommandFunctionImpl extends AbstractControllerFunctionImpl implements CommandFunction,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsCatchThrowable(Boolean.TRUE);
		getExecutionPhaseTry(Boolean.TRUE).getRun(Boolean.TRUE).addRunnables(new Runnable() {
			@Override
			public void run() {
				__inject__(CommandFunctionExecuteListenerThrough.class).setObject(CommandFunctionImpl.this).execute();
			}
		});
	}

	@Override
	protected void __execute__(SystemAction action) {}
	
	@Override
	protected void __finally__() {
		super.__finally__();
		
		//build notifications and send it to user interface
		Collection<Notification> notifications = null;
		Throwable throwable = (Throwable) getProperties().getThrowable();
		if(throwable == null) {
			notifications = __injectCollectionHelper__().instanciate(__inject__(Notification.class).setSummary("Opération exécutée.")
					.setDetails("L'opération a été exécutée avec succès."));
		}else {
			//System.out.println("TO BE REMOVED ::: CommandFunctionImpl.execute() : ERROR : "+throwable);
			notifications = __injectCollectionHelper__().instanciate(__inject__(Notification.class).setSummary(throwable.toString())
					.setDetails(throwable.toString()).setSeverity(__inject__(NotificationSeverityError.class)));
		}
		
		if(__injectCollectionHelper__().isNotEmpty(notifications)) {
			Collection<Object> messages = __inject__(MessagesBuilder.class).addNotifications(notifications).execute().getOutput();
			//TODO find a way to inline messages
			__inject__(MessageRender.class).addMessages(messages).setType(__inject__(MessageRenderTypeDialog.class)).execute();
		}
		
	}
	
}
