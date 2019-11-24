package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

public class DataLoaderImpl extends AbstractDataLoaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Response load() {
		throw ThrowableHelper.NOT_YET_IMPLEMENTED;
	}

}
