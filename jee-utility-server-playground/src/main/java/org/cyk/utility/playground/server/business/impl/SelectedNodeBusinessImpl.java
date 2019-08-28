package org.cyk.utility.playground.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.SelectedNodeBusiness;
import org.cyk.utility.playground.server.persistence.api.SelectedNodePersistence;
import org.cyk.utility.playground.server.persistence.entities.SelectedNode;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class SelectedNodeBusinessImpl extends AbstractBusinessEntityImpl<SelectedNode,SelectedNodePersistence> implements SelectedNodeBusiness,Serializable {
	private static final long serialVersionUID = 1L;

}
