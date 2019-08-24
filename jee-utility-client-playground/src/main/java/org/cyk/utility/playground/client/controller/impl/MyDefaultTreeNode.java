package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.data.DataIdentifiedByString;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.playground.client.controller.api.NodeController;
import org.cyk.utility.playground.client.controller.entities.Node;
import org.cyk.utility.server.persistence.query.filter.FilterDto;
import org.cyk.utility.value.ValueUsageType;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class MyDefaultTreeNode extends DefaultTreeNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataIdentifiedByString data;
	private Boolean __isChildrenHasBeenSet__;
	private Integer __numberOfChildren__;
	
	public MyDefaultTreeNode(String type, Object data, TreeNode parent) {
		super(type, data, parent);
		this.data = (DataIdentifiedByString) data;
		//System.out.println("MyDefaultTreeNode.MyDefaultTreeNode()");
	}

	@Override
	public int getChildCount() {
		__fetchChildren__();
		return __numberOfChildren__ == null ? 0 : __numberOfChildren__;
	}
	
	@Override
	public List<TreeNode> getChildren() {
		__fetchChildren__();
		return super.getChildren();
	}
	
	@Override
	public boolean isLeaf() {
		__fetchChildren__();
		return super.isLeaf();
	}
	
	/**/
	
	private void __fetchChildren__() {
		if(!Boolean.TRUE.equals(__isChildrenHasBeenSet__)) {
			__isChildrenHasBeenSet__= Boolean.TRUE;
			data = (DataIdentifiedByString) getData();
			FilterDto filter = new FilterDto();
			filter.setKlass(org.cyk.utility.playground.server.persistence.entities.Node.class);
			filter.addField(org.cyk.utility.playground.server.persistence.entities.Node.FIELD_PARENTS, Arrays.asList(data.getIdentifier()),ValueUsageType.SYSTEM);
			Properties properties = new Properties().setFilters(filter).setIsPageable(Boolean.FALSE);
			Collection<Node> children = DependencyInjection.inject(NodeController.class).read(properties);
			if(DependencyInjection.inject(CollectionHelper.class).isNotEmpty(children)) {
				Response response = (Response) properties.getResponse();	
				__numberOfChildren__ = DependencyInjection.inject(NumberHelper.class).getInteger(response.getHeaderString("X-Total-Count"));
				ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();
				for(Node child : children) {
					DefaultTreeNode treeNode = new MyDefaultTreeNode("T",child,null);
					treeNodes.add(treeNode);
				}
				super.getChildren().addAll(treeNodes);
			}	
		}
	}
	
}
