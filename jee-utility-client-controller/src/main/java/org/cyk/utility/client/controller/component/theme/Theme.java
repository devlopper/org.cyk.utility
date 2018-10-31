package org.cyk.utility.client.controller.component.theme;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.component.window.Window;

public interface Theme extends Objectable {

	ViewMap getViewMap();
	ViewMap getViewMap(Boolean injectIfNull);
	Theme setViewMap(ViewMap viewMap);
	Theme mapViews(Object...objects);
	
	ThemeTemplate getTemplate();
	Theme setTemplate(ThemeTemplate template);
	
	Theme process(Window window);
}
