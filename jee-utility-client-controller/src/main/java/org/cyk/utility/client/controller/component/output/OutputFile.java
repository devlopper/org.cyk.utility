package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.file.File;
import org.cyk.utility.repository.RepositoryType;

public interface OutputFile extends Output<File> {
	
	RepositoryType getRepositoryType();
	OutputFile setRepositoryType(RepositoryType repositoryType);
	
}
