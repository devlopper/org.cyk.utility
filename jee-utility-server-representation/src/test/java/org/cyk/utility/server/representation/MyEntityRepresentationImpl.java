package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.inject.Singleton;

@Singleton
public class MyEntityRepresentationImpl extends AbstractRepresentationEntityImpl<MyEntity,MyEntityBusiness,MyEntityDto,MyEntityDtoCollection> implements MyEntityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

}
