package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

@Dependent
public class BusinessFunctionRemoverImpl extends AbstractBusinessFunctionRemoverImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override @Transactional
	public BusinessFunctionRemover execute() {
		return super.execute();
	}
	
}
