package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.command.Command;
import org.cyk.utility.client.controller.component.layout.LayoutItem;

public abstract class AbstractVisibleComponentImpl extends AbstractComponentImpl implements VisibleComponent,Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutItem layoutItem;
	private Command command;
	private VisibleComponentArea area;
	
	@Override
	public VisibleComponentArea getArea() {
		return area;
	}

	@Override
	public VisibleComponentArea getArea(Boolean injectIfNull) {
		return (VisibleComponentArea) __getInjectIfNull__(FIELD_AREA, injectIfNull);
	}

	@Override
	public VisibleComponent setArea(VisibleComponentArea area) {
		this.area = area;
		return this;
	}
	
	@Override
	public LayoutItem getLayoutItem() {
		return layoutItem;
	}
	
	@Override
	public LayoutItem getLayoutItem(Boolean injectIfNull) {
		return (LayoutItem) __getInjectIfNull__(FIELD_LAYOUT_ITEM, injectIfNull);
	}
	
	@Override
	public VisibleComponent setLayoutItem(LayoutItem layoutItem) {
		this.layoutItem = layoutItem;
		return this;
	}
	
	@Override
	public Command getCommand() {
		return command;
	}
	
	@Override
	public Command getCommand(Boolean injectIfNull) {
		return (Command) __getInjectIfNull__(FIELD_COMMAND, injectIfNull);
	}
	
	@Override
	public VisibleComponent setCommand(Command command) {
		this.command = command;
		return this;
	}
	
	/**/
	
	public static final String FIELD_AREA = "area";
	public static final String FIELD_COMMAND = "command";
	public static final String FIELD_LAYOUT_ITEM = "layoutItem";
}
