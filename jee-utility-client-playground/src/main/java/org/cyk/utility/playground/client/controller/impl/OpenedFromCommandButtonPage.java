package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.input.InputText;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class OpenedFromCommandButtonPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private InputText inputText;
	private CommandButton commandButton01,commandButton02;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();		
		inputText = InputText.build();
		
		commandButton01 = CommandButton.build(CommandButton.FIELD_VALUE,"Do the job 01",CommandButton.ConfiguratorImpl.FIELD_OBJECT,this
				,CommandButton.ConfiguratorImpl.FIELD_METHOD_NAME,"doTheJob01"
				,CommandButton.ConfiguratorImpl.FIELD_LISTENER_IS_WINDOW_RENDERED_AS_DIALOG,getIsRenderTypeDialog());
		
		commandButton02 = CommandButton.build(CommandButton.FIELD_VALUE,"Do the job 02",CommandButton.ConfiguratorImpl.FIELD_OBJECT,this
				,CommandButton.ConfiguratorImpl.FIELD_METHOD_NAME,"doTheJob02"
				,CommandButton.ConfiguratorImpl.FIELD_LISTENER_IS_WINDOW_RENDERED_AS_DIALOG,getIsRenderTypeDialog());
	}
	
	public Object doTheJob01() {
		return "The job has been done ! "+inputText.getValue();
	}
	
	public void doTheJob02() {
		
	}
}
