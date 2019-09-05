package org.cyk.utility.client.controller.component.dialog;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilders;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.time.DurationBuilder;
import org.cyk.utility.time.DurationStringBuilder;

public class DialogBuilderImpl extends AbstractVisibleComponentBuilderImpl<Dialog> implements DialogBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringTextBuilder title;
	private CommandableBuilders commandables;
	private CommandableBuilder okCommandable;
	
	@Override
	protected void __execute__(Dialog dialog) {
		DurationBuilder durationBuilder = __inject__(DurationBuilder.class).setBeginToNow();
		OutputStringTextBuilder title = getTitle(Boolean.TRUE);
		System.out.println("\tDialogBuilderImpl.__execute__() 1 : "+__inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder.setEndToNow()).execute().getOutput());
		durationBuilder.setBeginToNow();
		__setRequestAndContextAndUniformResourceLocatorMapOf__(title);
		dialog.setTitle(title.execute().getOutput());
		System.out.println("\tDialogBuilderImpl.__execute__() 2 : "+__inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder.setEndToNow()).execute().getOutput());
		durationBuilder.setBeginToNow();
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
		System.out.println("\tDialogBuilderImpl.__execute__() 3 : "+__inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder.setEndToNow()).execute().getOutput());
		durationBuilder.setBeginToNow();
		if(__injectCollectionHelper__().isNotEmpty(commandables)) {
			for(CommandableBuilder index : commandables.get()) {
				__setRequestAndContextAndUniformResourceLocatorMapOf__(index);
				dialog.addCommandables(index.execute().getOutput());
			}
		}
		System.out.println("\tDialogBuilderImpl.__execute__() 4 : "+__inject__(DurationStringBuilder.class).setDurationBuilder(durationBuilder.setEndToNow()).execute().getOutput());
		durationBuilder.setBeginToNow();
	}
	
	@Override
	public CommandableBuilder getOkCommandable() {
		return okCommandable;
	}
	
	@Override
	public CommandableBuilder getOkCommandable(Boolean injectIfNull) {
		if(okCommandable == null && Boolean.TRUE.equals(injectIfNull)) {
			okCommandable = __inject__(CommandableBuilder.class);
			okCommandable.setNameInternalizationKeyValue("ok");
		}
		return okCommandable;
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
		if(title == null && Boolean.TRUE.equals(injectIfNull)) {
			title = __inject__(OutputStringTextBuilder.class);
			title.setValueInternalizationKeyValue("message");
		}
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

	public static final String FIELD_TITLE = "title";
	public static final String FIELD_COMMANDABLES = "commandables";
	public static final String FIELD_OK_COMMANDABLE = "okCommandable";
	
}
