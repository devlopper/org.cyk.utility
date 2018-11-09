package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.command.CommandFunction;
import org.cyk.utility.client.controller.component.command.CommandFunctionExecuteListenerThrough;
import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.crud.MyEntityData;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;

public class CommandFunctionExecuteListenerThroughFunctionRunnableImpl extends AbstractFunctionRunnableImpl<CommandFunctionExecuteListenerThrough> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public CommandFunctionExecuteListenerThroughFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				CommandFunction function = (CommandFunction) getFunction().getObject();
				SystemAction action = function.getAction();
				Object data = function.getProperties().getData();
				if(data instanceof MyEntityData) {
					if(action instanceof SystemActionCreate)
						MyEntityData.COLLECTION.add((MyEntityData) data);
				}
			}
		});
	}
	
}