package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.api.ParentTypeBusiness;
import org.cyk.utility.server.persistence.entities.ParentType;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.ParentTypeRepresentation;
import org.cyk.utility.server.representation.entities.ParentTypeDto;
import org.cyk.utility.server.representation.entities.ParentTypeDtoCollection;

@ApplicationScoped
public class ParentTypeRepresentationImpl extends AbstractRepresentationEntityImpl<ParentType,ParentTypeBusiness,ParentTypeDto,ParentTypeDtoCollection> implements ParentTypeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
