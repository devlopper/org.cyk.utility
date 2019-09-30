package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractObject;
import org.cyk.utility.client.controller.component.annotation.Commandable;
import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.annotation.Output;
import org.cyk.utility.client.controller.component.annotation.OutputString;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public abstract class AbstractFormImpl extends AbstractObject implements Form,Serializable {
	private static final long serialVersionUID = 1L;

	@Output @OutputString @OutputStringText
	private String title;
	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Form setTitle(String title) {
		this.title = title;
		return this;
	}
	
	@Override @Commandable(systemActionClass=SystemAction.class) @CommandableButton
	public void submit() {
		__submit__();
	}
	
	protected void __submit__() {}

}
