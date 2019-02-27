package org.cyk.utility.client.controller.impl.verycomplexentity;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.window.WindowBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

@Named @ViewScoped
public class VeryComplexEntityEdit02Page extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected WindowBuilder __getWindowBuilder__() {
		WindowBuilder windowBuilder = super.__getWindowBuilder__();
		windowBuilder.getView().getComponentsBuilder().setIsHandleLayout(Boolean.FALSE);
		return windowBuilder;
	}
	
}
