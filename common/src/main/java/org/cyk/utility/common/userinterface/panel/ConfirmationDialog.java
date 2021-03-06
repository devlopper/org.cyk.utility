package org.cyk.utility.common.userinterface.panel;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.command.Command;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class ConfirmationDialog extends Dialog implements Serializable {
	private static final long serialVersionUID = 1L;

	private Command yesCommand = new Command(),noCommand=new Command();
	
	public ConfirmationDialog() {
		yesCommand.setLabelFromIdentifier("yes");
		noCommand.setLabelFromIdentifier("no");
	}
}
