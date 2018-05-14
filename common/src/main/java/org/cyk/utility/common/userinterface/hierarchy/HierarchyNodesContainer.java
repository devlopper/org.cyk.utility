package org.cyk.utility.common.userinterface.hierarchy;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.Columns;
import org.cyk.utility.common.userinterface.collection.Row;

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
		Row row = Row.instanciateOne(node.getPropertiesMap().getValue(),node.get__orderNumber__()
			,Columns.getProperty((Component) node.getPropertiesMap().getTopLevelContainer()),Boolean.FALSE);
		
		node.getPropertiesMap().setRow(row);
		node.addOneChild(row);
		
		return super.addNode(node);
	}
}
