package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class ServiceNotFoundExceptionImpl extends AbstractSystemClientExceptionImpl implements ServiceNotFoundException,Serializable {
	private static final long serialVersionUID = 1L;

}
