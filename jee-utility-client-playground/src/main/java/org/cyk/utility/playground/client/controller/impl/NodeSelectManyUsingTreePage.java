package org.cyk.utility.playground.client.controller.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.data.TreeNodeListener;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.playground.client.controller.api.NodeController;
import org.cyk.utility.playground.client.controller.entities.Node;
import org.cyk.utility.server.persistence.query.filter.FilterDto;
import org.cyk.utility.value.ValueUsageType;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NodeSelectManyUsingTreePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private TreeNode root;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		root = new DefaultTreeNode();
		root.setExpanded(Boolean.TRUE);
		FilterDto filter = new FilterDto();
		filter.setKlass(org.cyk.utility.playground.server.persistence.entities.Node.class);
		filter.addField(org.cyk.utility.playground.server.persistence.entities.Node.FIELD_PARENTS, null);
		Collection<Node> nodes = __inject__(NodeController.class).read(new Properties().setFilters(filter).setIsPageable(Boolean.FALSE));
		if(__inject__(CollectionHelper.class).isNotEmpty(nodes))
			for(Node index : nodes) {
				new MyDefaultTreeNode("T", index, root);
				/*TreeNode node = new org.cyk.utility.client.controller.web.jsf.primefaces.DefaultTreeNode<Node>(index, new TreeNodeListener<Node>() {
					
					@Override
					public String getType(Node node) {
						// TODO Auto-generated method stub
						return "T";
					}

					@Override
					public Integer getNumberOfChildren(Node node) {
						FilterDto filter = new FilterDto();
						filter.setKlass(org.cyk.utility.playground.server.persistence.entities.Node.class);
						filter.addField(org.cyk.utility.playground.server.persistence.entities.Node.FIELD_PARENTS, Arrays.asList(node.getIdentifier()));
						Long numberOfChildren = __inject__(NodeController.class).count(new Properties().setFilters(filter).setIsPageable(Boolean.FALSE));
						System.out.println(
								"NodeSelectManyUsingTreePage.__listenPostConstruct__().new TreeNodeListener() {...}.getNumberOfChildren() : "+node+" , COUNT : "+numberOfChildren);
						return numberOfChildren == null ? 0 : numberOfChildren.intValue();
					}

					@Override
					public List<Node> getChildren(Node node) {
						FilterDto filter = new FilterDto();
						filter.setKlass(org.cyk.utility.playground.server.persistence.entities.Node.class);
						filter.addField(org.cyk.utility.playground.server.persistence.entities.Node.FIELD_PARENTS, Arrays.asList(node.getIdentifier()));
						Collection<Node> children = __inject__(NodeController.class).read(new Properties().setFilters(filter).setIsPageable(Boolean.FALSE));
						System.out.println(
								"NodeSelectManyUsingTreePage.__listenPostConstruct__().new TreeNodeListener() {...}.getChildren() : "+node+" : CHILDREN : "+children);
						return (List<Node>) children;
						
						//return null;
					}
				}, root);
				*/
				//TreeNode node = instantiateTreeNode(index,selectedNodes,listener, root);
				//buildTreeNode(index,nodes,hierarchies,selectedNodes,listener,node);
			}
		//__injectPrimefacesHelper__().buildTreeNode(nodes, hierarchies, selectedNodes, listener)
	}
	
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Select Many Using Tree";
	}
}
