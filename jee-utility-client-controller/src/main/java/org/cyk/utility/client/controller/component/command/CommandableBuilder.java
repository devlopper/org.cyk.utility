package org.cyk.utility.client.controller.component.command;

import java.util.Collection;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.client.controller.component.ComponentRole;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.window.WindowRenderType;
import org.cyk.utility.client.controller.data.Data;

public interface CommandableBuilder extends VisibleComponentBuilder<Commandable> {

	@Override CommandableBuilder addRoles(ComponentRole... roles);
	
	CommandableRenderType getRenderType();
	CommandableBuilder setRenderType(CommandableRenderType renderType);
	
	String getName();
	CommandableBuilder setName(String name);
	
	@Override CommandableBuilder setDerivableFieldNames(Object... values);
	
	Icon getIcon();
	CommandableBuilder setIcon(Icon icon);
	
	SystemAction getSystemAction();
	CommandableBuilder setSystemAction(SystemAction systemAction);
	
	CommandBuilder getCommand();
	CommandableBuilder setCommand(CommandBuilder command);
	CommandBuilder getCommand(Boolean injectIfNull);
	
	UniformResourceIdentifierAsFunctionParameter getUniformResourceIdentifier();
	UniformResourceIdentifierAsFunctionParameter getUniformResourceIdentifier(Boolean injectIfNull);
	CommandableBuilder setUniformResourceIdentifier(UniformResourceIdentifierAsFunctionParameter uniformResourceIdentifier);
	CommandableBuilder setUniformResourceIdentifierSystemAction(SystemAction systemAction);
	CommandableBuilder setUniformResourceIdentifierSystemActionClass(Class<? extends SystemAction> systemActionClass);
	CommandableBuilder setUniformResourceIdentifierSystemActionEntityClass(Class<?> systemActionEntityClass);
	
	
	CommandableBuilder setCommandFunction(CommandFunction function);
	CommandableBuilder setCommandFunctionAction(SystemAction systemAction);
	CommandableBuilder setCommandFunctionActionCustom(Object actionIdentifier);
	CommandableBuilder setCommandFunctionActionClass(Class<? extends SystemAction> systemActionClass);
	
	CommandableBuilder setCommandFunctionData(Data data);
	
	CommandableBuilder addCommandFunctionTryRunRunnable(Collection<Runnable> runnables);
	CommandableBuilder addCommandFunctionTryRunRunnable(Runnable...runnables);
	CommandableBuilder addCommandFunctionTryRunRunnableAt(Runnable runnable,Integer index);
	
	Class<? extends WindowRenderType> getWindowRenderTypeClass();
	CommandableBuilder setWindowRenderTypeClass(Class<? extends WindowRenderType> windowRenderTypeClass);
	
	/**/
	
	String PROPERTY_NAME = "name";
	String PROPERTY_ICON = "icon";
}
