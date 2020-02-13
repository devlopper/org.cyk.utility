package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Command extends AbstractCommand implements Serializable {

	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<Command> implements Serializable {
		@Override
		protected Class<Command> __getClass__() {
			return Command.class;
		}	
	}

	static {
		Configurator.set(Command.class, new ConfiguratorImpl());
	}
}