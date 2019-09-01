package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.tag.Tree;
import org.cyk.utility.playground.client.controller.entities.Node;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NodeTreePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Tree tree;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		tree = new Tree();
		tree.setNodeClass(Node.class);
		//tree.initialise();
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Arborescence de noeud";
	}
}
