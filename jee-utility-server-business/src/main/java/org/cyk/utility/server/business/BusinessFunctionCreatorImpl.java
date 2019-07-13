package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

@Dependent
public class BusinessFunctionCreatorImpl extends AbstractBusinessFunctionCreatorImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override @Transactional
	public BusinessFunctionCreator execute() {
		return super.execute();
	}

}
