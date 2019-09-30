package org.cyk.utility.client.controller.web.jsf.primefaces.tag;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString;
import org.cyk.utility.client.controller.data.hierarchy.Hierarchy;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.primefaces.model.TreeNode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Dependent
@Getter @Setter @NoArgsConstructor
public class InputTree extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private TreeNode root;
	private TreeNode[] selected;
	
	public InputTree(TreeNode root) {
		this.root = root;
	}
	
	public <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> void select(Collection<NODE> nodes) {
		__select__(nodes, Boolean.TRUE);
	}
	
	public <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> void unselect(Collection<NODE> nodes) {
		__select__(nodes, Boolean.FALSE);
	}
	
	public <NODE extends DataIdentifiedByString,HIERARCHY extends Hierarchy<NODE>> void __select__(Collection<NODE> nodes,Boolean isSelected) {
		if(CollectionHelper.isNotEmpty(nodes)) {
			PrimefacesHelper primefacesHelper = __inject__(PrimefacesHelper.class);
			primefacesHelper.setTreeNodesSelected(root, nodes,isSelected);
			Collection<TreeNode> treeNodes = primefacesHelper.getTreeNodeChildren(root, null,nodes);
			if(CollectionHelper.isNotEmpty(treeNodes)) {
				if(Boolean.TRUE.equals(isSelected))
					setSelected(ArrayUtils.addAll(getSelected(), treeNodes.toArray(new TreeNode[] {})));
				else {
					//setSelected(ArrayUtils.removeElements(getSelected(), treeNodes.toArray(new TreeNode[] {})));
					for(TreeNode index : treeNodes)
						setSelected(ArrayUtils.removeAllOccurences(getSelected(), index));
				}
			}
		}
	}
}
