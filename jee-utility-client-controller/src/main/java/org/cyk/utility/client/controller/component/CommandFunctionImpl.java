package org.cyk.utility.client.controller.component;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.AbstractControllerFunctionImpl;
import org.cyk.utility.client.controller.message.MessagesBuilder;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.notification.Notification;

public class CommandFunctionImpl extends AbstractControllerFunctionImpl implements CommandFunction,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsCatchThrowable(Boolean.TRUE);
	}
	
	@Override
	public Function<Properties, Void> execute() {
		super.execute();
		Throwable throwable = (Throwable) getProperties().getThrowable();
		if(throwable != null) {
			//build notifications from throwable and send it to user interface
			System.out.println("CommandFunctionImpl.execute() : ERROR : "+throwable);
			Collection<Notification> notifications = __injectCollectionHelper__().instanciate(__inject__(Notification.class).setSummary(throwable.toString())
					.setDetails(throwable.toString()));
			Collection<Object> messages = __inject__(MessagesBuilder.class).setNotifications(notifications).execute().getOutput();
			__inject__(MessageRender.class).setMessages(messages).execute();
		}
		return this;
	}
	
}