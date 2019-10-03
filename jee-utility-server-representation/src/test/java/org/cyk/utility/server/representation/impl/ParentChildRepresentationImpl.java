package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.api.ParentChildBusiness;
import org.cyk.utility.server.persistence.entities.ParentChild;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.ParentChildRepresentation;
import org.cyk.utility.server.representation.entities.ParentChildDto;
import org.cyk.utility.server.representation.entities.ParentChildDtoCollection;

@ApplicationScoped
public class ParentChildRepresentationImpl extends AbstractRepresentationEntityImpl<ParentChild,ParentChildBusiness,ParentChildDto,ParentChildDtoCollection> implements ParentChildRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
