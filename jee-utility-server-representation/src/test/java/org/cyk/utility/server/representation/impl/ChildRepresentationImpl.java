package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.ChildRepresentation;
import org.cyk.utility.server.representation.entities.ChildDto;

@ApplicationScoped
public class ChildRepresentationImpl extends AbstractRepresentationEntityImpl<ChildDto> implements ChildRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
