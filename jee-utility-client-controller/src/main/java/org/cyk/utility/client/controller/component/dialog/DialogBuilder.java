package org.cyk.utility.client.controller.component.dialog;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.command.CommandableBuilders;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;

public interface DialogBuilder extends VisibleComponentBuilder<Dialog> {

	OutputStringTextBuilder getTitle();
	OutputStringTextBuilder getTitle(Boolean injectIfNull);
	DialogBuilder setTitle(OutputStringTextBuilder title);
	DialogBuilder setTitleValue(String titleValue);

	CommandableBuilders getCommandables();
	CommandableBuilders getCommandables(Boolean injectIfNull);
	DialogBuilder setCommandables(CommandableBuilders commandables);
	DialogBuilder addCommandables(Collection<CommandableBuilder> commandables);
	DialogBuilder addCommandables(CommandableBuilder...commandables);

	CommandableBuilder getOkCommandable();
	CommandableBuilder getOkCommandable(Boolean injectIfNull);
	DialogBuilder setOkCommandable(CommandableBuilder okCommandable);
}
