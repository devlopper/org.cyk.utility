package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CustomPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowBuilder __getWindowBuilder__() {
		WindowBuilder windowBuilder = super.__getWindowBuilder__();
		windowBuilder.setApplicationNameValue("GLOB | Spec");
		windowBuilder.setTitleValue("MyTitle");
		return windowBuilder;
	}
	
}
