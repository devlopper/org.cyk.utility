package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractInputOutputImpl;

public abstract class AbstractOutputImpl<T> extends AbstractInputOutputImpl<T> implements Output<T>,Serializable {
	private static final long serialVersionUID = 1L;

}
