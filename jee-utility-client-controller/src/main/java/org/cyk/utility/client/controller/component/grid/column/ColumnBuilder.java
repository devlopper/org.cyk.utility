package org.cyk.utility.client.controller.component.grid.column;

import org.cyk.utility.client.controller.component.grid.DimensionBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilderMap;

public interface ColumnBuilder extends DimensionBuilder<Column> {

	ViewBuilderMap getViewMap();
	ViewBuilderMap getViewMap(Boolean injectIfNull);
	ColumnBuilder setViewMap(ViewBuilderMap viewMap);
	ColumnBuilder setViews(Object...keyValues);
	
	OutputStringTextBuilder getHeaderText();
	OutputStringTextBuilder getHeaderText(Boolean injectIfNull);
	ColumnBuilder setHeaderText(OutputStringTextBuilder headerText);
	ColumnBuilder setHeaderTextValue(String headerTextValue);
	
	OutputStringTextBuilder getFooterText();
	OutputStringTextBuilder getFooterText(Boolean injectIfNull);
	ColumnBuilder setFooterText(OutputStringTextBuilder footerText);
	ColumnBuilder setFooterTextValue(String footerTextValue);
	
	/**/
	
	String VIEW_HEADER_IDENTIFIER = "header";
	String VIEW_BODY_IDENTIFIER = "body";
	String VIEW_FOOTER_IDENTIFIER = "footer";
}
