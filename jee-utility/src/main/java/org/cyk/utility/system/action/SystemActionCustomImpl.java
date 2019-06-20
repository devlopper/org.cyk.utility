package org.cyk.utility.system.action;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class SystemActionCustomImpl extends AbstractSystemActionImpl implements SystemActionCustom, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIdentifier(IDENTIFIER);
	}
	
}
