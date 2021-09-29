package org.cyk.utility.service.server;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.cyk.utility.rest.RequestExecutor;

public abstract class AbstractServiceImpl implements Serializable {

	@Inject protected RequestExecutor requestExecutor;
	
	protected Response execute(RequestExecutor.Request request) {
		return requestExecutor.execute(request);
	}
}