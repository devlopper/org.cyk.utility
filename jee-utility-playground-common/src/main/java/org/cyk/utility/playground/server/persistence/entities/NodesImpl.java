package org.cyk.utility.playground.server.persistence.entities;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.playground.server.persistence.entities.Node;
import org.cyk.utility.playground.server.persistence.entities.Nodes;

@Dependent
public class NodesImpl extends AbstractCollectionInstanceImpl<Node> implements Nodes,Serializable {
	private static final long serialVersionUID = 1L;

}
