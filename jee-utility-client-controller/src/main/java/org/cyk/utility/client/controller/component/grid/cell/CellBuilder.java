package org.cyk.utility.client.controller.component.grid.cell;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;

public interface CellBuilder extends VisibleComponentBuilder<Cell> {

	ViewBuilder getView();
	ViewBuilder getView(Boolean injectIfNull);
	CellBuilder setView(ViewBuilder view);
	
	OutputStringTextBuilder getText();
	OutputStringTextBuilder getText(Boolean injectIfNull);
	CellBuilder setText(OutputStringTextBuilder text);
	CellBuilder setTextValue(String textValue);
	
}
