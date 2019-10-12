package org.cyk.utility.client.controller.component.file;

import org.cyk.utility.__kernel__.file.FileAsFunctionParameter;
import org.cyk.utility.client.controller.component.VisibleComponentBuilder;

public interface FileBuilder extends VisibleComponentBuilder<File> {

	FileAsFunctionParameter getValue();
	FileAsFunctionParameter getValue(Boolean injectIfNull);
	FileBuilder setValue(FileAsFunctionParameter value);
	
	Boolean getIsEmbbedable();
	FileBuilder setIsEmbeddable(Boolean isEmbeddable);
	
	Boolean getIsIdentifiable();
	FileBuilder setIsIdentifiable(Boolean isIdentifiable);
	
	FileBuilder setValuePath(String path);
	FileBuilder setValueName(String name);
	FileBuilder setValueClazz(Class<?> clazz);
	FileBuilder setValueBytes(byte[] bytes);
	FileBuilder setValueUniformResourceLocator(String uniformResourceLocator);
	FileBuilder setValueMimeType(String mimeType);
}
