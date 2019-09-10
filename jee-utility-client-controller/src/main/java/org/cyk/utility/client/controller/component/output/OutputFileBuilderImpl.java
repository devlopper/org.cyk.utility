package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.client.controller.component.file.FileBuilder;
import org.cyk.utility.client.controller.component.file.FileImageBuilder;
import org.cyk.utility.client.controller.component.link.LinkBuilder;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.repository.RepositoryType;
import org.cyk.utility.resource.locator.UniformResourceLocatorStringBuilder;

public class OutputFileBuilderImpl extends AbstractOutputBuilderImpl<OutputFile,org.cyk.utility.file.File> implements OutputFileBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private FileBuilder file;
	private RepositoryType repositoryType;
	private LinkBuilder link;
	private FileImageBuilder thumbnail;
	private Boolean isThumbnailDerivableFromFile,isThumbnailDerivable,isLinkDerivable;
	
	@Override
	protected void __execute__(OutputFile outputFile, Object object, Field field) {
		super.__execute__(outputFile, object, field);
		FileBuilder file = getFile();
		org.cyk.utility.file.File __file__ = outputFile.getValue();
		if(file == null && __file__!=null) {
			file = __inject__(FileBuilder.class);
		}
		
		if(file!=null) {
			if(Boolean.TRUE.equals(__getIsFieldNameDerivable__(PROPERTY_FILE))) {
				if(file.getValue(Boolean.TRUE).getPath() == null)
					file.getValue(Boolean.TRUE).setPath(__file__.getPath());
				if(file.getValue(Boolean.TRUE).getName() == null)
					file.getValue(Boolean.TRUE).setName(__file__.getName());
				if(file.getValue(Boolean.TRUE).getExtension() == null)
					file.getValue(Boolean.TRUE).setExtension(__file__.getExtension());
				if(file.getValue(Boolean.TRUE).getMimeType() == null)
					file.getValue(Boolean.TRUE).setMimeType(__file__.getMimeType());
				if(file.getValue(Boolean.TRUE).getBytes() == null)
					file.getValue(Boolean.TRUE).setBytes(__file__.getBytes());
				if(file.getValue(Boolean.TRUE).getSize() == null)
					file.getValue(Boolean.TRUE).setSize(__file__.getSize());
				if(file.getValue(Boolean.TRUE).getUniformResourceLocator() == null)
					file.getValue(Boolean.TRUE).setUniformResourceLocator(__file__.getUniformResourceLocator());
				if(file.getValue(Boolean.TRUE).getIdentifier() == null)
					file.getValue(Boolean.TRUE).setIdentifier(__file__.getIdentifier());
			}
			if(file.getRequest() == null)
				file.setRequest(getRequest());
			if(file.getContext() == null)
				file.setContext(getContext());
			if(file.getUniformResourceLocatorMap() == null)
				file.setUniformResourceLocatorMap(getUniformResourceLocatorMap());
			outputFile.setFile(file.execute().getOutput());
			__file__ = outputFile.getFile().getValue();
			outputFile.setValue(__file__);
			//Link
			LinkBuilder link = getLink();
			if(link == null /*&& Boolean.TRUE.equals(__getIsFieldNameDerivable__(PROPERTY_LINK))*/) {			
				if(__injectStringHelper__().isNotBlank(__file__.getUniformResourceLocator())) {
					link = __inject__(LinkBuilder.class);
					//link.setTextCharacters(__file__.getName());
					link.setTextCharacters("cliquer ici pour voir le fichier.");
					link.setUniformResourceLocator(__inject__(UniformResourceLocatorStringBuilder.class)
							.setUniformResourceIdentifierString(__inject__(UniformResourceIdentifierStringBuilder.class).setString(__file__.getUniformResourceLocator())));	
				}
			}
			
			if(link!=null) {
				if(link.getRequest() == null)
					link.setRequest(getRequest());
				if(link.getContext() == null)
					link.setContext(getContext());
				if(link.getUniformResourceLocatorMap() == null)
					link.setUniformResourceLocatorMap(getUniformResourceLocatorMap());
				outputFile.setLink(link.execute().getOutput());
			}
			
			//Thumbnail
			FileImageBuilder thumbnail = getThumbnail();
			if(thumbnail == null) {
				if(__injectStringHelper__().isNotBlank(__file__.getUniformResourceLocator())) {
					//Thumbnail will be an icon based on mime type
					thumbnail = __inject__(FileImageBuilder.class);
					thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setPath("image/icon");
					thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setName(StringUtils.substringBefore(__file__.getMimeType(),"/")).setExtension("png");	
				}
			}
			
			if(thumbnail!=null) {
				if(thumbnail.getRequest() == null)
					thumbnail.setRequest(getRequest());
				if(thumbnail.getContext() == null)
					thumbnail.setContext(getContext());
				if(thumbnail.getUniformResourceLocatorMap() == null)
					thumbnail.setUniformResourceLocatorMap(getUniformResourceLocatorMap());
				//thumbnail.getProperties().setAlt(StringUtils.substringBefore(file.getMimeType(),"/"));
				thumbnail.setTooltip(StringUtils.substringBefore(__file__.getMimeType(),"/"));
				thumbnail.setWidth(40);
				thumbnail.setHeight(40);
				outputFile.setThumbnail(thumbnail.execute().getOutput());
			}
		}
	}
	
	@Override
	protected org.cyk.utility.file.File __getValue__(Object object, Field field, Object value) {
		return (org.cyk.utility.file.File) value;
	}
	
	@Override
	public FileBuilder getFile() {
		return file;
	}
	
	@Override
	public FileBuilder getFile(Boolean injectIfNull) {
		if(file == null && Boolean.TRUE.equals(injectIfNull))
			file = __inject__(FileBuilder.class);
		return file;
	}
	
	@Override
	public OutputFileBuilder setFile(FileBuilder file) {
		this.file = file;
		return this;
	}

	@Override
	public RepositoryType getRepositoryType() {
		return repositoryType;
	}

	@Override
	public OutputFileBuilder setRepositoryType(RepositoryType repositoryType) {
		this.repositoryType = repositoryType;
		return this;
	}

	@Override
	public Boolean getIsThumbnailDerivableFromFile() {
		return isThumbnailDerivableFromFile;
	}

	@Override
	public OutputFileBuilder setIsThumbnailDerivableFromFile(Boolean isThumbnailDerivableFromFile) {
		this.isThumbnailDerivableFromFile = isThumbnailDerivableFromFile;
		return this;
	}
	
	@Override
	public Boolean getIsThumbnailDerivable() {
		return isThumbnailDerivable;
	}

	@Override
	public OutputFileBuilder setIsThumbnailDerivable(Boolean isThumbnailDerivable) {
		this.isThumbnailDerivable = isThumbnailDerivable;
		return this;
	}
	
	@Override
	public LinkBuilder getLink() {
		return link;
	}
	
	@Override
	public LinkBuilder getLink(Boolean injectIfNull) {
		if(link == null && Boolean.TRUE.equals(injectIfNull))
			link = __inject__(LinkBuilder.class);
		return link;
	}
	
	@Override
	public OutputFileBuilder setLink(LinkBuilder link) {
		this.link = link;
		return this;
	}
	
	@Override
	public Boolean getIsLinkDerivable() {
		return isLinkDerivable;
	}
	
	@Override
	public OutputFileBuilder setIsLinkDerivable(Boolean isLinkDerivable) {
		this.isLinkDerivable = isLinkDerivable;
		return this;
	}
	
	@Override
	public FileImageBuilder getThumbnail() {
		return thumbnail;
	}
	
	@Override
	public FileImageBuilder getThumbnail(Boolean injectIfNull) {
		if(thumbnail == null && Boolean.TRUE.equals(injectIfNull))
			thumbnail = __inject__(FileImageBuilder.class);
		return thumbnail;
	}
	
	@Override
	public OutputFileBuilder setThumbnail(FileImageBuilder thumbnail) {
		this.thumbnail = thumbnail;
		return this;
	}
	
	/**/

}
