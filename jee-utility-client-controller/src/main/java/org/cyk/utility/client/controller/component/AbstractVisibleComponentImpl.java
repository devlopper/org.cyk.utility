package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.command.Command;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.css.Style;
import org.cyk.utility.device.DeviceScreenArea;

public abstract class AbstractVisibleComponentImpl extends AbstractComponentImpl implements VisibleComponent,Serializable {
	private static final long serialVersionUID = 1L;

	private Command command;
	private DeviceScreenArea area;
	private Style style;
	private Object tooltip;
	private Theme theme;
	
	@Override
	public DeviceScreenArea getArea() {
		return area;
	}

	@Override
	public DeviceScreenArea getArea(Boolean injectIfNull) {
		if(area == null && Boolean.TRUE.equals(injectIfNull))
			area = __inject__(DeviceScreenArea.class);
		return area;
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
		if(style == null && Boolean.TRUE.equals(injectIfNull))
			style = __inject__(Style.class);
		return style;
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
		if(command == null && Boolean.TRUE.equals(injectIfNull))
			command = __inject__(Command.class);
		return command;
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
	
	@Override
	public Theme getTheme() {
		return theme;
	}
	
	@Override
	public VisibleComponent setTheme(Theme theme) {
		this.theme = theme;
		return this;
	}
	
	/**/
	
	public static final String FIELD_AREA = "area";
	public static final String FIELD_COMMAND = "command";
	public static final String FIELD_LAYOUT_ITEM = "layoutItem";
	public static final String FIELD_STYLE = "style";
}
