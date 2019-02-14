package org.cyk.utility.client.controller.web.jsf.primefaces.theme.atlantis;

import org.cyk.utility.client.controller.component.image.Image;
import org.cyk.utility.client.controller.component.tab.Tabs;
import org.cyk.utility.client.controller.component.theme.Theme;

public interface ThemeAtlantisDesktopDefault extends Theme {

	Image getLogo();
	Image getLogo(Boolean injectIfNull);
	ThemeAtlantisDesktopDefault setLogo(Image image);
	
	Tabs getMenuTabs();
	Tabs getMenuTabs(Boolean injectIfNull);
	ThemeAtlantisDesktopDefault setMenuTabs(Tabs menuTabs);
	
}
