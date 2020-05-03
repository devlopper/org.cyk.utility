package org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class AbstractAjax<ARGUMENT> extends AbstractAction implements Serializable {

	protected String event;
	protected Boolean disabled,partialSubmit;
	/*
	{
		listener = new Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action,Object argument) {
				if(Boolean.TRUE.equals(listenerIsNullable))
					return;
				throw new RuntimeException("Listener definition for event <<"+event+">> is required");
			}
		};
	}
	*/
	public void listen(ARGUMENT event) {
		act(event);
	}
	
	/**/
	
	public static final String FIELD_EVENT = "event";
	public static final String FIELD_DISABLED = "disabled";
	public static final String FIELD_PARTIAL_SUBMIT = "partialSubmit";
	//public static final String FIELD_LISTENER_IS_NULLABLE = "listenerIsNullable";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<AJAX extends AbstractAjax<ARGUMENT>,ARGUMENT> extends AbstractAction.AbstractConfiguratorImpl<AJAX> implements Serializable {

		@Override
		public void configure(AJAX ajax, Map<Object, Object> arguments) {
			super.configure(ajax, arguments);
			if(ajax.runnerArguments != null && ajax.runnerArguments.getThrowableMessageArguments() != null)
				ajax.runnerArguments.getThrowableMessageArguments().setRenderTypes(CollectionHelper.listOf(RenderType.GROWL));
			
			if(ajax.disabled == null)
				ajax.disabled = Boolean.TRUE;
			if(ajax.partialSubmit == null)
				ajax.partialSubmit = Boolean.TRUE;
			//if(ajax.listenerIsNullable == null)
			//	ajax.listenerIsNullable = Boolean.TRUE;
			
			if(StringHelper.isBlank(ajax.event))
				ajax.event = "click";
		}
	}
}