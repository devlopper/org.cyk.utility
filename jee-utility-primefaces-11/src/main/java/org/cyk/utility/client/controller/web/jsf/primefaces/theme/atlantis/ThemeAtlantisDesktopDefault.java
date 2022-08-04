package org.cyk.utility.client.controller.web.jsf.primefaces.theme.atlantis;

import org.cyk.utility.client.controller.component.tab.Tabs;
import org.cyk.utility.client.controller.component.theme.Theme;

@Deprecated
public interface ThemeAtlantisDesktopDefault extends Theme {

	Tabs getMenuTabs();
	Tabs getMenuTabs(Boolean injectIfNull);
	ThemeAtlantisDesktopDefault setMenuTabs(Tabs menuTabs);
	
}
