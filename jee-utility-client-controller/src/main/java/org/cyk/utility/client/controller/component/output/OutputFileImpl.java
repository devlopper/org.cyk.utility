package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.image.Image;
import org.cyk.utility.client.controller.component.link.Link;
import org.cyk.utility.file.File;
import org.cyk.utility.repository.RepositoryType;

public class OutputFileImpl extends AbstractOutputImpl<File> implements OutputFile,Serializable {
	private static final long serialVersionUID = 1L;

	private Link link;
	private Image thumbnail;
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
	public Image getThumbnail() {
		return thumbnail;
	}

	@Override
	public OutputFile setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
		return this;
	}

	
}
