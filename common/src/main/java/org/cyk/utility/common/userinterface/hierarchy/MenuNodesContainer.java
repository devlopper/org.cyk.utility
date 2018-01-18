package org.cyk.utility.common.userinterface.hierarchy;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.userinterface.command.MenuNode;

@Getter @Setter @Accessors(chain=true)
public class MenuNodesContainer extends AbstractHierarchyNodesContainer<MenuNode> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<MenuNode> getNodeClass() {
		return MenuNode.class; 
	}
	
}
