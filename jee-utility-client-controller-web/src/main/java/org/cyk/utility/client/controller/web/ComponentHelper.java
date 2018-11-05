package org.cyk.utility.client.controller.web;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableRenderTypeButton;
import org.cyk.utility.client.controller.component.command.CommandableRenderTypeLink;
import org.cyk.utility.client.controller.component.grid.Grid;
import org.cyk.utility.client.controller.component.grid.cell.Cell;
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
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;

@Singleton @Named
public class ComponentHelper extends AbstractSingleton implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getUrlByObjectByAction(Object object,Class<SystemAction> systemActionClass) {
		return "http://google.com";
	}
	
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
	
	public Boolean isGrid(Object object) {
		return object instanceof Grid;
	}
	
	public Boolean isCell(Object object) {
		return object instanceof Cell;
	}
	
	public Boolean isCommandable(Object object) {
		return object instanceof Commandable;
	}
	
	public Boolean isCommandableRenderTypeButton(Object object) {
		return object instanceof CommandableRenderTypeButton;
	}
	
	public Boolean isCommandableRenderTypeLink(Object object) {
		return object instanceof CommandableRenderTypeLink;
	}
	
	public Class<?> getSystemActionUpdateClass(){
		return SystemActionUpdate.class;
	}
	
	public Class<?> getSystemActionDeleteClass(){
		return SystemActionDelete.class;
	}
	
}
