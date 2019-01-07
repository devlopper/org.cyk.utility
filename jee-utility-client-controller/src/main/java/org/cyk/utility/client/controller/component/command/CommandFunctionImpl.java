package org.cyk.utility.client.controller.component.command;

import java.io.Serializable;

import org.cyk.utility.client.controller.AbstractControllerFunctionImpl;
import org.cyk.utility.system.action.SystemAction;

public class CommandFunctionImpl extends AbstractControllerFunctionImpl implements CommandFunction,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIsNotifyAfterExecutionPhaseFinally(Boolean.TRUE);
		setIsNotifyOnSuccess(Boolean.TRUE);
		setIsNotifyOnThrowable(Boolean.TRUE);
		setIsCatchThrowable(Boolean.TRUE);
		/*
		getExecutionPhaseTry(Boolean.TRUE).getRun(Boolean.TRUE).addRunnables(new Runnable() {
			@Override
			public void run() {
				
				SystemAction action = getAction();
				if(action!=null) {
					Objects entities = action.getEntities();
					if(action instanceof SystemActionCreate) {
						Object object = __injectCollectionHelper__().getFirst(entities);
						__inject__(Controller.class).create(object);
					}else if(action instanceof SystemActionUpdate) {
						Object object = __injectCollectionHelper__().getFirst(entities);
						__inject__(Controller.class).update(object);
					}else if(action instanceof SystemActionDelete) {
						Object object = __injectCollectionHelper__().getFirst(entities);
						__inject__(Controller.class).delete(object);
					}else if(action instanceof SystemActionSelect) {
						Object object = __injectCollectionHelper__().getFirst(entities);
						__inject__(Controller.class).select(object);
					}else if(action instanceof SystemActionProcess) {
						Object object = __injectCollectionHelper__().getFirst(entities);
						__inject__(Controller.class).process(object);
					}else if(action instanceof SystemActionAdd) {
						
					}else
						__injectThrowableHelper__().throwRuntimeException("System action not yet handle : "+action.getIdentifier());	
				}
				
				//__inject__(CommandFunctionExecuteListenerThrough.class).setObject(CommandFunctionImpl.this).execute();
			}
		});
		*/
	}

	@Override
	protected void __execute__(SystemAction action) {}
	
	/*
	@Override
	protected void __finally__() {
		super.__finally__();
		
		//build notifications and send it to user interface
		Collection<Notification> notifications = null;
		Throwable throwable = (Throwable) getProperties().getThrowable();
		if(throwable == null) {
			notifications = __injectCollectionHelper__().instanciate(__inject__(NotificationBuilder.class)
					.setSummaryInternalizationStringKeyValue("operation.execution.success.summary")
					.setDetailsInternalizationStringKeyValue("operation.execution.success.details")
					.execute().getOutput());
		}else {
			//System.out.println("TO BE REMOVED ::: CommandFunctionImpl.execute() : ERROR : "+throwable);
			notifications = __injectCollectionHelper__().instanciate(__inject__(NotificationBuilder.class).setThrowable(throwable).execute().getOutput());
		}
		
		if(__injectCollectionHelper__().isNotEmpty(notifications)) {
			Collection<Object> messages = __inject__(MessagesBuilder.class).addNotifications(notifications).execute().getOutput();
			//TODO find a way to inline messages
			__inject__(MessageRender.class).addMessages(messages).setType(__inject__(MessageRenderTypeDialog.class)).execute();
		}
		
	}
	*/
}
