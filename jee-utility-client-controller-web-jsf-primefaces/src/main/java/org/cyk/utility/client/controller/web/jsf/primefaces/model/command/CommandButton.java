package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.Confirm;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class CommandButton extends AbstractCommand implements Serializable {
	
	private Confirm confirm = new Confirm().setDisabled(Boolean.TRUE);
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
		protected Class<CommandButton> __getClass__() {
			return CommandButton.class;
		}
		
	}

	static {
		Configurator.set(CommandButton.class, new ConfiguratorImpl());
	}
}