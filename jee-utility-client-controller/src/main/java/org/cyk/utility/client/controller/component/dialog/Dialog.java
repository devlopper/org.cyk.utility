package org.cyk.utility.client.controller.component.dialog;

import java.util.Collection;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.Commandables;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public interface Dialog extends VisibleComponent {

	OutputStringText getTitle();
	OutputStringText getTitle(Boolean injectIfNull);
	Dialog setTitle(OutputStringText title);
	Dialog setTitleValue(String titleValue);
	
	Commandables getCommandables();
	Commandables getCommandables(Boolean injectIfNull);
	Dialog setCommandables(Commandables commandables);
	Dialog addCommandables(Collection<Commandable> commandables);
	Dialog addCommandables(Commandable...commandables);
	
	Commandable getOkCommandable();
	Dialog setOkCommandable(Commandable okCommandable);
	
}
