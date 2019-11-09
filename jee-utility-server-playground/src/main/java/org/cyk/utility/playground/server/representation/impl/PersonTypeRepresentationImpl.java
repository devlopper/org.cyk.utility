package org.cyk.utility.playground.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.representation.api.PersonTypeRepresentation;
import org.cyk.utility.playground.server.representation.entities.PersonTypeDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class PersonTypeRepresentationImpl extends AbstractRepresentationEntityImpl<PersonTypeDto> implements PersonTypeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
