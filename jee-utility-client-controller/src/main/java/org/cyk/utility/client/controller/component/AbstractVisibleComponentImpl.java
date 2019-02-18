package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.command.Command;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.css.Style;
import org.cyk.utility.device.DeviceScreenArea;

public abstract class AbstractVisibleComponentImpl extends AbstractComponentImpl implements VisibleComponent,Serializable {
	private static final long serialVersionUID = 1L;

	private Command command;
	private DeviceScreenArea area;
	private Style style;
	private Object tooltip;
	
	@Override
	public DeviceScreenArea getArea() {
		return area;
	}

	@Override
	public DeviceScreenArea getArea(Boolean injectIfNull) {
		return (DeviceScreenArea) __getInjectIfNull__(FIELD_AREA, injectIfNull);
	}

	@Override
	public VisibleComponent setArea(DeviceScreenArea area) {
		this.area = area;
		return this;
	}
	
	@Override
	public Style getStyle() {
		return style;
	}

	@Override
	public Style getStyle(Boolean injectIfNull) {
		return (Style) __getInjectIfNull__(FIELD_STYLE, injectIfNull);
	}

	@Override
	public VisibleComponent setStyle(Style style) {
		this.style = style;
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
	
	@Override
	public VisibleComponent addCommandFunctionTryRunRunnableAt(Runnable runnable, Integer index) {
		__inject__(CollectionHelper.class).addElementAt(getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).getRunnables(Boolean.TRUE), index, runnable);
		return this;
	}
	
	@Override
	public Object getTooltip() {
		return tooltip;
	}
	
	@Override
	public VisibleComponent setTooltip(Object tooltip) {
		this.tooltip = tooltip;
		return this;
	}
	
	/**/
	
	public static final String FIELD_AREA = "area";
	public static final String FIELD_COMMAND = "command";
	public static final String FIELD_LAYOUT_ITEM = "layoutItem";
	public static final String FIELD_STYLE = "style";
}
