package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.client.controller.component.file.FileBuilder;
import org.cyk.utility.client.controller.component.file.FileImageBuilder;
import org.cyk.utility.client.controller.component.link.LinkBuilder;
import org.cyk.utility.repository.RepositoryType;

public interface OutputFileBuilder extends OutputBuilder<OutputFile,org.cyk.utility.__kernel__.file.File> {
	
	RepositoryType getRepositoryType();
	OutputFileBuilder setRepositoryType(RepositoryType repositoryType);
	
	FileBuilder getFile();
	FileBuilder getFile(Boolean injectIfNull);
	OutputFileBuilder setFile(FileBuilder file);
	
	LinkBuilder getLink();
	LinkBuilder getLink(Boolean injectIfNull);
	OutputFileBuilder setLink(LinkBuilder link);
	
	Boolean getIsLinkDerivable();
	OutputFileBuilder setIsLinkDerivable(Boolean isLinkDerivable);
	
	FileImageBuilder getThumbnail();
	FileImageBuilder getThumbnail(Boolean injectIfNull);
	OutputFileBuilder setThumbnail(FileImageBuilder thumbnail);
	
	Boolean getIsThumbnailDerivable();
	OutputFileBuilder setIsThumbnailDerivable(Boolean isThumbnailDerivable);
	
	Boolean getIsThumbnailDerivableFromFile();
	OutputFileBuilder setIsThumbnailDerivableFromFile(Boolean isThumbnailDerivableFromFile);
	
	/**/
	
	String PROPERTY_FILE = "file";
	String PROPERTY_LINK = "link";
}
