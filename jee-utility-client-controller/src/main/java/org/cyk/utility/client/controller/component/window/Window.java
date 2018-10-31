package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.theme.Theme;
import org.cyk.utility.client.controller.component.view.View;

public interface Window extends VisibleComponent {

	OutputStringText getTitle();
	Window setTitle(OutputStringText title);
	
	View getView();
	Window setView(View view);
	
	Theme getTheme();
	Window setTheme(Theme theme);
}
