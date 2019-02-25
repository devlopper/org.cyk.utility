package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.notification.NotificationBuilders;
import org.cyk.utility.object.Objects;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
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
				__act__(action,data);
			}
		});
	}
	
	protected void __act__(SystemAction action,Object data) {
		if(action!=null) {
			Properties properties = new Properties();
			Objects entities = action.getEntities();
			if(action instanceof SystemActionCreate) {
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				__inject__(Controller.class).create(object,properties);
			}else if(action instanceof SystemActionRead) {
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				__inject__(Controller.class).redirect(action.getEntityClass(),__inject__(FieldHelper.class).getFieldValueBusinessIdentifier(object));
			}else if(action instanceof SystemActionUpdate) {
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				properties.setFields(getFunction().getProperty(Properties.FIELDS));
				__inject__(Controller.class).update(object,properties);
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
				//TODO not needed anymore
				Object object = __inject__(CollectionHelper.class).getFirst(entities);
				__inject__(Controller.class).redirect(action.getEntityClass(),__inject__(FieldHelper.class).getFieldValueBusinessIdentifier(object));
			}else if(action instanceof SystemActionAdd) {
				/*
				 * MVC : To add data to local collection
				 */
			}else
				__inject__(ThrowableHelper.class).throwRuntimeException("System action not yet handle : "+action.getIdentifier());	
			
			if(properties.getNotificationBuilders() instanceof NotificationBuilders)
				getFunction().getNotificationBuilders(Boolean.TRUE).add((NotificationBuilders) properties.getNotificationBuilders());
		}
	}
	
}