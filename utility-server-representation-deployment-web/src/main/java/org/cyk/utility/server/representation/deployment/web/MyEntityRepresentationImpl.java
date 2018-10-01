package org.cyk.utility.server.representation.deployment.web;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@Singleton
public class MyEntityRepresentationImpl extends AbstractRepresentationEntityImpl<MyEntity,MyEntityBusiness,MyEntityDto,MyEntityDtoCollection> implements MyEntityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
}
