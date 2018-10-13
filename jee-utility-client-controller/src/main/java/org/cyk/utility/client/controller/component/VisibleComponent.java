package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.layout.LayoutCell;

public interface VisibleComponent extends Component {

	LayoutCell getLayoutCellComponent();
	LayoutCell getLayoutCellComponent(Boolean injectIfNull);
	VisibleComponent setLayoutCellComponent(LayoutCell layoutCellComponent);
	
}
