package org.cyk.utility.client.controller;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.ResponseEntityDto;
import org.cyk.utility.__kernel__.system.action.SystemAction;

public class ControllerFunctionImpl extends AbstractControllerFunctionImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(SystemAction action) {
		
	}

	@Override
	protected void __executeRepresentation__() {
	}

	@Override
	protected ResponseEntityDto getResponseEntityDto(SystemAction action, Object representation, Response response) {
		return null;
	}

}
