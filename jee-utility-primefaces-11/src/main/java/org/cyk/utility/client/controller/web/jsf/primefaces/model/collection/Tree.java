package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObjectAjaxable;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.output.OutputText;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Tree extends AbstractObjectAjaxable implements Serializable {

	private TreeNode[] selection;
	private TreeNode value;
	private org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.TreeNode treeNode;
	private String filterBy,filterMatchMode,filterMode,selectionMode,requiredMessage;
	private Boolean required;
	private CommandButton processSelectionCommandButton;
	private Boolean propagateSelectionDown,propagateSelectionUp;
	private OutputText titleOutputText;
	
	@SuppressWarnings("unchecked")
	public Object getDataKey(Tree tree,Object data) {
		return ((Listener<Object>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getDataKey(this,data);
	}
	
	@SuppressWarnings("unchecked")
	public Tree instantiateProcessSelectionCommandButton() {
		processSelectionCommandButton = CommandButton.build(((Listener<Object>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getProcessSelectionCommandButtonArguments(this));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Tree computeNodes() {
		((Listener<Object>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).computeNodes(this);
		return this;
	}
	
	public TreeNode getParentNodeOfData(Object data) {
		if(value == null ||  data == null)
			return null;
		return getParentNodeOfData(value.getChildren(), data);
	}
	
	@SuppressWarnings("unchecked")
	private TreeNode getParentNodeOfData(Collection<TreeNode> nodes,Object data) {
		if(CollectionHelper.isEmpty(nodes) || data == null)
			return null;
		for(TreeNode index : nodes) {
			if(Boolean.TRUE.equals(((Listener<Object>)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).isParent(index.getData(), data)))
				return index;
			TreeNode node = getParentNodeOfData(index.getChildren(),data);
			if(node != null)
				return node;
		}
		return null;
	}
	
	public TreeNode getNodeByDataSystemIdentifier(String identifier) {
		return getNodeByDataSystemIdentifier(value, identifier);
	}
	
	private TreeNode getNodeByDataSystemIdentifier(TreeNode root,String identifier) {
		if(StringHelper.isBlank(identifier) || root == null || CollectionHelper.isEmpty(root.getChildren()))
			return null;
		for(TreeNode child : root.getChildren()) {
			if(identifier.equals(FieldHelper.readSystemIdentifier(child.getData())))
				return child;
			TreeNode node = getNodeByDataSystemIdentifier(child,identifier);
			if(node != null)
				return node;
		}
		return null;
	}
	
	public Tree addNodeFromData(Object data,String parentDataIdentifier) {
		TreeNode parent = getNodeByDataSystemIdentifier(parentDataIdentifier);
		if(parent == null)
			parent = value;
		new DefaultTreeNode(data, parent);
		return this;
	}
	
	public Tree addNodeFromData(Object data) {
		if(data == null)
			return this;
		TreeNode parent = getParentNodeOfData(data);
		if(parent == null)
			parent = value;
		new DefaultTreeNode(data, parent);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Collection<T> getSelectionDatas(Class<T> klass) {
		if(ArrayHelper.isEmpty(selection))
			return null;
		return (Collection<T>) CollectionHelper.listOf(selection).stream().map(x -> x.getData()).collect(Collectors.toList());
	}
	
	/**/
	
	public static final String FIELD_SELECTION = "selection";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_TREE_NODE = "treeNode";
	public static final String FIELD_PROCESS_SELECTION_COMMAND_BUTTON = "processSelectionCommandButton";
	public static final String FIELD_FILTER_BY = "filterBy";
	public static final String FIELD_FILTER_MATCH_MODE = "filterMatchMode";
	public static final String FIELD_FILTER_MODE = "filterMode";
	public static final String FIELD_SELECTION_MODE = "selectionMode";
	public static final String FIELD_REQUIRED_MESSAGE = "requiredMessage";
	public static final String FIELD_REQUIRED = "required";
	public static final String FIELD_PROPAGATE_SELECTION_DOWN = "propagateSelectionDown";
	public static final String FIELD_PROPAGATE_SELECTION_UP = "propagateSelectionUp";
	
	public static class ConfiguratorImpl extends AbstractObjectAjaxable.AbstractConfiguratorImpl<Tree> implements Serializable {

		@Override
		public void configure(Tree tree, Map<Object, Object> arguments) {
			super.configure(tree, arguments);
			if(tree.value == null)
				tree.value = new DefaultTreeNode();
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_PROCESS_SELECTION_COMMAND_BUTTON_INSTANTIABLE)))
				tree.instantiateProcessSelectionCommandButton();
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_UPDATABLE_AFTER_SELECTION_PROCESSING)) && tree.getProcessSelectionCommandButton() != null)
				tree.getProcessSelectionCommandButton().addUpdates(":form:"+tree.getIdentifier());
			if(tree.treeNode == null)
				tree.treeNode = org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.TreeNode.build();
			if(tree.titleOutputText == null) {
				String titleValue = (String) MapHelper.readByKey(arguments, FIELD_TITLE_VALUE);
				if(StringHelper.isNotBlank(titleValue))
					tree.titleOutputText = OutputText.buildFromValue(titleValue);
			}
			
		}
		
		@Override
		protected String __getTemplate__(Tree tree, Map<Object, Object> arguments) {
			return "/collection/tree/default.xhtml";
		}
		
		@Override
		protected Class<Tree> __getClass__() {
			return Tree.class;
		}
		
		public static final String FIELD_PROCESS_SELECTION_COMMAND_BUTTON_INSTANTIABLE = "PROCESS_SELECTION_COMMAND_BUTTON_INSTANTIABLE";
		public static final String FIELD_UPDATABLE_AFTER_SELECTION_PROCESSING = "UPDATABLE_AFTER_SELECTION_PROCESSING";
		public static final String FIELD_TITLE_VALUE = "TITLE_VALUE";
	}
	
	/**/
	
	/**/
	
	public static interface Listener<DATA> {
		
		Object getDataKey(Tree tree,DATA data);
		void processSelection(Tree tree,Collection<DATA> datas);		
		Map<Object,Object> getProcessSelectionCommandButtonArguments(Tree tree);
		default Boolean isParent(DATA data1,DATA data2) {
			throw new RuntimeException("is parent not yet implemented");
		}
		
		void computeNodes(Tree tree);
		
		/**/
		public static abstract class AbstractImpl<DATA> extends AbstractObject implements Listener<DATA>,Serializable {
			@Override
			public Object getDataKey(Tree tree, DATA data) {
				return data == null ? null : FieldHelper.readSystemIdentifier(data);
			}
			
			@Override
			public void processSelection(Tree tree,Collection<DATA> datas) {}
			
			@Override
			public void computeNodes(Tree tree) {
				throw new RuntimeException("compute nodes not yet implemented");
			}
			
			@Override
			public Map<Object, Object> getProcessSelectionCommandButtonArguments(Tree tree) {
				Map<Object, Object> arguments = new HashMap<>();
				arguments.put(CommandButton.FIELD_VALUE,"Enregistrer");
				arguments.put(CommandButton.FIELD_ICON,"fa fa-floppy-o");
				arguments.put(CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION);
				arguments.put(CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES,List.of(RenderType.GROWL));
				arguments.put(CommandButton.FIELD_LISTENER, new CommandButton.Listener.AbstractImpl() {
					@SuppressWarnings("unchecked")
					@Override
					protected Object __runExecuteFunction__(AbstractAction action) {
						if(ArrayHelper.isNotEmpty(tree.getSelection())) {
							Collection<DATA> datas = new ArrayList<>();
							for(Object index : tree.getSelection()) {
								if(((TreeNode)index).getData() != null)
									datas.add((DATA) ((TreeNode)index).getData());
							}
							processSelection(tree, datas);
						}
						return null;
					}
				});
				return arguments;
			}
			
			/**/
			
			public static class DefaultImpl extends AbstractImpl<Object> implements Serializable {
				public static final Tree.Listener<Object> INSTANCE = new DefaultImpl();
			}
		}
	}
	
	public static Tree build(Map<Object,Object> arguments) {
		return Builder.build(Tree.class,arguments);
	}
	
	public static Tree build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(Tree.class, new ConfiguratorImpl());
	}
}