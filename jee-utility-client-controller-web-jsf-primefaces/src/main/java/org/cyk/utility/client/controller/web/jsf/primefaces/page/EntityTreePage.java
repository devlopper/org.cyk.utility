package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.tag.Tree;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class EntityTreePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Tree tree;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		tree = new Tree();
		tree.setNodeClass(getSystemAction().getEntityClass());
	}
}
