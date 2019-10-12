package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.FileImage;
import org.cyk.utility.client.controller.component.link.Link;
import org.cyk.utility.repository.RepositoryType;

public class OutputFileImpl extends AbstractOutputImpl<org.cyk.utility.__kernel__.file.File> implements OutputFile,Serializable {
	private static final long serialVersionUID = 1L;

	private File file;
	private Link link;
	private FileImage thumbnail;
	private RepositoryType repositoryType;
	
	@Override
	public File getFile() {
		return file;
	}
	
	@Override
	public OutputFile setFile(File file) {
		this.file = file;
		return this;
	}
	
	@Override
	public RepositoryType getRepositoryType() {
		return repositoryType;
	}

	@Override
	public OutputFile setRepositoryType(RepositoryType repositoryType) {
		this.repositoryType = repositoryType;
		return this;
	}

	@Override
	public Link getLink() {
		return link;
	}

	@Override
	public OutputFile setLink(Link link) {
		this.link = link;
		return this;
	}

	@Override
	public FileImage getThumbnail() {
		return thumbnail;
	}

	@Override
	public OutputFile setThumbnail(FileImage thumbnail) {
		this.thumbnail = thumbnail;
		return this;
	}

	
}
