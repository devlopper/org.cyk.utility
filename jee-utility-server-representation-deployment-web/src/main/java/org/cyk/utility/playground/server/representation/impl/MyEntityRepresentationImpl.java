package org.cyk.utility.playground.server.representation.impl;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.playground.server.business.api.MyEntityBusiness;
import org.cyk.utility.playground.server.persistence.entities.MyEntity;
import org.cyk.utility.playground.server.representation.api.MyEntityRepresentation;
import org.cyk.utility.playground.server.representation.entities.MyEntityDto;
import org.cyk.utility.playground.server.representation.entities.MyEntityDtoCollection;
import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@Singleton
public class MyEntityRepresentationImpl extends AbstractRepresentationEntityImpl<MyEntity,MyEntityBusiness,MyEntityDto,MyEntityDtoCollection> implements MyEntityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
