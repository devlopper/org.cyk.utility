package org.cyk.utility.server.representation;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

public class MyRepresentationImpl implements MyRepresentation {

	@Override
	public Response getTime() {
		return Response.status(Response.Status.OK).entity(System.currentTimeMillis()+"").build();
	}
	
	@Override
	public Response getCollection() {
		Collection<MyEntityDto> list = new ArrayList<>();
		list.add(new MyEntityDto());
		list.add(new MyEntityDto());
		list.add(new MyEntityDto());
		//return Response.status(Response.Status.OK).entity(list).build();
		return Response.status(Response.Status.OK).entity(new GenericEntity<Collection<MyEntityDto>>(list) {}).build();
		
	}

	@Override
	public Response getOne() {
		return Response.status(Response.Status.OK).entity(new MyEntityDto()).build();
	}
}
