package org.cyk.utility.playground.client.controller.api;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.client.controller.AbstractControllerEntityImpl;
import org.cyk.utility.playground.client.controller.entities.NodeHierarchy;

@ApplicationScoped
public class NodeHierarchyControllerImpl extends AbstractControllerEntityImpl<NodeHierarchy> implements NodeHierarchyController,Serializable {
	private static final long serialVersionUID = 1L;
	
}
