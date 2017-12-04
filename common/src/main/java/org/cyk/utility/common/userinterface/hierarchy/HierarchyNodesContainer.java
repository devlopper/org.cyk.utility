package org.cyk.utility.common.userinterface.hierarchy;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.collection.DataTable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class HierarchyNodesContainer extends AbstractHierarchyNodesContainer<HierarchyNode> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<HierarchyNode> getNodeClass() {
		return HierarchyNode.class;
	}
	
	public HierarchyNodesContainer getParent() {
		return (HierarchyNodesContainer) super.getParent();
	}
	
	@Override
	public HierarchyNode addNode(HierarchyNode node) {
		if(node.getPropertiesMap().getValue() == null)
			node.getPropertiesMap().setValue(node.getLabel().getPropertiesMap().getValue());
		DataTable.Row row = new DataTable.Row()._setObject(node.getPropertiesMap().getValue());
		row.getPropertiesMap().setValue(node.getPropertiesMap().getValue());
		node.getPropertiesMap().setRow(row);
		return super.addNode(node);
	}
}
