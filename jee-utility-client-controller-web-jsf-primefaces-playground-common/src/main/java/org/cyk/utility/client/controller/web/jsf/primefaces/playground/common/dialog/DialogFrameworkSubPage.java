package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.dialog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter; 

@Named @ViewScoped @Getter @Setter
public class DialogFrameworkSubPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private String data;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "DialogSub";
	}
	
	public void openSubSub() {
		System.out.println("DialogFrameworkSubPage.openSubSub()");
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 540);
        options.put("height", 280);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
         
        PrimeFaces.current().dialog().openDynamic("subsub", options, null);
	}
	
	public void closeSub() {
		PrimeFaces.current().dialog().closeDynamic(data);
	}
}
