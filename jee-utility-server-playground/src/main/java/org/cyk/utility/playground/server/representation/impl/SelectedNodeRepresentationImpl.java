package org.cyk.utility.playground.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.SelectedNodeBusiness;
import org.cyk.utility.playground.server.persistence.entities.SelectedNode;
import org.cyk.utility.playground.server.representation.api.SelectedNodeRepresentation;
import org.cyk.utility.playground.server.representation.entities.SelectedNodeDto;
import org.cyk.utility.playground.server.representation.entities.SelectedNodeDtoCollection;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class SelectedNodeRepresentationImpl extends AbstractRepresentationEntityImpl<SelectedNode,SelectedNodeBusiness,SelectedNodeDto,SelectedNodeDtoCollection> implements SelectedNodeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
