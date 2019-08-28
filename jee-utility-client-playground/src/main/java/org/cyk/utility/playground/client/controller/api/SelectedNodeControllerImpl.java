package org.cyk.utility.playground.client.controller.api;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.client.controller.AbstractControllerEntityImpl;
import org.cyk.utility.playground.client.controller.entities.SelectedNode;

@ApplicationScoped
public class SelectedNodeControllerImpl extends AbstractControllerEntityImpl<SelectedNode> implements SelectedNodeController,Serializable {
	private static final long serialVersionUID = 1L;
	
}
