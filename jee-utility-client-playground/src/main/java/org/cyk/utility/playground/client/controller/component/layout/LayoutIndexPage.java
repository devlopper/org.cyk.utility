package org.cyk.utility.playground.client.controller.component.layout;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LayoutIndexPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layoutTwoColumns;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Layout Index Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		layoutTwoColumns = Builder.build(Layout.class);
		layoutTwoColumns.addCells(new Cell());
		layoutTwoColumns.addCells(new Cell());
		layoutTwoColumns.addCells(new Cell());
		layoutTwoColumns.addCells(new Cell());
	}
	
}
