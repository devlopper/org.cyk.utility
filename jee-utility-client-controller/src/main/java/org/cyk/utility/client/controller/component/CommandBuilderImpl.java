package org.cyk.utility.client.controller.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.command.Command;
import org.cyk.utility.client.controller.component.command.CommandBuilder;
import org.cyk.utility.client.controller.component.command.CommandFunction;

public class CommandBuilderImpl extends AbstractInvisibleComponentBuilderImpl<Command> implements CommandBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private CommandFunction function;
	//private Data data;
	
	@Override
	protected void __execute__(Command command) {
		super.__execute__(command);
		command.setFunction(getFunction());
	}
	
	@Override
	public CommandFunction getFunction() {
		return function;
	}

	@Override
	public CommandBuilder setFunction(CommandFunction function) {
		this.function = function;
		return this;
	}

	@Override
	public CommandFunction getFunction(Boolean injectIfNull) {
		return (CommandFunction) __getInjectIfNull__(FIELD_FUNCTION, injectIfNull);
	}
	
	/**/
	
	public static final String FIELD_FUNCTION = "function";

}
