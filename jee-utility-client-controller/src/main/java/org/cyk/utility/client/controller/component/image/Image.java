package org.cyk.utility.client.controller.component.image;

import org.cyk.utility.client.controller.component.VisibleComponent;
import org.cyk.utility.client.controller.component.file.File;

public interface Image extends VisibleComponent {

	File getFile();
	Image setFile(File file);
	
	Image setWidth(Integer width);
	Integer getWidth();
	
	Image setHeight(Integer height);
	Integer getHeight();
}
