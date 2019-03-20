package org.cyk.utility.client.controller.web.jsf.primefaces.component;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.tree.AbstractTreeImpl;
import org.cyk.utility.client.controller.component.tree.Tree;
import org.cyk.utility.client.controller.component.tree.TreeRenderType;
import org.cyk.utility.client.controller.component.tree.TreeRenderTypeOrganigram;
import org.cyk.utility.client.controller.web.jsf.primefaces.annotation.Primefaces;
import org.cyk.utility.hierarchy.HierarchyNode;
import org.cyk.utility.hierarchy.HierarchyNodeData;
import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.model.OrganigramNode;

import lombok.Getter;
import lombok.Setter;

@Primefaces
public class TreeImpl extends AbstractTreeImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter private Object __selection__;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSelectedNodeAs(Class<T> aClass) {
		T node = null;
		if(OrganigramNode.class.equals(aClass))
			// re-evaluate selection - might be a differenct object instance if viewstate serialization is enabled
			node = (T) OrganigramHelper.findTreeNode((OrganigramNode) getTargetModel(), (OrganigramNode) get__selection__());
		else
			System.err.println("Getting tree node of type "+aClass+" not yet handled");
		return node;
	}
	
	@Override
	public Object getSelectedNodeDataValue() {
		Object value = null;
		OrganigramNode node = getSelectedNodeAs(OrganigramNode.class);
		if(node!=null) {
			Object data = node.getData();
			if(data instanceof HierarchyNodeData) {
				value = ((HierarchyNodeData)data).getValue();
			}
		}
		return value;
	}
	
	@Override
	public Tree addData(Object data) {
		TreeRenderType renderType = getRenderType();
		if(renderType instanceof TreeRenderTypeOrganigram) {			
			OrganigramNode selection = getSelectedNodeAs(OrganigramNode.class);
	        __inject__(OrganigramNodeBuilder.class).setHierarchyNode(__inject__(HierarchyNode.class).setData(data)).setParent(selection).execute().getOutput();
		}else
			System.err.println("adding tree node for render type "+renderType+" not yet handled");
		return this;
	}
	
	@Override
	public Tree removeData(Object data) {
		OrganigramNode selection = null;
		if(data == null)
			selection = getSelectedNodeAs(OrganigramNode.class);
		if(selection!=null)
			selection.getParent().getChildren().remove(selection);
    	return this;
	}
	
}
