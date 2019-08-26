package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.tag.Tree;
import org.cyk.utility.client.controller.web.jsf.primefaces.tag.TreeSelectionMode;
import org.cyk.utility.playground.client.controller.api.NodeController;
import org.cyk.utility.playground.client.controller.entities.Node;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NodeSelectManyUsingTreePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Tree<Node> tree;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		tree = new Tree<Node>(__inject__(NodeController.class));
		tree.setRootLabel("Disponilbe");
		tree.setSelectionLabel("Selectionnés");
		tree.setSelectable(Boolean.TRUE);
		tree.setSelectionMode(TreeSelectionMode.REMOVE_ADD);
		tree.initialise();
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Select Many Using Tree";
	}
}
