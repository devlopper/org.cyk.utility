package org.cyk.utility.client.controller.component.command;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.system.action.SystemAction;

public interface CommandableBuilder extends VisibleComponentBuilder<Commandable> {

	CommandableRenderType getRenderType();
	CommandableBuilder setRenderType(CommandableRenderType renderType);
	
	String getName();
	CommandableBuilder setName(String name);
	
	CommandableBuilder setNameInternalization(InternalizationStringBuilder nameInternalization);
	CommandableBuilder setNameInternalizationKeyValue(String nameInternalizationKeyValue);
	
	CommandBuilder getCommand();
	CommandableBuilder setCommand(CommandBuilder command);
	CommandBuilder getCommand(Boolean injectIfNull);
	
	NavigationBuilder getNavigation();
	NavigationBuilder getNavigation(Boolean injectIfNull);
	CommandableBuilder injectNavigationIfNull();
	CommandableBuilder setNavigation(NavigationBuilder navigation);
	CommandableBuilder setNavigationIdentifier(Object identifier);
	CommandableBuilder setNavigationParameters(Object...keyValues);
	CommandableBuilder setNavigationIdentifierAndParameters(Object identifier,Object[] keyValues);
	CommandableBuilder addNavigationDynamicParameterNames(Object...names);
	CommandableBuilder setNavigationSystemAction(SystemAction systemAction);
	CommandableBuilder setNavigationIdentifierBuilderSystemAction(SystemAction systemAction);
	
	CommandableBuilder setCommandFunctionActionClass(Class<? extends SystemAction> systemActionClass);
	
	CommandableBuilder setCommandFunctionData(Data data);
	
	CommandableBuilder addCommandFunctionTryRunRunnable(Collection<Runnable> runnables);
	CommandableBuilder addCommandFunctionTryRunRunnable(Runnable...runnables);
	CommandableBuilder addCommandFunctionTryRunRunnableAt(Runnable runnable,Integer index);
	
}
