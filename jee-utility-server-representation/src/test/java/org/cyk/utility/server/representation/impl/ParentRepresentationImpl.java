package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.ParentRepresentation;
import org.cyk.utility.server.representation.entities.ParentDto;

@ApplicationScoped
public class ParentRepresentationImpl extends AbstractRepresentationEntityImpl<ParentDto> implements ParentRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
