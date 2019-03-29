package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.client.controller.component.image.Image;
import org.cyk.utility.client.controller.component.link.Link;
import org.cyk.utility.file.File;
import org.cyk.utility.repository.RepositoryType;

public interface OutputFile extends Output<File> {
	
	Link getLink();
	OutputFile setLink(Link link);
	
	Image getThumbnail();
	OutputFile setThumbnail(Image thumbnail);
	
	RepositoryType getRepositoryType();
	OutputFile setRepositoryType(RepositoryType repositoryType);
	
}
