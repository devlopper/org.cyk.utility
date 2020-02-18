package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Button extends AbstractCommand implements Serializable {

	private String outcome;
	private Map<String,String> parameters;
	
	/**/
	
	public static final String FIELD_OUTCOME = "outcome";
	public static final String FIELD_PARAMETERS = "parameters";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Button> implements Serializable {
		@Override
		protected Class<Button> __getClass__() {
			return Button.class;
		}	
	}

	static {
		Configurator.set(Button.class, new ConfiguratorImpl());
	}
}