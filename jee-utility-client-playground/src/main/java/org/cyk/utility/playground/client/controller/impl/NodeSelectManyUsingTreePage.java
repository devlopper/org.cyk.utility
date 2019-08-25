package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NodeSelectManyUsingTreePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Tree sourceTree;
	private Tree destinationTree;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		sourceTree = new Tree();
		sourceTree.initialise();	
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Select Many Using Tree";
	}
}
