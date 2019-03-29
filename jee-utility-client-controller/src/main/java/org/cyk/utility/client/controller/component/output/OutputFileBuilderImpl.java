package org.cyk.utility.client.controller.component.output;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.client.controller.component.image.ImageBuilder;
import org.cyk.utility.client.controller.component.link.LinkBuilder;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.client.controller.session.SessionAttributeSetter;
import org.cyk.utility.file.File;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.repository.RepositoryType;
import org.cyk.utility.repository.RepositoryTypeDatabase;
import org.cyk.utility.repository.RepositoryTypeSession;
import org.cyk.utility.string.StringHelper;

public class OutputFileBuilderImpl extends AbstractOutputBuilderImpl<OutputFile,File> implements OutputFileBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private RepositoryType repositoryType;
	private LinkBuilder link;
	private ImageBuilder thumbnail;
	private Boolean isThumbnailDerivableFromFile,isThumbnailDerivable,isLinkDerivable;
	
	@Override
	protected void __execute__(OutputFile outputFile, Object object, Field field) {
		super.__execute__(outputFile, object, field);
		RepositoryType repositoryType = getRepositoryType();
		if(repositoryType == null) {
			File file = getValue();
			if(file == null || file.getIdentifier() == null)
				repositoryType = __inject__(RepositoryTypeSession.class);
			else
				repositoryType = __inject__(RepositoryTypeDatabase.class);
		}
		
		File file = outputFile.getValue();
		
		LinkBuilder link = getLink();
		if(link == null) {
			Boolean isLinkDerivable = getIsLinkDerivable();
			if(Boolean.TRUE.equals(isLinkDerivable)) {
				String url = file.getUniformResourceLocator();
				if(__injectStringHelper__().isBlank(url)) {
					String identifier = null;
					String location = null;
					if(file.getIdentifier() == null) {
						//file content is not persisted so it will be put in user session
						identifier = "file_identifier_"+__inject__(RandomHelper.class).getAlphanumeric(10);
						__inject__(SessionAttributeSetter.class).setRequest(getRequest()).setAttribute(identifier).setValue(file).execute();
						location = "session";
					}else {
						//file content is persisted durable in database
						identifier = file.getIdentifier().toString();
						location = "database";
					}
					
					NavigationBuilder navigation = __inject__(NavigationBuilder.class).setIdentifier("__file__GetFunction").setParameters("identifier",identifier,"location",location);					
					url = navigation.execute().getOutput().getUniformResourceLocator().toString();	
				}
				
				if(__inject__(StringHelper.class).isNotBlank(url)) {
					link = __inject__(LinkBuilder.class);
					link.getText(Boolean.TRUE).setCharacters("LINK");
					link.getUniformResourceLocator(Boolean.TRUE).getUniformResourceIdentifierString(Boolean.TRUE).setString(url);
				}
			}
		}
		
		String uniformResourceLocator = null;
		
		if(link!=null) {
			outputFile.setLink(link.execute().getOutput());
			uniformResourceLocator = outputFile.getLink().getUniformResourceLocator();
		}
		
		ImageBuilder thumbnail = getThumbnail();
		if(thumbnail == null) {
			Boolean isThumbnailDerivable = __injectValueHelper__().defaultToIfNull(getIsThumbnailDerivable(), Boolean.TRUE);
			if(Boolean.TRUE.equals(isThumbnailDerivable)) {
				thumbnail = __inject__(ImageBuilder.class);
				Boolean isThumbnailDerivableFromFile = getIsThumbnailDerivableFromFile();
				if(isThumbnailDerivableFromFile == null && file!=null)
					isThumbnailDerivableFromFile = file.isImage();
				if(Boolean.TRUE.equals(isThumbnailDerivableFromFile)) {
					byte[] bytes = file.getBytes();
					if(Boolean.TRUE.equals(file.isImage()) && (bytes!=null || __inject__(StringHelper.class).isNotBlank(uniformResourceLocator))) {
						//Thumbnail will be image itself
						if(bytes == null) {
							//no data to be embbeded , browser will handle it by doing get request
							thumbnail.getFile(Boolean.TRUE).getValue(Boolean.TRUE).setUniformResourceLocator(uniformResourceLocator);
						}else {
							//data to be embbeded using base64 encoding
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
	}
	
	@Override
	protected File __getValue__(Object object, Field field, Object value) {
		return (File) value;
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
	public ImageBuilder getThumbnail() {
		return thumbnail;
	}
	
	@Override
	public ImageBuilder getThumbnail(Boolean injectIfNull) {
		return (ImageBuilder) __getInjectIfNull__(FIELD_THUMBNAIL, injectIfNull);
	}
	
	@Override
	public OutputFileBuilder setThumbnail(ImageBuilder thumbnail) {
		this.thumbnail = thumbnail;
		return this;
	}
	
	/**/
	
	private static final String FIELD_LINK = "link";
	private static final String FIELD_THUMBNAIL = "thumbnail";
}
