package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.system.action.SystemAction;

public abstract class AbstractCommandableBuilderImpl<COMPONENT extends Commandable> extends AbstractVisibleComponentBuilderImpl<COMPONENT> implements CommandableBuilder<COMPONENT>,Serializable {
	private static final long serialVersionUID = 1L;

	private CommandBuilder command;
	
	@Override
	protected void __execute__(COMPONENT commandable) {
		super.__execute__(commandable);
		CommandBuilder commandBuilder = getCommand();
		if(commandBuilder!=null)
			commandable.setCommand(commandBuilder.execute().getOutput());
	}
	
	@Override
	public CommandBuilder getCommand() {
		return command;
	}
	
	@Override
	public CommandBuilder getCommand(Boolean injectIfNull) {
		return (CommandBuilder) __getInjectIfNull__(FIELD_COMMAND, injectIfNull);
	}
	
	@Override
	public CommandableBuilder<COMPONENT> setCommand(CommandBuilder command) {
		this.command = command;
		return this;
	}
	
	@Override
	public CommandableBuilder<COMPONENT> addCommandFunctionTryRunRunnable(Collection<Runnable> runnables) {
		getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).addRunnables(runnables);
		return this;
	}
	
	@Override
	public CommandableBuilder<COMPONENT> addCommandFunctionTryRunRunnable(Runnable...runnables) {
		return addCommandFunctionTryRunRunnable(__injectCollectionHelper__().instanciate(runnables));
	}
	
	@Override
	public CommandableBuilder<COMPONENT> setCommandFunctionActionClass(Class<? extends SystemAction> systemActionClass) {
		getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(__inject__(systemActionClass));
		return this;
	}
	
	/**/
	
	public static final String FIELD_COMMAND = "command";
}