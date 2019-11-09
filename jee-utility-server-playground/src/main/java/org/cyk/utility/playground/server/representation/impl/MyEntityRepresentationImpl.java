package org.cyk.utility.playground.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.playground.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.playground.server.representation.entities.MyEntityDto;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@ApplicationScoped
public class MyEntityRepresentationImpl extends AbstractRepresentationEntityImpl<MyEntityDto> implements MyEntityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
