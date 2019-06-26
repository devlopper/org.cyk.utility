package org.cyk.utility.client.controller.component.grid.column;

import java.util.Collection;

import org.cyk.utility.client.controller.component.grid.DimensionBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilderMap;
import org.cyk.utility.string.Strings;

public interface ColumnBuilder extends DimensionBuilder<Column> {

	Strings getFieldNameStrings();
	Strings getFieldNameStrings(Boolean injectIfNull);
	ColumnBuilder setFieldNameStrings(Strings fieldNameStrings);
	ColumnBuilder addFieldNameStrings(Collection<String> fieldNameStrings);
	ColumnBuilder addFieldNameStrings(String...fieldNameStrings);
	
	ViewBuilderMap getViewMap();
	ViewBuilderMap getViewMap(Boolean injectIfNull);
	ColumnBuilder setViewMap(ViewBuilderMap viewMap);
	ColumnBuilder setViews(Object...keyValues);
	ViewBuilder getView(String key);
	
	OutputStringTextBuilder getHeaderText();
	OutputStringTextBuilder getHeaderText(Boolean injectIfNull);
	ColumnBuilder setHeaderText(OutputStringTextBuilder headerText);
	ColumnBuilder setHeaderTextValue(String headerTextValue);
	
	ViewBuilder getBodyView();
	ViewBuilder getBodyView(Boolean injectIfNull);
	ColumnBuilder setBodyView(ViewBuilder bodyView);
	
	OutputStringTextBuilder getFooterText();
	OutputStringTextBuilder getFooterText(Boolean injectIfNull);
	ColumnBuilder setFooterText(OutputStringTextBuilder footerText);
	ColumnBuilder setFooterTextValue(String footerTextValue);
	
	Object getWidth();
	ColumnBuilder setWidth(Object width);
	
	
}
