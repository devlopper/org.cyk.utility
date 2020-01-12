package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;

import org.cyk.utility.__kernel__.listener.CallWithOneArgumentListener;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbstractAjax<LISTENER_ARGUMENT> extends AbstractObject implements Serializable {

	private String event;
	private CallWithOneArgumentListener<LISTENER_ARGUMENT> listener;
	private Boolean disabled = Boolean.TRUE,partialSubmit=Boolean.TRUE;
	private Boolean throwNotYetImplemented = Boolean.TRUE;
	
	public AbstractAjax(String event) {
		this.event = event;
		listener = new CallWithOneArgumentListener<LISTENER_ARGUMENT>() {			
			@Override
			public void listen(Object argument) {
				if(Boolean.TRUE.equals(throwNotYetImplemented))
					ThrowableHelper.throwNotYetImplemented();
			}
		};
	}
	
	public void listen(LISTENER_ARGUMENT event) {
		if(listener==null)
			return;
		listener.listen(event);
	}
}
