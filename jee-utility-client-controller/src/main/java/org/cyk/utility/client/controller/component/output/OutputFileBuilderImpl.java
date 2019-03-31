package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.client.controller.component.file.File;
import org.cyk.utility.client.controller.component.file.FileBuilder;
import org.cyk.utility.client.controller.component.file.FileImageBuilder;
import org.cyk.utility.client.controller.component.link.LinkBuilder;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.client.controller.session.SessionAttributeSetter;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.repository.RepositoryType;
import org.cyk.utility.repository.RepositoryTypeDatabase;
import org.cyk.utility.repository.RepositoryTypeSession;
import org.cyk.utility.resource.locator.UniformResourceLocatorStringBuilder;
import org.cyk.utility.string.StringHelper;

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
					link.setTextCharacters(__file__.getName());
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
				Boolean isThumbnailDerivableFromFile = getIsThumbnailDerivableFromFile();
				if(isThumbnailDerivableFromFile == null && file!=null)
					isThumbnailDerivableFromFile = __file__.isImage();
				if(Boolean.TRUE.equals(isThumbnailDerivableFromFile)) {
					thumbnail = __inject__(FileImageBuilder.class);
					thumbnail.getFile(Boolean.TRUE).setValueName(__file__.getName()).setValueBytes(__file__.getBytes());
					thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setExtension(__file__.getExtension());
					thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setMimeType(__file__.getMimeType());
					thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setUniformResourceLocator(__file__.getUniformResourceLocator());
					thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setSize(__file__.getSize());
					thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setPath(__file__.getPath());
					/*
					byte[] bytes = file.getBytes();
					if(Boolean.TRUE.equals(__file__.isImage()) && (bytes!=null || __inject__(StringHelper.class).isNotBlank(uniformResourceLocator))) {
						//Thumbnail will be image itself
						if(bytes == null) {
							//no data to be embedded , browser will handle it by doing get request
							thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setUniformResourceLocator(uniformResourceLocator);
						}else {
							//data to be embedded using base64 encoding
							thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setBytes(bytes).setMimeType(file.getMimeType());
						}									
					}else {
						//Thumbnail will be an icon based on mime type
						thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setPath("image/icon");
						thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setName(StringUtils.substringBefore(file.getMimeType(),"/")).setExtension("png");	
					}
					*/
				}else {
					System.err.println("derive thumbnail from file not handle in this case 02");
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

		/*
		RepositoryType repositoryType = getRepositoryType();
		if(repositoryType == null) {
			File file = getValue();
			if(file == null || file.getIdentifier() == null)
				repositoryType = __inject__(RepositoryTypeSession.class);
			else
				repositoryType = __inject__(RepositoryTypeDatabase.class);
		}
		
		File file = outputFile.getValue();
				
		FileImageBuilder thumbnail = getThumbnail();
		if(thumbnail == null) {
			Boolean isThumbnailDerivable = __injectValueHelper__().defaultToIfNull(getIsThumbnailDerivable(), Boolean.TRUE);
			if(Boolean.TRUE.equals(isThumbnailDerivable)) {
				thumbnail = __inject__(FileImageBuilder.class);
				Boolean isThumbnailDerivableFromFile = getIsThumbnailDerivableFromFile();
				if(isThumbnailDerivableFromFile == null && file!=null)
					isThumbnailDerivableFromFile = file.isImage();
				if(Boolean.TRUE.equals(isThumbnailDerivableFromFile)) {
					byte[] bytes = file.getBytes();
					if(Boolean.TRUE.equals(file.isImage()) && (bytes!=null || __inject__(StringHelper.class).isNotBlank(uniformResourceLocator))) {
						//Thumbnail will be image itself
						if(bytes == null) {
							//no data to be embedded , browser will handle it by doing get request
							thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setUniformResourceLocator(uniformResourceLocator);
						}else {
							//data to be embedded using base64 encoding
							thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setBytes(bytes).setMimeType(file.getMimeType());
						}									
					}else {
						//Thumbnail will be an icon based on mime type
						thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setPath("image/icon");
						thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setName(StringUtils.substringBefore(file.getMimeType(),"/")).setExtension("png");	
					}
				}else {
					System.err.println("derive thumbnail from file not handle in this case 02");
				}
			}
		}
		
		if(thumbnail!=null) {
			//thumbnail.getProperties().setAlt(StringUtils.substringBefore(file.getMimeType(),"/"));
			thumbnail.setTooltip(StringUtils.substringBefore(file.getMimeType(),"/"));
			thumbnail.setWidth(40);
			thumbnail.setHeight(40);
		}
		*/
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
		return (FileBuilder) __getInjectIfNull__(FIELD_FILE, injectIfNull);
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
		return (LinkBuilder) __getInjectIfNull__(FIELD_LINK, injectIfNull);
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
		return (FileImageBuilder) __getInjectIfNull__(FIELD_THUMBNAIL, injectIfNull);
	}
	
	@Override
	public OutputFileBuilder setThumbnail(FileImageBuilder thumbnail) {
		this.thumbnail = thumbnail;
		return this;
	}
	
	/**/
	
	private static final String FIELD_FILE = "file";
	private static final String FIELD_LINK = "link";
	private static final String FIELD_THUMBNAIL = "thumbnail";
}
