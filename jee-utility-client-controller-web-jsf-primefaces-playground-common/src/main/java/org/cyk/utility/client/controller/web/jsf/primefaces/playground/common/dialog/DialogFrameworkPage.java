package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.dialog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter; 

@Named @ViewScoped @Getter @Setter
public class DialogFrameworkPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Dialog";
	}
	
	public void openSub() {
		System.out.println("DialogFrameworkPage.openSub()");
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 640);
        options.put("height", 340);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
         
        PrimeFaces.current().dialog().openDynamic("sub", options, null);
	}
	
	public void listenReturn(SelectEvent event) {
		System.out.println("DialogFrameworkPage.listenReturn() : "+event.getObject());
	}
	
}
