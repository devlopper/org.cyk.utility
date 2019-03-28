package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;

import org.cyk.utility.file.File;
import org.cyk.utility.repository.RepositoryType;

public class OutputFileImpl extends AbstractOutputImpl<File> implements OutputFile,Serializable {
	private static final long serialVersionUID = 1L;

	private RepositoryType repositoryType;
	
	@Override
	public RepositoryType getRepositoryType() {
		return repositoryType;
	}

	@Override
	public OutputFile setRepositoryType(RepositoryType repositoryType) {
		this.repositoryType = repositoryType;
		return this;
	}

	
}
