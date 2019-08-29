package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.tag.Tree;
import org.cyk.utility.client.controller.web.jsf.primefaces.tag.TreeSelectionMode;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.playground.client.controller.api.SelectedNodeController;
import org.cyk.utility.playground.client.controller.entities.Node;
import org.cyk.utility.playground.client.controller.entities.SelectedNode;
import org.cyk.utility.system.action.SystemActionCustom;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NodeSelectManyUsingTreePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Tree<Node> tree;
	private Commandable saveCommandable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		Collection<Node> selectedNodes = null;
		Collection<SelectedNode> __selectedNodes__ = __inject__(SelectedNodeController.class).read(new Properties().setIsPageable(Boolean.FALSE));
		if(__inject__(CollectionHelper.class).isNotEmpty(__selectedNodes__)) {
			selectedNodes = __selectedNodes__.stream().map(SelectedNode::getNode).collect(Collectors.toList());
		}
		
		tree = new Tree<Node>();
		tree.setNodeClass(Node.class);
		tree.setRootLabel("Disponilbe");
		tree.setSelectionLabel("Selectionnés");
		tree.setInitialSelectedNodes(selectedNodes);
		tree.setSelectable(Boolean.TRUE);
		tree.setSelectionMode(TreeSelectionMode.REMOVE_ADD);
		tree.initialise();
		
		CommandableBuilder saveCommandableBuilder = __inject__(CommandableBuilder.class);
		saveCommandableBuilder.setName("Enregistrer").setCommandFunctionActionClass(SystemActionCustom.class).addCommandFunctionTryRunRunnable(
			new Runnable() {
				@Override
				public void run() {
					save();
				}
			}
		);
		saveCommandable = saveCommandableBuilder.execute().getOutput();
	}
	
	public void save() {
		Collection<Node> nodes = tree.getSelectedNodes();
		Collection<SelectedNode> selectedNodes = null;
		if(__inject__(CollectionHelper.class).isNotEmpty(nodes)) {
			selectedNodes = new ArrayList<>();
			for(Node index : nodes) {
				SelectedNode selectedNode = __inject__(SelectedNode.class);
				selectedNode.setNode(index);
				selectedNodes.add(selectedNode);
			}
		}
		__inject__(SelectedNodeController.class).deleteAll();
		if(__inject__(CollectionHelper.class).isNotEmpty(selectedNodes)) {
			__inject__(SelectedNodeController.class).createMany(selectedNodes);	
		}
		
		/*
		String message = "Selected : "+tree.getSelectedNodes()+" , Leaves : "+tree.getSelectedNodes(0);
		__inject__(MessageRender.class).addNotificationBuilders().setType(__inject__(MessageRenderTypeDialog.class))
		.addNotifications(__inject__(Notification.class).setSummary(message).setDetails(message))
		.execute();
		*/
		
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Select Many Using Tree";
	}
}
