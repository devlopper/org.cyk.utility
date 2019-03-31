package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.FileImage;
import org.cyk.utility.client.controller.component.link.Link;
import org.cyk.utility.repository.RepositoryType;

public interface OutputFile extends Output<org.cyk.utility.file.File> {
	
	File getFile();
	OutputFile setFile(File file);
	
	Link getLink();
	OutputFile setLink(Link link);
	
	FileImage getThumbnail();
	OutputFile setThumbnail(FileImage thumbnail);
	
	RepositoryType getRepositoryType();
	OutputFile setRepositoryType(RepositoryType repositoryType);
	
}
