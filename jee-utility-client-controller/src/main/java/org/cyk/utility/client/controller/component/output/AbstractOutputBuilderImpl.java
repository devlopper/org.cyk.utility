package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;

public abstract class AbstractOutputBuilderImpl<OUTPUT extends Output<?>> extends AbstractVisibleComponentBuilderImpl<OUTPUT> implements OutputBuilder<OUTPUT>,Serializable {
	private static final long serialVersionUID = 1L;

}
