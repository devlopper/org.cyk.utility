package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class CommandButton extends Command implements Serializable {
	
	private Listener listener;
	
	public void action() {
		if(listener != null)
			listener.listenAction();
	}
	
	/**/
	
	public static interface Listener {
		
		void listenAction();
		
	}
}
