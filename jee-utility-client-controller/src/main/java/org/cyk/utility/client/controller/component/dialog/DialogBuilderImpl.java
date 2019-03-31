package org.cyk.utility.client.controller.component.dialog;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilders;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;

public class DialogBuilderImpl extends AbstractVisibleComponentBuilderImpl<Dialog> implements DialogBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringTextBuilder title;
	private CommandableBuilders commandables;
	private CommandableBuilder okCommandable;
	
	@Override
	protected void __execute__(Dialog dialog) {
		OutputStringTextBuilder title = getTitle(Boolean.TRUE);
		__setRequestAndContextAndUniformResourceLocatorMapOf__(title);
		dialog.setTitle(title.execute().getOutput());
		CommandableBuilders commandables = getCommandables();
		
		if(__injectCollectionHelper__().isEmpty(commandables)) {
			CommandableBuilder commandable = getOkCommandable(Boolean.TRUE);
			if(commandable!=null) {
				/*if(commandables!=null)
					commandables = getCommandables(Boolean.TRUE);
				commandables.add(commandable);
				*/
				__setRequestAndContextAndUniformResourceLocatorMapOf__(commandable);
				dialog.setOkCommandable(commandable.execute().getOutput());
				dialog.addCommandables(dialog.getOkCommandable());
			}
		}
		
		if(__injectCollectionHelper__().isNotEmpty(commandables)) {
			for(CommandableBuilder index : commandables.get()) {
				__setRequestAndContextAndUniformResourceLocatorMapOf__(index);
				dialog.addCommandables(index.execute().getOutput());
			}
		}
	}
	
	@Override
	public CommandableBuilder getOkCommandable() {
		return okCommandable;
	}
	
	@Override
	public CommandableBuilder getOkCommandable(Boolean injectIfNull) {
		CommandableBuilder commandable = (CommandableBuilder) __getInjectIfNull__(FIELD_OK_COMMANDABLE, injectIfNull);
		commandable.setNameInternalizationKeyValue("ok");
		return commandable;
	}
	
	@Override
	public DialogBuilder setOkCommandable(CommandableBuilder okCommandable) {
		this.okCommandable = okCommandable;
		return this;
	}
	
	@Override
	public OutputStringTextBuilder getTitle() {
		return title;
	}

	@Override
	public OutputStringTextBuilder getTitle(Boolean injectIfNull) {
		OutputStringTextBuilder title = (OutputStringTextBuilder) __getInjectIfNull__(FIELD_TITLE, injectIfNull);
		title.setValueInternalizationKeyValue("message");
		return title;
	}
	
	@Override
	public DialogBuilder setTitle(OutputStringTextBuilder title) {
		this.title = title;
		return this;
	}
	
	@Override
	public DialogBuilder setTitleValue(String titleValue) {
		getTitle(Boolean.TRUE).setValue(titleValue);
		return this;
	}
	
	@Override
	public CommandableBuilders getCommandables() {
		return commandables;
	}
	
	@Override
	public CommandableBuilders getCommandables(Boolean injectIfNull) {
		return (CommandableBuilders) __getInjectIfNull__(FIELD_COMMANDABLES, injectIfNull);
	}
	
	@Override
	public DialogBuilder setCommandables(CommandableBuilders commandables) {
		this.commandables = commandables;
		return this;
	}
	
	@Override
	public DialogBuilder addCommandables(Collection<CommandableBuilder> commandables) {
		getCommandables(Boolean.TRUE).add(commandables);
		return this;
	}
	
	@Override
	public DialogBuilder addCommandables(CommandableBuilder... commandables) {
		getCommandables(Boolean.TRUE).add(commandables);
		return this;
	}

	private static final String FIELD_TITLE = "title";
	private static final String FIELD_COMMANDABLES = "commandables";
	private static final String FIELD_OK_COMMANDABLE = "okCommandable";
	
}
