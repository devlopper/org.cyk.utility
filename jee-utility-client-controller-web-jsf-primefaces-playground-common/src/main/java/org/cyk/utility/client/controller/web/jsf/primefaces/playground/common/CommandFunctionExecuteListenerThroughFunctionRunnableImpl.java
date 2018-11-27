package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.component.command.CommandFunction;
import org.cyk.utility.client.controller.component.command.CommandFunctionExecuteListenerThrough;
import org.cyk.utility.client.controller.entities.myentity.MyEntity;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;

public class CommandFunctionExecuteListenerThroughFunctionRunnableImpl extends AbstractFunctionRunnableImpl<CommandFunctionExecuteListenerThrough> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public CommandFunctionExecuteListenerThroughFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				CommandFunction function = (CommandFunction) getFunction().getObject();
				SystemAction action = function.getAction();
				Object data = function.getProperties().getData();
				if(data instanceof MyEntity) {
					
				}else if(data instanceof VerySimpleEntity) {
					if(action instanceof SystemActionCreate)
						VerySimpleEntity.COLLECTION.add( ((VerySimpleEntity) data).setIdentifier(__inject__(RandomHelper.class).getNumeric(4).toString()) );
					else if(action instanceof SystemActionDelete)
						VerySimpleEntity.COLLECTION.remove((VerySimpleEntity) data);
				}
			}
		});
	}
	
}