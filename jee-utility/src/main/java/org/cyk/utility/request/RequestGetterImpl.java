package org.cyk.utility.request;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent
public class RequestGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements RequestGetter,Serializable {
	private static final long serialVersionUID = 1L;

}
