package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.file.File;
import org.cyk.utility.repository.RepositoryType;

public interface OutputFileBuilder extends OutputBuilder<OutputFile,File> {
	
	RepositoryType getRepositoryType();
	OutputFileBuilder setRepositoryType(RepositoryType repositoryType);
	
}
