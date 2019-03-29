package org.cyk.utility.client.controller.component.image;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.file.FileBuilder;

public interface ImageBuilder extends VisibleComponentBuilder<Image> {

	FileBuilder getFile();
	FileBuilder getFile(Boolean injectIfNull);
	ImageBuilder setFile(FileBuilder file);
	
	ImageBuilder setWidth(Integer width);
	Integer getWidth();
	
	ImageBuilder setHeight(Integer height);
	Integer getHeight();
}
