package org.cyk.utility.client.controller.web.jsf.primefaces.tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.server.persistence.query.filter.FilterDto;
import org.cyk.utility.value.ValueUsageType;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.model.DefaultTreeNode;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Tree<NODE extends DataIdentifiedByString<NODE>> extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private org.primefaces.model.TreeNode root;
	private String rootLabel = "Available";
	private Boolean selectable;
	private TreeSelectionMode selectionMode;
	private org.primefaces.model.TreeNode selection;
	private String selectionLabel = "Selected";
	
	private ControllerEntity<NODE> controller;
	private String fields = "identifier,code,name,numberOfChildren";
	private String type;
	
	public Tree(ControllerEntity<NODE> controller) {
		this.controller = controller;
	}
	
	public void initialise() {
		root = new DefaultTreeNode();
		Collection<NODE> nodes = controller.read(new Properties().setFilters(new FilterDto().addField(DataIdentifiedByString.PROPERTY_PARENTS, null))
				.setIsPageable(Boolean.FALSE).setFields(fields));
		if(__inject__(CollectionHelper.class).isNotEmpty(nodes))
			for(NODE index : nodes)
				new TreeNode(type, index, root);
		
		if(Boolean.TRUE.equals(selectable)) {
			if(selectionMode == null)
				selectionMode = TreeSelectionMode.REMOVE_ADD;
			
			if(TreeSelectionMode.REMOVE_ADD.equals(selectionMode)) {
				selection = new DefaultTreeNode();
			}
		}
	}
	
	private void __select__(NODE node,org.primefaces.model.TreeNode root,Boolean isConsiderTreeNodeNumberOfChildren,Boolean isConsiderTreeNodeNumberOfLoadedChildren) {
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
	
	public void select(NODE node) {
		__select__(node, selection, Boolean.TRUE, Boolean.FALSE);		
	}
	
	public void deselect(NODE node) {
		__select__(node, root, Boolean.FALSE, Boolean.TRUE);
	}
	
	public void listenExpand(NodeExpandEvent event) {
		@SuppressWarnings("unchecked")
		NODE node = (NODE) event.getTreeNode().getData();
		Collection<NODE> children = controller.read(new Properties().setFilters(new FilterDto().addField("parents"
				, Arrays.asList(node.getIdentifier()),ValueUsageType.SYSTEM)).setIsPageable(Boolean.FALSE).setFields(fields));
		if(__inject__(CollectionHelper.class).isNotEmpty(children))
			for(NODE index : children)
				new TreeNode(type, index, event.getTreeNode());
    }
	
}
