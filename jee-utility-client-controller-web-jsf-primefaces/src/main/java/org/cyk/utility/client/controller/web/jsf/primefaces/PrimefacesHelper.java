package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.clazz.ClassNameBuilder;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.component.Component;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.data.hierarchy.TreeNodeListener;
import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString;
import org.cyk.utility.client.controller.data.hierarchy.Hierarchy;
import org.cyk.utility.client.controller.event.Event;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.file.File;
import org.cyk.utility.object.Objects;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.Strings;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

@ApplicationScoped @Named
public class PrimefacesHelper extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getScriptInstructionHide(String widgetVar) {
		return String.format(SCRIPT_INSTRUCTION_COMPONENT_METHOD_CALL_FORMAT, widgetVar,"hide");
	}

	public String computeAttributeUpdate(Objects updatables,Strings strings) {
		if(strings == null)
			strings = __inject__(Strings.class);
		if(__inject__(CollectionHelper.class).isNotEmpty(updatables))
			for(Object index : updatables.get()) {
				Component indexComponent = null;
				if(index instanceof Component)
					indexComponent = (Component) index;
				else if(index instanceof ComponentBuilder<?>)
					indexComponent = ((ComponentBuilder<?>)index).getComponent();
				
				if(indexComponent instanceof VisibleComponent) {
					String token = null;
					token = (String)((VisibleComponent)indexComponent).getProperties().getIdentifierAsStyleClass();
					if(__inject__(StringHelper.class).isNotBlank(token))
						strings.add("@(."+token+")");		
				}else
					strings.add(index.toString());
				
			}
		return strings.concatenate(ConstantCharacter.COMA);
	}
	
	public String computeAttributeUpdate(Component component,Strings strings) {
		return computeAttributeUpdate(component.getUpdatables(), strings);
	}
	
	public String computeAttributeUpdate(Component component,String...strings) {
		return computeAttributeUpdate(component, __inject__(Strings.class).add(strings));
	}
	
	public String computeAttributeUpdate(Component component) {
		return computeAttributeUpdate(component,__inject__(Strings.class));
	}
	
	public String computeAttributeUpdate(Event event,Strings strings) {
		return computeAttributeUpdate(event.getUpdatables(), strings);
	}
	
	public String computeAttributeUpdate(Event event,String...strings) {
		return computeAttributeUpdate(event, __inject__(Strings.class).add(strings));
	}
	
	public String computeAttributeUpdate(Event event) {
		return computeAttributeUpdate(event,__inject__(Strings.class));
	}
	
	public StreamedContent computeStreamedContent(File file) {
		return new DefaultStreamedContent(new ByteArrayInputStream(file.getBytes()), file.getMimeType());
	}
	
	public String getMediaPlayerFromMimeType(String mimeType) {
		String player = null;
		if(StringUtils.endsWithIgnoreCase(mimeType, "/pdf"))
			player = "pdf";
		else if(StringUtils.startsWithIgnoreCase(mimeType, "audio/"))
			player = "quicktime";
		else if(StringUtils.startsWithIgnoreCase(mimeType, "video/"))
			player = "quicktime";
		return player;
	}
	
	public <T> DualListModel<T> buildDualList(Collection<T> available,Collection<T> selected) {
		DualListModel<T> dualListModel = new DualListModel<T>(new ArrayList<>(), new ArrayList<>());
		if(__inject__(CollectionHelper.class).isNotEmpty(available)) {
			for(T index : available) {
				Boolean isSelected = null;
				if(__inject__(CollectionHelper.class).isNotEmpty(selected)) {
					for(T indexSub : selected) {
						if(index.equals(indexSub)) {
							isSelected = Boolean.TRUE;
							break;
						}
					}
				}
				if(Boolean.TRUE.equals(isSelected))
					dualListModel.getTarget().add(index);
				else
					dualListModel.getSource().add(index);
			}
		}
		return dualListModel;
	}
	
	public PrimefacesHelper openDialog(String outcome,Map<String,Object> options,Map<String,List<String>> parameters) {
        PrimeFaces.current().dialog().openDynamic(outcome, options, parameters);
        return this;
	}
	
	public PrimefacesHelper openDialog(String outcome,String identifier) {
		Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("width", 1000);
        options.put("height", 580);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        
        Map<String,List<String>> parameters = new HashMap<>();
        parameters.put("identifier", Arrays.asList(identifier));
        
		return openDialog(outcome, options, parameters);
	}
	
	/**/
	
	public <NODE extends DataIdentifiedByString> TreeNode buildTreeNode(Collection<NODE> nodes,Collection<NODE> selectedNodes,org.cyk.utility.client.controller.data.TreeNodeListener<NODE> listener) {
		TreeNode root = new DefaultTreeNode();
		root.setExpanded(Boolean.TRUE);
		if(__inject__(CollectionHelper.class).isNotEmpty(nodes))
			for(NODE index : nodes) {
				TreeNode node = new org.cyk.utility.client.controller.web.jsf.primefaces.DefaultTreeNode<NODE>(index, listener, root);
				//TreeNode node = instantiateTreeNode(index,selectedNodes,listener, root);
				//buildTreeNode(index,nodes,hierarchies,selectedNodes,listener,node);
			}
		return root;
	}
	
	public <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> TreeNode buildTreeNode(Collection<NODE> nodes,Collection<HIERARCHY> hierarchies,Collection<NODE> selectedNodes,TreeNodeListener<NODE,HIERARCHY> listener) {
		TreeNode root = new DefaultTreeNode();
		root.setExpanded(Boolean.TRUE);
		if(__inject__(CollectionHelper.class).isNotEmpty(nodes))
			for(NODE index : nodes) {
				Boolean hasParent = Boolean.FALSE;
				if(__inject__(CollectionHelper.class).isNotEmpty(hierarchies)) {
					for(HIERARCHY hierarchy : hierarchies) {
						if(hierarchy.getChild().getIdentifier().equals(index.getIdentifier())) {
							hasParent = Boolean.TRUE;
							break;
						}
					}	
				}
				
				if(!hasParent) {
					TreeNode node = instantiateTreeNode(index,selectedNodes,listener, root);
					buildTreeNode(index,nodes,hierarchies,selectedNodes,listener,node);
				}
			}
		return root;
	}
	
	private <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> void buildTreeNode(NODE node,Collection<NODE> nodes,Collection<HIERARCHY> hierarchies,Collection<NODE> selectedNodes,TreeNodeListener<NODE,HIERARCHY> listener,TreeNode root) {
		//Find children
		if(__inject__(CollectionHelper.class).isNotEmpty(nodes) && __inject__(CollectionHelper.class).isNotEmpty(hierarchies)) {
			for(NODE index : nodes) {
				for(HIERARCHY hierarchy : hierarchies) {
					if(hierarchy.getParent().getIdentifier().equals(node.getIdentifier()) && hierarchy.getChild().getIdentifier().equals(index.getIdentifier())) {
						TreeNode treeNode = instantiateTreeNode(index,selectedNodes,listener, root);		
						buildTreeNode(index,nodes,hierarchies,selectedNodes,listener,treeNode);
						break;
					}
				}
			}
		}
	}
	
	private <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> TreeNode instantiateTreeNode(NODE node,Collection<NODE> selectedNodes,TreeNodeListener<NODE,HIERARCHY> listener,TreeNode parent) {
		TreeNode treeNode = new DefaultTreeNode(listener.getType(node),listener.getData(node),parent);
		//TreeNode treeNode = new org.cyk.utility.client.controller.web.jsf.primefaces.DefaultTreeNode<NODE>(node,listener,parent);
		//mark as selected if it belongs to 
		if(__inject__(CollectionHelper.class).isNotEmpty(selectedNodes)) {
			treeNode.setSelected(selectedNodes.contains(node));
			if(treeNode.isSelected()) {
				setTreeNodesParentsExpanded(treeNode,Boolean.TRUE);
			}
		}
		return treeNode;
	}
	
	public <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> Collection<TreeNode> getTreeNodeChildren(TreeNode treeNode,Integer depth,Collection<NODE> datas) {
		Collection<TreeNode> children = new ArrayList<>();
		if(treeNode != null) {
			if(depth == null)
				depth = Integer.MAX_VALUE;
			else if(depth < 0)
				depth = 0;
			Integer index = 0;
			Collection<TreeNode> parents = Arrays.asList(treeNode);
			Collection<TreeNode> directChildren;
			Boolean datasIsEmpty = __inject__(CollectionHelper.class).isEmpty(datas);
			while (index < depth && __inject__(CollectionHelper.class).isNotEmpty(parents)) {
				directChildren = new ArrayList<>();
				Collection<TreeNode> nextParents = new ArrayList<>();
				for(TreeNode parent : parents) {
					if(__inject__(CollectionHelper.class).isNotEmpty(parent.getChildren())) {
						if(datasIsEmpty)
							directChildren.addAll(parent.getChildren());
						else {
							for(TreeNode indexDirectChildren : parent.getChildren()) {
								if(datas.contains(indexDirectChildren.getData()))
									directChildren.add(indexDirectChildren);
							}
						}
						nextParents.addAll(parent.getChildren());
					}
				}
				children.addAll(directChildren);
				index = index + 1;
				parents = __inject__(CollectionHelper.class).isEmpty(nextParents) ? null : new ArrayList<>(nextParents);
			}			
		}
		return children;
	}
	
	public <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> PrimefacesHelper setTreeNodesSelected(TreeNode treeNode,Collection<NODE> nodes,Boolean selected) {
		if(treeNode!=null && __inject__(CollectionHelper.class).isNotEmpty(nodes)) {
			Collection<TreeNode> children = getTreeNodeChildren(treeNode, null,null);
			for(TreeNode index : children) {
				if(nodes.contains(index.getData()))
					index.setSelected(selected);
				if(Boolean.TRUE.equals(selected))
					setTreeNodesParentsExpanded(index,Boolean.TRUE);
				//else if(!index.getParent().isSelected())
				//	setTreeNodesParentsExpanded(index,Boolean.FALSE);
			}
		}
		return this;
	}
	
	public PrimefacesHelper setTreeNodesParentsExpanded(TreeNode treeNode,Boolean expanded) {
		TreeNode parent = treeNode.getParent();
		while(parent != null) {
			parent.setExpanded(expanded);
			parent = parent.getParent();
		}
		return this;
	}
	
	public <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> TreeNode buildTreeNode(Class<NODE> klass,Collection<NODE> selectedNodes) {
		Collection<NODE> nodes = __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(klass).read(new Properties().setIsPageable(Boolean.FALSE));
		ClassNameBuilder hierarchyClassNameBuilder = __inject__(ClassNameBuilder.class);
		hierarchyClassNameBuilder.setPackageName(klass.getPackage().getName()).setSimpleName(klass.getSimpleName());
		hierarchyClassNameBuilder.getSourceNamingModel(Boolean.TRUE).client().controller().entities();
		hierarchyClassNameBuilder.getDestinationNamingModel(Boolean.TRUE).client().controller().entities().setSuffix("Hierarchy");
		@SuppressWarnings("unchecked")
		Class<HIERARCHY> hierarchyClass = (Class<HIERARCHY>) __inject__(ClassHelper.class).getByName(hierarchyClassNameBuilder);
		Collection<HIERARCHY> hierarchies = __inject__(ControllerLayer.class).injectInterfaceClassFromEntityClass(hierarchyClass).read(new Properties().setIsPageable(Boolean.FALSE));
		
		ClassNameBuilder treeNodeListenerClassNameBuilder = __inject__(ClassNameBuilder.class);
		treeNodeListenerClassNameBuilder.setPackageName(klass.getPackage().getName()).setSimpleName(klass.getSimpleName());
		treeNodeListenerClassNameBuilder.getSourceNamingModel(Boolean.TRUE).client().controller().entities();
		treeNodeListenerClassNameBuilder.getDestinationNamingModel(Boolean.TRUE).client().controller().impl().setSuffix(TreeNodeListener.class.getSimpleName());
		@SuppressWarnings("unchecked")
		Class<TreeNodeListener<NODE,HIERARCHY>> treeNodeListenerClass = (Class<TreeNodeListener<NODE,HIERARCHY>>) __inject__(ClassHelper.class).getByName(treeNodeListenerClassNameBuilder);
		
		TreeNodeListener<NODE,HIERARCHY> listener = __inject__(treeNodeListenerClass);
		return buildTreeNode(nodes, hierarchies, selectedNodes, listener);
	}
	
	public <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> TreeNode buildTreeNode(Class<NODE> klass,org.cyk.utility.client.controller.data.DataIdentifiedByString master) {
		@SuppressWarnings("unchecked")
		Collection<NODE> selectedNodes = (Collection<NODE>) __inject__(FieldValueGetter.class).execute(master, __inject__(StringHelper.class)
				.getVariableNameFrom(klass.getSimpleName())+"s").getOutput();
		return buildTreeNode(klass, selectedNodes);
	}
	
	public TreeNode getTreeNodeOf(TreeNode root,Object node) {
		org.primefaces.model.TreeNode treeNode = null;
		if(root != null && node != null) {
			for(org.primefaces.model.TreeNode index : root.getChildren()) {
				if(index.getData().equals(node)) {
					treeNode = index;
				}else
					treeNode = getTreeNodeOf(index, node);
				if(treeNode != null)
					break;
			}
		}
		return treeNode;
	}
	
	public Collection<Object> getDatas(org.primefaces.model.TreeNode root,Integer isHavingNumberOfChildren) {
		Collection<Object> datas = new ArrayList<>();
		__addDatas__(root,datas,isHavingNumberOfChildren);
		return datas;
	}
	
	private void __addDatas__(org.primefaces.model.TreeNode root,Collection<Object> datas,Integer isHavingNumberOfChildren) {
		Object data = root.getData();
		if(data != null) {
			if(isHavingNumberOfChildren == null || root.getChildCount() == isHavingNumberOfChildren)
				datas.add(data);
		}
		if(root.getChildCount() > 0)
			for(org.primefaces.model.TreeNode index : root.getChildren())
				__addDatas__(index, datas,isHavingNumberOfChildren);
	}
	
	/**/
	
	private static final String SCRIPT_INSTRUCTION_COMPONENT_METHOD_CALL_FORMAT = "PF('%s').%s();";
}
