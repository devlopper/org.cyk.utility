package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class CommandButton extends AbstractCommand implements Serializable {
	
	private String type = "submit";
	
	@Override
	public CommandButton setIcon(Icon icon) {
		return (CommandButton) super.setIcon(icon);
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<CommandButton> implements Serializable {
		
		@Override
		public void configure(CommandButton commandButton, Map<Object, Object> arguments) {
			super.configure(commandButton, arguments);
			if(commandButton.immediate == null)
				commandButton.immediate = Boolean.FALSE;
		}
		
		@Override
		protected String __getTemplate__(CommandButton commandButton, Map<Object, Object> arguments) {
			return "/command/commandbutton/default.xhtml";
		}
		
		@Override
		protected Class<CommandButton> __getClass__() {
			return CommandButton.class;
		}
	}

	public static CommandButton build(Map<Object,Object> arguments) {
		return Builder.build(CommandButton.class,arguments);
	}
	
	public static CommandButton build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(CommandButton.class, new ConfiguratorImpl());
	}
}