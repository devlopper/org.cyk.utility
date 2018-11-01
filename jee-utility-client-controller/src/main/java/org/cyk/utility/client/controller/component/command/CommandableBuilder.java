package org.cyk.utility.client.controller.component.command;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.system.action.SystemAction;

public interface CommandableBuilder<COMPONENT extends Commandable> extends VisibleComponentBuilder<COMPONENT> {

	CommandBuilder getCommand();
	CommandableBuilder<COMPONENT> setCommand(CommandBuilder command);
	CommandBuilder getCommand(Boolean injectIfNull);
	
	CommandableBuilder<COMPONENT> setCommandFunctionActionClass(Class<? extends SystemAction> systemActionClass);
	CommandableBuilder<COMPONENT> addCommandFunctionTryRunRunnable(Collection<Runnable> runnables);
	CommandableBuilder<COMPONENT> addCommandFunctionTryRunRunnable(Runnable...runnables);
	CommandableBuilder<COMPONENT> addCommandFunctionTryRunRunnableAt(Runnable runnable,Integer index);
	
}
