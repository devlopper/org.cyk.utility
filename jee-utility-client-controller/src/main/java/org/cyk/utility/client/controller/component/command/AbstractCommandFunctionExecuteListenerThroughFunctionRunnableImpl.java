package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.command.CommandFunction;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractCommandFunctionExecuteListenerThroughFunctionRunnableImpl extends AbstractFunctionRunnableImpl<CommandFunctionExecuteListenerThrough> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public AbstractCommandFunctionExecuteListenerThroughFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				CommandFunction function = (CommandFunction) getFunction().getObject();
				Object data = function.getProperties().getData();
				SystemAction action = function.getAction();
				__act__(data,action);
			}
		});
	}
	
	protected void __act__(Object data,SystemAction action) {
		if(action!=null) {
			Objects entities = action.getEntities();
			if(action instanceof SystemActionCreate) {
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				__inject__(Controller.class).create(object);
			}else if(action instanceof SystemActionUpdate) {
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				__inject__(Controller.class).update(object);
			}else if(action instanceof SystemActionDelete) {
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				__inject__(Controller.class).delete(object);
			}else
				__inject__(ThrowableHelper.class).throwRuntimeException("System action not yet handle : "+action.getIdentifier());	
		}
	}
	
}