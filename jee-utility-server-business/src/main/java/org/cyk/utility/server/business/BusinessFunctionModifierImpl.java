package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

@Dependent
public class BusinessFunctionModifierImpl extends AbstractBusinessFunctionModifierImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override @Transactional
	public BusinessFunctionModifier execute() {
		return super.execute();
	}
	
}
