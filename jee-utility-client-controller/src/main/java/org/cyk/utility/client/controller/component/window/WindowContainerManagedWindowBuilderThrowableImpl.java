package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.client.controller.message.MessageRender;
import org.cyk.utility.client.controller.message.MessageRenderTypeInline;
import org.cyk.utility.client.controller.message.MessagesBuilder;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public class WindowContainerManagedWindowBuilderThrowableImpl extends AbstractWindowContainerManagedWindowBuilderImpl implements WindowContainerManagedWindowBuilderThrowable,Serializable {
	private static final long serialVersionUID = 1L;

	private Throwable throwable;
	
	@Override
	protected void __execute__(WindowBuilder window, SystemAction systemAction, Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		Throwable throwable = getThrowable();
		throwable.printStackTrace();
		window.setTitleValue("ERROR");
		Collection<Object> messages = __inject__(MessagesBuilder.class).addNotificationsFromThrowables(throwable).execute().getOutput();
		__inject__(MessageRender.class).addMessages(messages).setType(__inject__(MessageRenderTypeInline.class)).execute();
	}

	@Override
	public Throwable getThrowable() {
		return throwable;
	}

	@Override
	public WindowContainerManagedWindowBuilderThrowable setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}

}
