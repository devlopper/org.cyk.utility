package org.cyk.utility.client.controller.component.file;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;

public interface FileImageBuilder extends VisibleComponentBuilder<FileImage> {

	FileBuilder getFile();
	FileBuilder getFile(Boolean injectIfNull);
	FileImageBuilder setFile(FileBuilder file);
	
	FileImageBuilder setWidth(Integer width);
	Integer getWidth();
	
	FileImageBuilder setHeight(Integer height);
	Integer getHeight();
}
