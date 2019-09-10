package org.cyk.utility.playground.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.business.api.PersonBusiness;
import org.cyk.utility.playground.server.persistence.entities.Person;
import org.cyk.utility.playground.server.representation.api.PersonRepresentation;
import org.cyk.utility.playground.server.representation.entities.PersonDto;
import org.cyk.utility.playground.server.representation.entities.PersonDtoCollection;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class PersonRepresentationImpl extends AbstractRepresentationEntityImpl<Person,PersonBusiness,PersonDto,PersonDtoCollection> implements PersonRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
