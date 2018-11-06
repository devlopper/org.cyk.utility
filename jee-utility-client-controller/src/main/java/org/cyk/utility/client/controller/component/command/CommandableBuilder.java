package org.cyk.utility.client.controller.component.command;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.system.action.SystemAction;

public interface CommandableBuilder extends VisibleComponentBuilder<Commandable> {

	CommandableRenderType getRenderType();
	CommandableBuilder setRenderType(CommandableRenderType renderType);
	
	String getName();
	CommandableBuilder setName(String name);
	
	CommandBuilder getCommand();
	CommandableBuilder setCommand(CommandBuilder command);
	CommandBuilder getCommand(Boolean injectIfNull);
	
	NavigationBuilder getNavigation();
	NavigationBuilder getNavigation(Boolean injectIfNull);
	CommandableBuilder setNavigation(NavigationBuilder navigation);
	CommandableBuilder setNavigationIdentifier(Object identifier);
	
	CommandableBuilder setCommandFunctionActionClass(Class<? extends SystemAction> systemActionClass);
	CommandableBuilder addCommandFunctionTryRunRunnable(Collection<Runnable> runnables);
	CommandableBuilder addCommandFunctionTryRunRunnable(Runnable...runnables);
	CommandableBuilder addCommandFunctionTryRunRunnableAt(Runnable runnable,Integer index);
	
}
