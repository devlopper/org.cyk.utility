package org.cyk.jee.utility.server.representation.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.AbstractRepresentationEntityImpl;

@Singleton
public class MyEntityRepresentationImpl extends AbstractRepresentationEntityImpl<MyEntity,MyEntityBusiness,MyEntityDto> implements MyEntityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;
/*
	@Override
	public Response createOne(MyEntityDto dto) {
		__inject__(MyEntityBusiness.class).create(dto.getPersistenceEntity());
		return Response.ok().status(Response.Status.CREATED).build();
	}

	@Override
	public Collection<MyEntityDto> getMany() {
		Collection<MyEntityDto> dtos = new ArrayList<>();
		for(MyEntity index : __inject__(MyEntityBusiness.class).findMany())
			dtos.add(new MyEntityDto(index));
		return dtos;
	}
	*/
	

}
