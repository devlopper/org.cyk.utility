package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.client.controller.component.command.CommandFunctionExecuteListenerThrough;
import org.cyk.utility.client.controller.entities.myentity.MyEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;

public class CommandFunctionExecuteListenerThroughFunctionRunnableImpl extends AbstractFunctionRunnableImpl<CommandFunctionExecuteListenerThrough> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public CommandFunctionExecuteListenerThroughFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				CommandFunction function = (CommandFunction) getFunction().getObject();
				//SystemAction action = function.getAction();
				Object data = function.getProperties().getData();
				if(data instanceof MyEntity) {
					
				}else if(data instanceof VerySimpleEntity) {
					
				}
			}
		});
	}
	
}