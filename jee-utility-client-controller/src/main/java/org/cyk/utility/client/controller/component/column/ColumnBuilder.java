package org.cyk.utility.client.controller.component.column;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;

public interface ColumnBuilder extends VisibleComponentBuilder<Column> {

	OutputStringTextBuilder getHeader();
	OutputStringTextBuilder getHeader(Boolean injectIfNull);
	ColumnBuilder setHeader(OutputStringTextBuilder header);
	ColumnBuilder setHeaderValue(String headerValue);
	
}
