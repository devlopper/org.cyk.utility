package org.cyk.utility.client.controller.component.grid.cell;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.view.View;

public interface Cell extends VisibleComponent {
	
	View getView();
	Cell setView(View view);
	
}
