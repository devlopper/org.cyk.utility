package org.cyk.utility.playground.client.controller.component.command;

import java.io.Serializable;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.Command;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Command commandServerOnClick,commandServerOnDoubleClick;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Command Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		commandServerOnClick = Builder.build(Command.class,Map.of(Command.FIELD_LISTENER,new Command.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("You have click", RenderType.GROWL);
			}
		}));
		
		commandServerOnDoubleClick = Builder.build(Command.class,Map.of(Command.FIELD_LISTENER,new Command.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				MessageRenderer.getInstance().render("You have double click", RenderType.GROWL);
			}
		}));
	}
	
}
