package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.server.representation.entities.MyEntityDto;

@ApplicationScoped
public class MyEntityRepresentationImpl extends AbstractRepresentationEntityImpl<MyEntityDto> implements MyEntityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
