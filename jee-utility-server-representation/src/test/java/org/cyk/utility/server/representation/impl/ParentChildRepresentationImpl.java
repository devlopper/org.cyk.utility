package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.ParentChildRepresentation;
import org.cyk.utility.server.representation.entities.ParentChildDto;

@ApplicationScoped
public class ParentChildRepresentationImpl extends AbstractRepresentationEntityImpl<ParentChildDto> implements ParentChildRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
