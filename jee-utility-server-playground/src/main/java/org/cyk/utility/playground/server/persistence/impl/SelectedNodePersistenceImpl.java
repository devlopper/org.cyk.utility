package org.cyk.utility.playground.server.persistence.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.persistence.api.SelectedNodePersistence;
import org.cyk.utility.playground.server.persistence.entities.SelectedNode;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class SelectedNodePersistenceImpl extends AbstractPersistenceEntityImpl<SelectedNode> implements SelectedNodePersistence,Serializable {
	private static final long serialVersionUID = 1L;

}
