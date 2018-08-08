package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;

@Singleton
public class MyEntityRepresentationImpl extends AbstractRepresentationEntityImpl<MyEntity,MyEntityPersistence> implements MyEntityRepresentation,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response create(MyEntityDto dto) {
		__inject__(MyEntityBusiness.class).create(dto.getPersistenceEntity());
		return null;
	}

	@Override
	public Collection<MyEntityDto> getAll() {
		Collection<MyEntityDto> dtos = new ArrayList<>();
		for(MyEntity index : __inject__(MyEntityBusiness.class).findMany())
			dtos.add(new MyEntityDto(index));
		return dtos;
	}

	

}
