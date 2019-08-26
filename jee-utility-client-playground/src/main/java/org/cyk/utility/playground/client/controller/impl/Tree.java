package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.playground.client.controller.api.NodeController;
import org.cyk.utility.playground.client.controller.entities.Node;
import org.cyk.utility.server.persistence.query.filter.FilterDto;
import org.cyk.utility.value.ValueUsageType;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.DefaultTreeNode;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Tree extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private org.primefaces.model.TreeNode root;
	private org.primefaces.model.TreeNode selection;
	private String fields = "identifier,code,name,numberOfChildren";
	private String type;
	
	public void initialise() {
		root = new DefaultTreeNode();
		Collection<Node> nodes = __inject__(NodeController.class).read(new Properties().setFilters(new FilterDto().addField(Node.PROPERTY_PARENTS, null))
				.setIsPageable(Boolean.FALSE).setFields(fields));
		if(__inject__(CollectionHelper.class).isNotEmpty(nodes))
			for(Node index : nodes)
				new TreeNode(type, index, root);
		
		selection = new DefaultTreeNode();
	}
	
	private void __select__(Node node,org.primefaces.model.TreeNode root,Boolean isConsiderTreeNodeNumberOfChildren,Boolean isConsiderTreeNodeNumberOfLoadedChildren) {
		if(root != null) {
			org.primefaces.model.TreeNode destinationRoot = null;
			TreeNode treeNode = (TreeNode) node.getProperties().getTreeNode();
			org.primefaces.model.TreeNode parent = treeNode.getParent();
			treeNode.removeFromParent(isConsiderTreeNodeNumberOfChildren,isConsiderTreeNodeNumberOfLoadedChildren);	
			if(parent != null) {
				List<Object> nodesToBeCreated = new ArrayList<>();
				org.primefaces.model.TreeNode indexDestinationRoot = parent;
				while(destinationRoot == null && indexDestinationRoot!=null && indexDestinationRoot.getData()!=null) {
					destinationRoot = __inject__(PrimefacesHelper.class).getTreeNodeOf(root, indexDestinationRoot.getData());
					if(destinationRoot == null) {
						nodesToBeCreated.add(indexDestinationRoot.getData());
						indexDestinationRoot = indexDestinationRoot.getParent();	
					}
				}
				
				if(destinationRoot == null) {
					destinationRoot = root;
				}else {
					
				}
				
				Collections.reverse(nodesToBeCreated);
				for(Object index : nodesToBeCreated) {
					destinationRoot = new TreeNode(type, index, destinationRoot);
					destinationRoot.setExpanded(Boolean.TRUE);
				}
				destinationRoot = new TreeNode(type, node,null, destinationRoot);
			}
		}
	}
	
	public void select(Node node) {
		__select__(node, selection, Boolean.TRUE, Boolean.FALSE);
		/*if(selection != null) {
			org.primefaces.model.TreeNode destinationRoot = null;
			TreeNode treeNode = (TreeNode) node.getProperties().getTreeNode();
			org.primefaces.model.TreeNode parent = treeNode.getParent();
			treeNode.removeFromParent();	
			if(parent != null) {
				List<Object> nodesToBeCreated = new ArrayList<>();
				org.primefaces.model.TreeNode indexDestinationRoot = parent;
				while(destinationRoot == null && indexDestinationRoot!=null && indexDestinationRoot.getData()!=null) {
					destinationRoot = __inject__(PrimefacesHelper.class).getTreeNodeOf(selection, indexDestinationRoot.getData());
					if(destinationRoot == null) {
						nodesToBeCreated.add(indexDestinationRoot.getData());
						indexDestinationRoot = indexDestinationRoot.getParent();	
					}
				}
				
				if(destinationRoot == null) {
					destinationRoot = selection;
				}else {
					
				}
				
				Collections.reverse(nodesToBeCreated);
				for(Object index : nodesToBeCreated) {
					destinationRoot = new TreeNode(type, index, destinationRoot);
					destinationRoot.setExpanded(Boolean.TRUE);
				}
				destinationRoot = new TreeNode(type, node,null, destinationRoot);
			}
		}*/
		
	}
	
	public void deselect(Node node) {
		__select__(node, root, Boolean.FALSE, Boolean.TRUE);
		/*org.primefaces.model.TreeNode destinationRoot = null;
		TreeNode treeNode = (TreeNode) node.getProperties().getTreeNode();
		org.primefaces.model.TreeNode parent = treeNode.getParent();
		treeNode.removeFromParent(Boolean.FALSE,Boolean.TRUE);
		if(parent != null) {
			List<Object> nodesToBeCreated = new ArrayList<>();
			org.primefaces.model.TreeNode indexDestinationRoot = parent;
			while(destinationRoot == null && indexDestinationRoot!=null && indexDestinationRoot.getData()!=null) {
				destinationRoot = __inject__(PrimefacesHelper.class).getTreeNodeOf(root, indexDestinationRoot.getData());
				if(destinationRoot == null) {
					nodesToBeCreated.add(indexDestinationRoot.getData());
					indexDestinationRoot = indexDestinationRoot.getParent();	
				}
			}
			
			if(destinationRoot == null) {
				destinationRoot = root;
			}else {
				
			}
			
			Collections.reverse(nodesToBeCreated);
			for(Object index : nodesToBeCreated) {
				destinationRoot = new TreeNode(type, index, destinationRoot);
				destinationRoot.setExpanded(Boolean.TRUE);
			}
			destinationRoot = new TreeNode(type, node, destinationRoot);
		}
		*/
	}
	
	public void listenExpand(NodeExpandEvent event) {
		Node node = (Node) event.getTreeNode().getData();
		Collection<Node> children = __inject__(NodeController.class).read(new Properties().setFilters(new FilterDto().addField("parents"
				, Arrays.asList(node.getIdentifier()),ValueUsageType.SYSTEM)).setIsPageable(Boolean.FALSE).setFields(fields));
		if(__inject__(CollectionHelper.class).isNotEmpty(children))
			for(Node index : children)
				new TreeNode(type, index, event.getTreeNode());
    }
	
}
