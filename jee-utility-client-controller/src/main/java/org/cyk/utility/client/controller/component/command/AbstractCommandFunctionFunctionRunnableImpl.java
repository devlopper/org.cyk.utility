package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.object.Objects;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRedirect;
import org.cyk.utility.system.action.SystemActionSelect;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractCommandFunctionFunctionRunnableImpl extends AbstractFunctionRunnableImpl<CommandFunction> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public AbstractCommandFunctionFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				CommandFunction function = (CommandFunction) getFunction();
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
			}else if(action instanceof SystemActionSelect) {
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				__inject__(Controller.class).select(object);
			}else if(action instanceof SystemActionProcess) {
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				__inject__(Controller.class).process(object);
			}else if(action instanceof SystemActionRedirect) {
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				__inject__(Controller.class).redirect(action.getEntityClass(),__inject__(FieldHelper.class).getFieldValueBusinessIdentifier(object));
			}else if(action instanceof SystemActionAdd) {
				/*
				 * MVC : To add data to local collection
				 */
			}else
				__inject__(ThrowableHelper.class).throwRuntimeException("System action not yet handle : "+action.getIdentifier());	
		}
	}
	
}