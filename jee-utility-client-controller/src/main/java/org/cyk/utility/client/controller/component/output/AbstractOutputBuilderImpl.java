package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractInputOutputBuilderImpl;
import org.cyk.utility.client.controller.component.ComponentRole;

public abstract class AbstractOutputBuilderImpl<OUTPUT extends Output<VALUE>,VALUE> extends AbstractInputOutputBuilderImpl<OUTPUT,VALUE> implements OutputBuilder<OUTPUT,VALUE>,Serializable {
	private static final long serialVersionUID = 1L;

	public AbstractOutputBuilderImpl() {
		addRoles(ComponentRole.OUTPUT);
	}
	
}
