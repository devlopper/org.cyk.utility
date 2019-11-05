package org.cyk.utility.playground.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.PersonTypeBusiness;
import org.cyk.utility.playground.server.persistence.entities.PersonType;
import org.cyk.utility.playground.server.representation.api.PersonTypeRepresentation;
import org.cyk.utility.playground.server.representation.entities.PersonTypeDto;
import org.cyk.utility.playground.server.representation.entities.PersonTypeDtoCollection;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class PersonTypeRepresentationImpl extends AbstractRepresentationEntityImpl<PersonType,PersonTypeBusiness,PersonTypeDto,PersonTypeDtoCollection> implements PersonTypeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
