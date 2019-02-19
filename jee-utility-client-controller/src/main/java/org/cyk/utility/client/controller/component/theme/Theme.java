package org.cyk.utility.client.controller.component.theme;

import org.cyk.utility.client.controller.Objectable;
import org.cyk.utility.client.controller.component.image.Image;
import org.cyk.utility.client.controller.component.image.ImageMap;
import org.cyk.utility.client.controller.component.view.ViewMap;
import org.cyk.utility.client.controller.component.window.Window;

public interface Theme extends Objectable {

	ViewMap getViewMap();
	ViewMap getViewMap(Boolean injectIfNull);
	Theme setViewMap(ViewMap viewMap);
	Theme mapViews(Object...objects);
	
	ThemeTemplate getTemplate();
	Theme setTemplate(ThemeTemplate template);
	
	ImageMap getImageMap();
	ImageMap getImageMap(Boolean injectIfNull);
	Theme setImageMap(ImageMap imageMap);
	Theme mapImages(Object...objects);
	
	Image getIcon();
	Image getIcon(Boolean injectIfNull);
	Theme setIcon(Image icon);
	
	Image getLogo();
	Image getLogo(Boolean injectIfNull);
	Theme setLogo(Image logo);
	
	Theme process(Window window);
}
