package org.cyk.utility.client.controller.component.column;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentImpl;
import org.cyk.utility.client.controller.component.output.OutputStringText;

public class ColumnImpl extends AbstractVisibleComponentImpl implements Column,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringText header;
	
	@Override
	public OutputStringText getHeader() {
		return header;
	}

	@Override
	public Column setHeader(OutputStringText header) {
		this.header = header;
		return this;
	}

}
