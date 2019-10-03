package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.server.business.api.ChildBusiness;
import org.cyk.utility.server.persistence.entities.Child;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;
import org.cyk.utility.server.representation.api.ChildRepresentation;
import org.cyk.utility.server.representation.entities.ChildDto;
import org.cyk.utility.server.representation.entities.ChildDtoCollection;

@ApplicationScoped
public class ChildRepresentationImpl extends AbstractRepresentationEntityImpl<Child,ChildBusiness,ChildDto,ChildDtoCollection> implements ChildRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
