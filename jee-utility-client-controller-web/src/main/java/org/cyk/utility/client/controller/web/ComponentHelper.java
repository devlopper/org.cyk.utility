package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.client.controller.component.command.CommandableButton;
import org.cyk.utility.client.controller.component.input.InputStringLineMany;
import org.cyk.utility.client.controller.component.input.InputStringLineOne;
import org.cyk.utility.client.controller.component.layout.Insert;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutItem;
import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeColumnPanel;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.component.output.OutputStringLabel;
import org.cyk.utility.client.controller.component.output.OutputStringMessage;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.random.RandomHelper;

@Singleton @Named
public class ComponentHelper extends AbstractSingleton implements Serializable {
	private static final long serialVersionUID = 1L;

	public RandomHelper getRandomHelper() {
		return __inject__(RandomHelper.class);
	}
	
	public Boolean isInputStringLineOne(Object object) {
		return object instanceof InputStringLineOne;
	}
	
	public Boolean isInputStringLineMany(Object object) {
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
	
	public Boolean isCommandableButton(Object object) {
		return object instanceof CommandableButton;
	}
	
	public Boolean isLayout(Object object) {
		return object instanceof Layout;
	}
	
	public Boolean isLayoutItem(Object object) {
		return object instanceof LayoutItem;
	}
	
	public Boolean isView(Object object) {
		return object instanceof View;
	}
	
	public Boolean isInsert(Object object) {
		return object instanceof Insert;
	}
	
	public Boolean isMenu(Object object) {
		return object instanceof Menu;
	}
	
	public Boolean isMenuRenderTypeColumnPanel(Object object) {
		return object instanceof MenuRenderTypeColumnPanel;
	}
	
	public Boolean isMenuRenderTypeRowBar(Object object) {
		return object instanceof MenuRenderTypeRowBar;
	}
}
