package org.cyk.utility.client.controller.web.jsf.primefaces.tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.primefaces.event.NodeExpandEvent;

import lombok.Getter;
import lombok.Setter;

public class Tree extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter private TreeNode root;
	@Getter @Setter private String rootIdentifier,rootLabel = "Available";
	@Getter @Setter private Boolean selectable;
	@Getter @Setter private TreeSelectionMode selectionMode;
	@Getter @Setter private TreeNode selection;
	@Getter @Setter private TreeNode[] rootSelectedNodes;
	@Getter @Setter private String selectionLabel = "Selected";
	
	@Getter @Setter private Class<?> nodeClass;
	@Getter @Setter private Collection<?> nodesNotHavingParent,initialSelectedNodes;
	@Getter @Setter private String fields = "identifier,code,name,numberOfChildren";
	@Getter @Setter private String type;
	
	@Getter @Setter private BlockUI rootBlockUI,blockUI;
	
	/* working variables */
	
	private Map<Object,TreeNode> __nodes__ = new HashMap<Object, TreeNode>();
	
	/**/
	
	public Tree() {
		this(null);
	}
	
	public Tree(Class<?> dataClass) {
		this.nodeClass = dataClass;
		
		blockUI = new BlockUI();
		blockUI.getProperties().setWidgetVar("treeBlockUI");
		blockUI.getProperties().setBlock("outputPanel");
		blockUI.getProperties().setTrigger("initialise");
		blockUI.getProperties().setBlocked(Boolean.TRUE);
		
		/*
		Event event = __inject__(Event.class);
		event.getProperties().setManyIfNull(new Object[] {Properties.IMMEDIATE,Properties.ASYNC,Properties.DISABLED,Properties.PARTIAL_SUBMIT
				,Properties.RESET_VALUES,Properties.IGNORE_AUTO_UPDATE}, Boolean.FALSE);
		event.getProperties().setManyIfNull(new Object[] {Properties.GLOBAL,Properties.SKIP_CHILDREN}, Boolean.TRUE);
		event.getProperties().setIfNull(Properties.EVENT,"expand");
		
		CommandFunction function = __inject__(CommandFunction.class);
		function.setAction(__inject__(SystemActionCustom.class));
		function.addTryRunRunnables(new Runnable() {
			@Override
			public void run() {
				System.out.println("Tree.Tree(...).new Runnable() {...}.run() ::: "+event+getProperties());
			}
		});
		
		//function.setIsNotifyAfterExecutionPhaseFinally(Boolean.FALSE);
		//function.setIsNotifyOnThrowableIsNull(Boolean.FALSE);
		event.getProperties().setFunction(function);
		
		setProperty(Properties.EXPANDED, event);
		*/
	}
	
	@Override
	protected void __initialise__() {
		super.__initialise__();
		root = new TreeNode();
		if(nodesNotHavingParent == null)
			nodesNotHavingParent = __inject__(Controller.class).read(nodeClass,new Properties().setFilters(new Filter.Dto().addField(DataIdentifiedByString.PROPERTY_PARENTS, null))
				.setIsPageable(Boolean.FALSE).setFields(fields));
		
		if(CollectionHelper.isNotEmpty(nodesNotHavingParent))
			for(Object index : nodesNotHavingParent)
				new TreeNode(type, index, root,__nodes__);
		
		if(Boolean.TRUE.equals(selectable)) {
			rootIdentifier = "tree1";
			if(selectionMode == null)
				selectionMode = TreeSelectionMode.REMOVE_ADD;
			
			if(TreeSelectionMode.REMOVE_ADD.equals(selectionMode)) {
				selection = new TreeNode();
				if(CollectionHelper.isNotEmpty(initialSelectedNodes)) {
					//Expand nodes
					Collection<Object> expandables = new ArrayList<>(initialSelectedNodes);
					do {
						Collection<TreeNode> treeNodes = new ArrayList<>();
						for(Object index : expandables) {
							TreeNode treeNode = (TreeNode) DependencyInjection.inject(PrimefacesHelper.class).getTreeNodeOf(root, index);
							if(treeNode != null) {
								if(treeNode.getNumberOfChildren() > 0)
									expand(treeNode);
								treeNodes.add(treeNode);
							}
						}
						for(TreeNode index : treeNodes)
							expandables.remove(index.getData());
					}while(!expandables.isEmpty());
					//Select leaves nodes
					for(Object index : initialSelectedNodes) {
						TreeNode treeNode = (TreeNode) DependencyInjection.inject(PrimefacesHelper.class).getTreeNodeOf(root, index);
						if(treeNode != null && !treeNode.isParentOfOneOrMoreData(initialSelectedNodes) /*&& treeNode.getChildCount() == 0*/) {		
							select(treeNode.getData());
						}
					}
				}
			}else if(TreeSelectionMode.CHECKBOX.equals(selectionMode)) {
				/*if(CollectionHelper.isNotEmpty(selectedNodes)) {
					Collection<TreeNode> collection = new ArrayList<TreeNode>();
					for(NODE index : selectedNodes) {
						
					}
				}*/	
			}
		}else {
			rootIdentifier = "tree";
		}
		
		rootBlockUI = new BlockUI();
		rootBlockUI.getProperties().setWidgetVar(rootIdentifier+"InteractibityBlocker");
		rootBlockUI.getProperties().setBlock(rootIdentifier);
		rootBlockUI.getProperties().setTrigger(rootIdentifier);
	}
	
	public Collection<?> getSelectedNodes(Integer isHavingNumberOfChildren) {
		Collection<?> selectedNodes = null;
		if(selection!=null) {
			selectedNodes = selection.getDatas(isHavingNumberOfChildren,nodeClass);
		}
		return selectedNodes;
	}
	
	public Collection<?> getSelectedNodes() {
		return getSelectedNodes(null);
	}
	
	private void __select__(Object node,org.primefaces.model.TreeNode source,org.primefaces.model.TreeNode destination,Boolean isConsiderTreeNodeNumberOfChildren,Boolean isConsiderTreeNodeNumberOfLoadedChildren) {
		if(source == null || destination == null) {
			__logWarning__("Tree source and destination must be not null");
			return;
		}
		//What is the tree node associated to this data(node)
		TreeNode treeNode = (TreeNode) __inject__(PrimefacesHelper.class).getTreeNodeOf(source, node);
		if(treeNode == null) {
			__logWarning__(String.format("Tree node not found for data %s",node));
			return;
		}
		node = treeNode.getData();	
		org.primefaces.model.TreeNode destinationRoot = null;
		org.primefaces.model.TreeNode parent = treeNode.getParent();
		treeNode.removeFromParent(isConsiderTreeNodeNumberOfChildren,isConsiderTreeNodeNumberOfLoadedChildren);	
		if(parent != null) {
			List<Object> nodesToBeCreated = new ArrayList<>();
			org.primefaces.model.TreeNode indexDestinationRoot = parent;
			while(destinationRoot == null && indexDestinationRoot!=null && indexDestinationRoot.getData()!=null) {
				destinationRoot = __inject__(PrimefacesHelper.class).getTreeNodeOf(destination, indexDestinationRoot.getData());
				if(destinationRoot == null) {
					nodesToBeCreated.add(indexDestinationRoot.getData());
					indexDestinationRoot = indexDestinationRoot.getParent();	
				}
			}
			
			if(destinationRoot == null) {
				destinationRoot = destination;
			}else {
				
			}
			
			Collections.reverse(nodesToBeCreated);
			for(Object index : nodesToBeCreated) {
				destinationRoot = new TreeNode(type, index, destinationRoot,__nodes__);
				destinationRoot.setExpanded(Boolean.TRUE);
			}
			destinationRoot = new TreeNode(type, node,null, destinationRoot,__nodes__);
		}
		
	}
	
	public void select(Collection<?> nodes) {
		for(Object index : nodes) {
			__select__(index, root,selection, Boolean.TRUE, Boolean.FALSE);
		}
	}

	public void select(Object...nodes) {
		select(CollectionHelper.listOf(nodes));
	}
	
	public void selectOne(Object node) {
		select(node);
	}
	
	public void deselect(Collection<?> nodes) {
		for(Object index : nodes) {
			__select__(index, selection,root, Boolean.FALSE, Boolean.TRUE);	
		}
	}
	
	public void deselect(Object...nodes) {
		deselect(CollectionHelper.listOf(nodes));
	}
	
	public void deselectOne(Object node) {
		deselect(node);
	}
	
	/* Event */
	
	public void listenExpand(NodeExpandEvent event) {
		expand(event.getTreeNode().getData());
    }
	
	public void expand(TreeNode treeNode) {
		treeNode.setExpanded(Boolean.TRUE);
		Object node = treeNode.getData();
		Collection<?> children = __inject__(Controller.class).read(nodeClass,new Properties().setFilters(new Filter.Dto().addField("parents"
				, Arrays.asList( ((org.cyk.utility.client.controller.data.DataIdentifiedByString)node).getIdentifier()),ValueUsageType.SYSTEM)).setIsPageable(Boolean.FALSE).setFields(fields));
		if(CollectionHelper.isNotEmpty(children))
			for(Object index : children)
				new TreeNode(type, index, treeNode,__nodes__);
    }
	
	public void expand(Object node) {
		if(node == null)
			return;
		TreeNode treeNode = __nodes__.get(node);
		if(treeNode == null)
			throw new RuntimeException("tree node to expand has not been found for data <<"+node+">>");
		expand(treeNode);
    }
	
}
