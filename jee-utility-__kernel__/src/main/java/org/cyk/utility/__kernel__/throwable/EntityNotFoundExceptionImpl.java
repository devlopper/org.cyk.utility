package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class EntityNotFoundExceptionImpl extends AbstractSystemClientExceptionImpl implements EntityNotFoundException,Serializable {
	private static final long serialVersionUID = 1L;

}
