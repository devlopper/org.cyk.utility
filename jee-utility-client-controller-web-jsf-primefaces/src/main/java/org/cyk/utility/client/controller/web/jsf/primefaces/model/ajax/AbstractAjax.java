package org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.BlockUI;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.Event;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class AbstractAjax<ARGUMENT> extends AbstractAction implements Serializable {

	protected String event;
	protected Boolean disabled = Boolean.TRUE,partialSubmit=Boolean.TRUE,global;
	protected Boolean throwNotYetImplemented = Boolean.TRUE;
	
	{
		listener = new Listener() {
			@Override
			public void listenAction(Object argument) {
				if(Boolean.TRUE.equals(throwNotYetImplemented))
					ThrowableHelper.throwNotYetImplemented();
			}
		};
	}
	
	public void listen(ARGUMENT event) {
		action(event);
	}
	
	/**/
	
	public static final String FIELD_EVENT = "event";
	public static final String FIELD_DISABLED = "disabled";
	public static final String FIELD_GLOBAL = "global";
	public static final String FIELD_PARTIAL_SUBMIT = "partialSubmit";
	public static final String FIELD_THROW_NOT_YET_IMPLEMENTED = "throwNotYetImplemented";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<AJAX extends AbstractAjax<ARGUMENT>,ARGUMENT> extends AbstractAction.AbstractConfiguratorImpl<AJAX> implements Serializable {

		@Override
		public void configure(AJAX ajax, Map<Object, Object> arguments) {
			super.configure(ajax, arguments);
			if(ajax.getRunnerArguments() != null && ajax.getRunnerArguments().getThrowableMessageArguments() != null)
				ajax.getRunnerArguments().getThrowableMessageArguments().setRenderTypes(CollectionHelper.listOf(RenderType.GROWL));
			
			BlockUI blockUI = (BlockUI) MapHelper.readByKey(arguments, FIELD_BLOCK_UI);
			if(blockUI != null) {
				if(ajax.global == null)
					ajax.global = Boolean.FALSE;
				ajax.getEventScripts(Boolean.TRUE).write(Event.START, "PF('"+blockUI.getWidgetVar()+"').show();");
				ajax.getEventScripts(Boolean.TRUE).write(Event.COMPLETE, "PF('"+blockUI.getWidgetVar()+"').hide();");
			}
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE))) {
				ajax.getRunnerArguments().setSuccessMessageArguments(null);
			}
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_LISTENER_NULLABLE))) {
				ajax.throwNotYetImplemented = Boolean.FALSE;
			}
			if(StringHelper.isBlank(ajax.getEvent()))
				ajax.setEvent("click");
			
			if(ajax.global == null)
				ajax.global = Boolean.TRUE;
		}
		
		public static final String FIELD_LISTENER_NULLABLE = "LISTENER_NULLABLE";
		public static final String FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE = "RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE";
		public static final String FIELD_BLOCK_UI = "BLOCK_UI";
	}
}