package org.cyk.utility.common.userinterface.panel;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Notifications;
import org.cyk.utility.common.userinterface.command.Command;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class NotificationDialog extends Dialog implements Serializable {
	private static final long serialVersionUID = 1L;

	private Notifications notifications = new Notifications();
	private Component notificationList = new Component();
	private Command okCommand=new Command();
	
	public NotificationDialog() {
		okCommand.setLabelFromIdentifier("ok");
	}
}
