package org.cyk.jee.utility.server.representation.application;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@Singleton
public class MyEntityRepresentationImpl extends AbstractRepresentationEntityImpl<MyEntity,MyEntityBusiness,MyEntityDto> implements MyEntityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public MyEntity getPersistenceEntityByIdentifier(MyEntityDto entity) {
		return super.getPersistenceEntityByIdentifier(entity).setName(entity.getName());
	}
	

}
