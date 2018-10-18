package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.client.controller.component.command.CommandButton;
import org.cyk.utility.client.controller.component.input.InputStringLineMany;
import org.cyk.utility.client.controller.component.input.InputStringLineOne;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.random.RandomHelper;

@Singleton @Named
public class ComponentHelper extends AbstractSingleton implements Serializable {
	private static final long serialVersionUID = 1L;

	public RandomHelper getRandomHelper() {
		return __inject__(RandomHelper.class);
	}
	
	public Boolean isInputText(Object object) {
		return object instanceof InputStringLineOne;
	}
	
	public Boolean isInputTextArea(Object object) {
		return object instanceof InputStringLineMany;
	}
	
	public Boolean isOutputStringText(Object object) {
		return object instanceof OutputStringText;
	}
	
	public Boolean isOutputStringLabel(Object object) {
		return object instanceof OutputStringLabel;
	}
	
	public Boolean isOutputStringMessage(Object object) {
		return object instanceof OutputStringMessage;
	}
	
	public Boolean isCommandButton(Object object) {
		return object instanceof CommandButton;
	}
}
