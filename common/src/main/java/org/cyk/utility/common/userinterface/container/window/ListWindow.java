package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

public class ListWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Boolean getIsAutomaticallySetForm() {
		return Boolean.FALSE;
	}
	
	@Override
	protected Boolean getIsAutomaticallySetDatatable() {
		return Boolean.TRUE;
	}
}
