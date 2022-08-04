package org.cyk.utility.client.controller.web.jsf.primefaces.model.ajax;

import org.primefaces.event.UnselectEvent;

@Deprecated
public class AjaxUnselectEvent extends AbstractAjax<UnselectEvent> {

	@Override
	public AjaxUnselectEvent setEvent(String event) {
		return (AjaxUnselectEvent) super.setEvent(event);
	}
	
}
