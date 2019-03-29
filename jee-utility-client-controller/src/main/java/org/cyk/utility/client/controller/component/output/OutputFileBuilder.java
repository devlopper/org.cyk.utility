package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.client.controller.component.image.ImageBuilder;
import org.cyk.utility.client.controller.component.link.LinkBuilder;
import org.cyk.utility.file.File;
import org.cyk.utility.repository.RepositoryType;

public interface OutputFileBuilder extends OutputBuilder<OutputFile,File> {
	
	RepositoryType getRepositoryType();
	OutputFileBuilder setRepositoryType(RepositoryType repositoryType);
	
	LinkBuilder getLink();
	LinkBuilder getLink(Boolean injectIfNull);
	OutputFileBuilder setLink(LinkBuilder link);
	
	Boolean getIsLinkDerivable();
	OutputFileBuilder setIsLinkDerivable(Boolean isLinkDerivable);
	
	ImageBuilder getThumbnail();
	ImageBuilder getThumbnail(Boolean injectIfNull);
	OutputFileBuilder setThumbnail(ImageBuilder thumbnail);
	
	Boolean getIsThumbnailDerivable();
	OutputFileBuilder setIsThumbnailDerivable(Boolean isThumbnailDerivable);
	
	Boolean getIsThumbnailDerivableFromFile();
	OutputFileBuilder setIsThumbnailDerivableFromFile(Boolean isThumbnailDerivableFromFile);
	
}
