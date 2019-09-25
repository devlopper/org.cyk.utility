package org.cyk.utility.__kernel__.system.action;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

@Dependent
public class SystemActionSelectImpl extends AbstractSystemActionImpl implements SystemActionSelect, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIdentifier(IDENTIFIER);
	}
	
}
