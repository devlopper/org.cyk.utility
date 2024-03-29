package org.cyk.utility.client.controller.command;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.object.Objects;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionAdd;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionCustom;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionProcess;
import org.cyk.utility.__kernel__.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.system.action.SystemActionRedirect;
import org.cyk.utility.__kernel__.system.action.SystemActionSelect;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.client.controller.AbstractControllerFunctionImpl;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.data.DataSelect;
import org.cyk.utility.notification.NotificationBuilders;
import org.cyk.utility.server.representation.ResponseEntityDto;

public abstract class AbstractCommandFunctionImpl extends AbstractControllerFunctionImpl implements CommandFunction,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsNotifyAfterExecutionPhaseFinally(Boolean.TRUE);
		setIsNotifyOnThrowableIsNull(Boolean.TRUE);
		setIsNotifyOnThrowableIsNotNull(Boolean.TRUE);
		setIsCatchThrowable(Boolean.TRUE);
	}

	@Override
	protected void __execute__(SystemAction action) {
		Object data = getProperties().getData();
		__act__(action,data);
	}

	@Override
	protected void __executeRepresentation__() {
	}

	@Override
	protected ResponseEntityDto getResponseEntityDto(SystemAction action, Object representation, Response response) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void __act__(SystemAction action,Object data) {
		//clear all previous notifications
		getNotificationBuilders(Boolean.TRUE).removeAll();
		getNotifications(Boolean.TRUE).removeAll();
		
		if(action!=null) {
			Properties properties = new Properties();
			Objects entities = action.getEntities();
			if(action instanceof SystemActionCreate) {
				Object object = CollectionHelper.getFirst(entities);
				__inject__(Controller.class).create(object,properties);
			}else if(action instanceof SystemActionRead) {
				Object object = CollectionHelper.getFirst(entities);
				__inject__(Controller.class).redirect(action.getEntityClass(),FieldHelper.readBusinessIdentifier(object));
			}else if(action instanceof SystemActionUpdate) {
				Object object = CollectionHelper.getFirst(entities);
				properties.setFields(getProperty(Properties.FIELDS));
				__inject__(Controller.class).update(object,properties);
			}else if(action instanceof SystemActionDelete) {
				Object object = CollectionHelper.getFirst(entities);
				__inject__(Controller.class).delete(object);
			}else if(action instanceof SystemActionSelect) {
				Collection<Object> identifiers = null;
				if(data instanceof DataSelect) {
					identifiers = ((DataSelect<?>)data).getSystemIdentifiers();
				}
				__inject__(Controller.class).select(action.getEntityClass(),identifiers,new Properties().setSystemAction(action.getNextAction()));
			}else if(action instanceof SystemActionProcess) {
				Object object = CollectionHelper.getFirst(entities);
				__inject__(Controller.class).process(object,new Properties().setSystemAction(action));
			}else if(action instanceof SystemActionRedirect) {
				//TODO not needed anymore
				Object object = CollectionHelper.getFirst(entities);
				__inject__(Controller.class).redirect(action.getEntityClass(),FieldHelper.readBusinessIdentifier(object));
			}else if(action instanceof SystemActionAdd) {
				/*
				 * MVC : To add data to local collection
				 */
			}else if(action instanceof SystemActionCustom) {
				/* Nothing to do */
			}else
				throw new RuntimeException("System action not yet handle : "+action.getIdentifier());	
			
			if(properties.getNotificationBuilders() instanceof NotificationBuilders)
				getNotificationBuilders(Boolean.TRUE).add((NotificationBuilders) properties.getNotificationBuilders());
		}
	}
	
}
