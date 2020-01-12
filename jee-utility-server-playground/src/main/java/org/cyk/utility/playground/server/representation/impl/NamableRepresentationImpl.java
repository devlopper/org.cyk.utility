package org.cyk.utility.playground.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.representation.api.NamableRepresentation;
import org.cyk.utility.playground.server.representation.entities.NamableDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class NamableRepresentationImpl extends AbstractRepresentationEntityImpl<NamableDto> implements NamableRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
