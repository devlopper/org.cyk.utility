package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.ParentTypeRepresentation;
import org.cyk.utility.server.representation.entities.ParentTypeDto;

@ApplicationScoped
public class ParentTypeRepresentationImpl extends AbstractRepresentationEntityImpl<ParentTypeDto> implements ParentTypeRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
