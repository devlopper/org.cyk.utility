package org.cyk.utility.server.representation.impl;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractDataLoaderImpl extends AbstractFunctionWithPropertiesAsInputImpl<Response> implements DataLoader,Serializable {
	private static final long serialVersionUID = 1L;

}
