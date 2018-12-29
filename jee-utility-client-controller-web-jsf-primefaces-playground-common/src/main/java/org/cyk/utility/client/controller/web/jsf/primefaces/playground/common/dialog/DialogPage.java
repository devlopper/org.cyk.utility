package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.dialog;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter; 

@Named @ViewScoped @Getter @Setter
public class DialogPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Dialog";
	}
	/*
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		gridBuilder = createDataTableBuilder(this);
		gridIdentifier = gridBuilder.getOutputProperties().getIdentifier().toString();
		viewBuilder.addComponentBuilder(gridBuilder);
		return viewBuilder;
	}
	 */
}
