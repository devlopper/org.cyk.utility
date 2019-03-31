package org.cyk.utility.client.controller.component.file;

import org.cyk.utility.client.controller.component.VisibleComponent;

public interface FileImage extends VisibleComponent {

	File getFile();
	FileImage setFile(File file);
	
	FileImage setWidth(Integer width);
	Integer getWidth();
	
	FileImage setHeight(Integer height);
	Integer getHeight();
}
