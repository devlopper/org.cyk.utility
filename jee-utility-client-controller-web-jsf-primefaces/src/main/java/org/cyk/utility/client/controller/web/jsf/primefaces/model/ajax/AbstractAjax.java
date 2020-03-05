package org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class AbstractAjax<ARGUMENT> extends AbstractAction implements Serializable {

	protected String event;
	protected Boolean disabled = Boolean.TRUE,partialSubmit=Boolean.TRUE;
	protected Boolean throwNotYetImplemented = Boolean.TRUE;
	
	{
		listener = new Listener.AbstractImpl() {
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
			
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_LISTENER_NULLABLE))) {
				ajax.throwNotYetImplemented = Boolean.FALSE;
			}
			if(StringHelper.isBlank(ajax.getEvent()))
				ajax.setEvent("click");
		}
	}
}