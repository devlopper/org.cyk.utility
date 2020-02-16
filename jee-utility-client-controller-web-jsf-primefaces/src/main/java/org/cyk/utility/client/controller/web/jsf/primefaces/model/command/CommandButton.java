package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.Confirm;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;

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
		public void configure(CommandButton commandButton, Map<Object, Object> arguments) {
			super.configure(commandButton, arguments);
			DataTable dataTable = (DataTable) MapHelper.readByKey(arguments, FIELD_DATA_TABLE);
			if(dataTable == null) {
				
			}else {
				commandButton.update += " :form:"+dataTable.getDialogOutputPanel().getIdentifier();
				commandButton.getRunnerArguments().setSuccessMessageArguments(null);
			}			
		}
		
		@Override
		protected Class<CommandButton> __getClass__() {
			return CommandButton.class;
		}
		
		public static final String FIELD_DATA_TABLE = "dataTable";
	}

	static {
		Configurator.set(CommandButton.class, new ConfiguratorImpl());
	}
}