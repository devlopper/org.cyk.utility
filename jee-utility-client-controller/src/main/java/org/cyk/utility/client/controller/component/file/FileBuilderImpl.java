package org.cyk.utility.client.controller.component.file;

import java.io.Serializable;

import org.cyk.utility.__kernel__.file.FileAsFunctionParameter;
import org.cyk.utility.__kernel__.file.FileHelper;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierHelper;
import org.cyk.utility.__kernel__.session.SessionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.random.RandomHelper;

public class FileBuilderImpl extends AbstractVisibleComponentBuilderImpl<File> implements FileBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private FileAsFunctionParameter value;
	private Boolean isEmbeddable,isIdentifiable;
	
	@Override
	protected void __execute__(File file) {
		super.__execute__(file);
		FileAsFunctionParameter value = getValue();
		if(value != null) {
			Boolean isEmbeddable = ValueHelper.defaultToIfNull(getIsEmbbedable(),Boolean.FALSE);
			Boolean isIdentifiable = ValueHelper.defaultToIfNull(getIsIdentifiable(),StringHelper.isBlank(value.getPath()));
			file.setIsEmbedded(isEmbeddable);
			Object sessionAttributeIdentifier = null;
			if(Boolean.FALSE.equals(isEmbeddable) || (value.getBytes() == null && value.getInputStream()==null && value.getClassToGetResourceAsStream()==null)) {
				//content is not embeddable
				if(Boolean.TRUE.equals(isIdentifiable)) {
					//content will be identified by a derived uniform resource locator
					String url = value.getUniformResourceLocator() == null ? null : UniformResourceIdentifierHelper.build(value.getUniformResourceLocator());
					if(StringHelper.isBlank(url)) {
						String identifier = null;
						String location = null;
						if(value.getIdentifier() == null) {
							//file content is not persisted so it will be put in user session
							sessionAttributeIdentifier = identifier = "file_identifier_"+RandomHelper.getAlphanumeric(10);
							location = "session";
						}else {
							//file content is persisted durable in database
							identifier = value.getIdentifier().toString();
							location = "database";
						}
						UniformResourceIdentifierAsFunctionParameter parameter = new UniformResourceIdentifierAsFunctionParameter();
						parameter.getPath(Boolean.TRUE).setIdentifier("__file__GetFunction");
						parameter.getQuery(Boolean.TRUE).setValue("identifier="+identifier+"&location="+location);
						value.getUniformResourceLocator(Boolean.TRUE).setValue(UniformResourceIdentifierHelper.build(parameter));
					}	
				}
			}
			file.setValue(FileHelper.build(value));
			if(sessionAttributeIdentifier!=null)
				SessionHelper.setAttributeValueFromRequest(sessionAttributeIdentifier, file.getValue(),getRequest());
		}
	}
	
	@Override
	public FileAsFunctionParameter getValue() {
		return value;
	}

	@Override
	public FileAsFunctionParameter getValue(Boolean injectIfNull) {
		if(value == null && Boolean.TRUE.equals(injectIfNull))
			value = new FileAsFunctionParameter();
		return value;
	}

	@Override
	public FileBuilder setValue(FileAsFunctionParameter value) {
		this.value = value;
		return this;
	}
	
	@Override
	public Boolean getIsEmbbedable() {
		return isEmbeddable;
	}
	
	@Override
	public FileBuilder setIsEmbeddable(Boolean isEmbeddable) {
		this.isEmbeddable = isEmbeddable;
		return this;
	}
	
	@Override
	public Boolean getIsIdentifiable() {
		return isIdentifiable;
	}
	
	@Override
	public FileBuilder setIsIdentifiable(Boolean isIdentifiable) {
		this.isIdentifiable = isIdentifiable;
		return this;
	}
	
	@Override
	public FileBuilder setValuePath(String path) {
		getValue(Boolean.TRUE).setPath(path);
		return this;
	}
	
	@Override
	public FileBuilder setValueName(String name) {
		getValue(Boolean.TRUE).setName(name);
		return this;
	}
	
	@Override
	public FileBuilder setValueClazz(Class<?> clazz) {
		getValue(Boolean.TRUE).setClassToGetResourceAsStream(clazz);
		return this;
	}

	@Override
	public FileBuilder setValueBytes(byte[] bytes) {
		getValue(Boolean.TRUE).setBytes(bytes);
		return this;
	}
	
	@Override
	public FileBuilder setValueUniformResourceLocator(String uniformResourceLocator) {
		getValue(Boolean.TRUE).getUniformResourceLocator(Boolean.TRUE).setValue(uniformResourceLocator);
		return this;
	}
	
	@Override
	public FileBuilder setValueMimeType(String mimeType) {
		getValue(Boolean.TRUE).setMimeType(mimeType);
		return this;
	}
	
	/**/
	
}
