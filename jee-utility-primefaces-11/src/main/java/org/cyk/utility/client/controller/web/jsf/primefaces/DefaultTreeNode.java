package org.cyk.utility.client.controller.web.jsf.primefaces;

import java.io.Serializable;
import java.util.List;

import org.cyk.utility.client.controller.data.TreeNodeListener;
import org.primefaces.model.TreeNode;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class DefaultTreeNode<NODE> extends org.primefaces.model.DefaultTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private TreeNodeListener<NODE> listener;
	private NODE node;

	private Boolean __childrenHasBeenSet__;
	private Integer __childCount__;
	private Boolean __isLeaf__;
	
	@SuppressWarnings("unchecked")
	public DefaultTreeNode(Object data,TreeNodeListener<NODE> listener, TreeNode parent) {
		super(listener.getType((NODE) data), listener.getData((NODE) data), parent);
		setNode((NODE) data);
		setListener(listener);
	}
	
	public DefaultTreeNode(Object data,TreeNodeListener<NODE> listener) {
		this(data,listener, null);
	}
	
	@Override
    public List<TreeNode> getChildren() {
		/*if(!Boolean.TRUE.equals(__childrenHasBeenSet__) && listener!=null) {
			List<NODE> children = listener.getChildren(node);
			if(CollectionHelper.isNotEmpty(children)) {
				List<TreeNode> treeNodes = new ArrayList<>();
				for(NODE index : children) {
					DefaultTreeNode<NODE> treeNode = new DefaultTreeNode<NODE>(index, listener, this);
					treeNodes.add(treeNode);
				}
				setChildren(treeNodes);
				__childrenHasBeenSet__ = Boolean.TRUE;
			}
		}*/
        return super.getChildren();
    }

    @Override
    public int getChildCount() {
    	if(Boolean.TRUE.equals(__childrenHasBeenSet__))
    		return super.getChildCount();
    	if(__childCount__ != null)
    		return __childCount__;
    	__childCount__ = listener == null ? null : listener.getNumberOfChildren(node);
    	return __childCount__ == null ? 0 : __childCount__.intValue();
    }

    @Override
    public boolean isLeaf() {
    	if(Boolean.TRUE.equals(__childrenHasBeenSet__))
    		return super.isLeaf();
    	if(__isLeaf__ != null)
    		return __isLeaf__;
    	__isLeaf__ = listener == null ? null : listener.getIsLeaf(node);
    	return Boolean.TRUE.equals(__isLeaf__);
    }

}
