package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.api.ParentBusiness;
import org.cyk.utility.server.persistence.entities.Parent;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.ParentRepresentation;
import org.cyk.utility.server.representation.entities.ParentDto;
import org.cyk.utility.server.representation.entities.ParentDtoCollection;

@ApplicationScoped
public class ParentRepresentationImpl extends AbstractRepresentationEntityImpl<Parent,ParentBusiness,ParentDto,ParentDtoCollection> implements ParentRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
