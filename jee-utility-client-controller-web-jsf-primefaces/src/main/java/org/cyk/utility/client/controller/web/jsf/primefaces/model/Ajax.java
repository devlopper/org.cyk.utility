package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;

import javax.faces.event.AjaxBehaviorEvent;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Ajax extends AbstractAjax<AjaxBehaviorEvent> implements Serializable {

	public Ajax(String event) {
		super(event);
	}

}
