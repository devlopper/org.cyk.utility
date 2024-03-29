package org.cyk.utility.client.controller.component.dialog;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.Commandables;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public class DialogImpl extends AbstractVisibleComponentImpl implements Dialog,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringText title;
	private Commandables commandables;
	private Commandable okCommandable;
	
	@Override
	public OutputStringText getTitle() {
		return title;
	}

	@Override
	public OutputStringText getTitle(Boolean injectIfNull) {
		if(title == null && Boolean.TRUE.equals(injectIfNull))
			title = __inject__(OutputStringText.class);
		return title;
	}

	@Override
	public Dialog setTitle(OutputStringText title) {
		this.title = title;
		return this;
	}

	@Override
	public Dialog setTitleValue(String titleValue) {
		getTitle(Boolean.TRUE).setValue(titleValue);
		return this;
	}
	
	@Override
	public Commandables getCommandables() {
		return commandables;
	}
	
	@Override
	public Commandables getCommandables(Boolean injectIfNull) {
		if(commandables == null && Boolean.TRUE.equals(injectIfNull))
			commandables = __inject__(Commandables.class);
		return commandables;
	}
	
	@Override
	public Dialog setCommandables(Commandables commandables) {
		this.commandables = commandables;
		return this;
	}
	
	@Override
	public Dialog addCommandables(Collection<Commandable> commandables) {
		getCommandables(Boolean.TRUE).add(commandables);
		return this;
	}
	
	@Override
	public Dialog addCommandables(Commandable... commandables) {
		getCommandables(Boolean.TRUE).add(commandables);
		return this;
	}
	
	@Override
	public Commandable getOkCommandable() {
		return okCommandable;
	}
	
	@Override
	public Dialog setOkCommandable(Commandable okCommandable) {
		this.okCommandable = okCommandable;
		return this;
	}

}
