package org.cyk.utility.client.controller.component.column;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;

public class ColumnBuilderImpl extends AbstractVisibleComponentBuilderImpl<Column> implements ColumnBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringTextBuilder header;
	
	@Override
	protected void __execute__(Column column) {
		super.__execute__(column);
		OutputStringTextBuilder header = getHeader();
		if(header!=null) {
			column.setHeader(header.execute().getOutput());
		}
	}
	
	@Override
	public OutputStringTextBuilder getHeader() {
		return header;
	}

	@Override
	public OutputStringTextBuilder getHeader(Boolean injectIfNull) {
		return (OutputStringTextBuilder) __getInjectIfNull__(FIELD_HEADER, injectIfNull);
	}

	@Override
	public ColumnBuilder setHeader(OutputStringTextBuilder header) {
		this.header = header;
		return this;
	}

	@Override
	public ColumnBuilder setHeaderValue(String headerValue) {
		getHeader(Boolean.TRUE).setValue(headerValue);
		return this;
	}

	public static final String FIELD_HEADER = "header";
}
