package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.ResponseEntityDto;
import org.cyk.utility.system.action.SystemAction;

public class ControllerFunctionImpl extends AbstractControllerFunctionImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(SystemAction action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Response __actWithRepresentationInstanceOfRepresentationEntity__(SystemAction action,@SuppressWarnings("rawtypes") RepresentationEntity representation, Collection<?> dataTransferObjects) {
		return null;
	}

	@Override
	protected ResponseEntityDto getResponseEntityDto(SystemAction action, Object representation, Response response) {
		// TODO Auto-generated method stub
		return null;
	}

}
