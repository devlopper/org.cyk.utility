package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.system.action.SystemAction;

public class CommandableBuilderImpl extends AbstractVisibleComponentBuilderImpl<Commandable> implements CommandableBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private CommandBuilder command;
	private String name;
	private CommandableRenderType renderType;
	
	@Override
	protected void __execute__(Commandable commandable) {
		super.__execute__(commandable);
		String name = getName();
		commandable.setName(name);
		CommandBuilder commandBuilder = getCommand();
		if(commandBuilder!=null)
			commandable.setCommand(commandBuilder.execute().getOutput());
		
		CommandableRenderType renderType = getRenderType();
		if(renderType == null)
			renderType = __inject__(CommandableRenderTypeButton.class);
		commandable.setRenderType(renderType);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public CommandableBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public CommandableRenderType getRenderType() {
		return renderType;
	}
	
	@Override
	public CommandableBuilder setRenderType(CommandableRenderType renderType) {
		this.renderType = renderType;
		return this;
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
	public CommandableBuilder setCommand(CommandBuilder command) {
		this.command = command;
		return this;
	}
	
	@Override
	public CommandableBuilder addCommandFunctionTryRunRunnable(Collection<Runnable> runnables) {
		getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).addRunnables(runnables);
		return this;
	}
	
	@Override
	public CommandableBuilder addCommandFunctionTryRunRunnable(Runnable...runnables) {
		return addCommandFunctionTryRunRunnable(__injectCollectionHelper__().instanciate(runnables));
	}
	
	@Override
	public CommandableBuilder setCommandFunctionActionClass(Class<? extends SystemAction> systemActionClass) {
		getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setAction(__inject__(systemActionClass));
		return this;
	}
	
	@Override
	public CommandableBuilder addCommandFunctionTryRunRunnableAt(Runnable runnable, Integer index) {
		__inject__(CollectionHelper.class).addElementAt(getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).try_().getRun(Boolean.TRUE).getRunnables(Boolean.TRUE), index, runnable);
		return this;
	}
	
	/**/
	
	public static final String FIELD_COMMAND = "command";
}
