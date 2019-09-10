package org.cyk.utility.client.controller.component.file;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.component.AbstractVisibleComponentBuilderImpl;
import org.cyk.utility.client.controller.navigation.NavigationBuilder;
import org.cyk.utility.random.RandomHelper;

public class FileBuilderImpl extends AbstractVisibleComponentBuilderImpl<File> implements FileBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private org.cyk.utility.file.FileBuilder value;
	private Boolean isEmbeddable,isIdentifiable;
	
	@Override
	protected void __execute__(File file) {
		super.__execute__(file);
		org.cyk.utility.file.FileBuilder value = getValue();
		if(value != null) {
			Boolean isEmbeddable = __injectValueHelper__().defaultToIfNull(getIsEmbbedable(),Boolean.FALSE);
			Boolean isIdentifiable = __injectValueHelper__().defaultToIfNull(getIsIdentifiable(),__injectStringHelper__().isBlank(value.getPath()));
			file.setIsEmbedded(isEmbeddable);
			Object sessionAttributeIdentifier = null;
			if(Boolean.FALSE.equals(isEmbeddable) || (value.getBytes() == null && value.getInputStream()==null && value.getClazz()==null)) {
				//content is not embeddable
				if(Boolean.TRUE.equals(isIdentifiable)) {
					//content will be identified by a derived uniform resource locator
					String url = value.getUniformResourceLocator();
					if(__injectStringHelper__().isBlank(url)) {
						String identifier = null;
						String location = null;
						if(value.getIdentifier() == null) {
							//file content is not persisted so it will be put in user session
							sessionAttributeIdentifier = identifier = "file_identifier_"+__inject__(RandomHelper.class).getAlphanumeric(10);
							location = "session";
						}else {
							//file content is persisted durable in database
							identifier = value.getIdentifier().toString();
							location = "database";
						}
						
						NavigationBuilder navigation = __inject__(NavigationBuilder.class).setIdentifier("__file__GetFunction").setParameters("identifier",identifier,"location",location);					
						navigation.setProperty(Properties.UNIFORM_RESOURCE_LOCATOR_MAP, getUniformResourceLocatorMap());
						navigation.setProperty(Properties.CONTEXT, getContext());
						url = navigation.execute().getOutput().getUniformResourceLocator().toString();	
						value.setUniformResourceLocator(url);	
					}	
				}
			}
			file.setValue(value.execute().getOutput());
			if(sessionAttributeIdentifier!=null)
				__injectSessionHelper__().setAttributeValue(sessionAttributeIdentifier, file.getValue(), getRequest());
		}
	}
	
	@Override
	public org.cyk.utility.file.FileBuilder getValue() {
		return value;
	}

	@Override
	public org.cyk.utility.file.FileBuilder getValue(Boolean injectIfNull) {
		if(value == null && Boolean.TRUE.equals(injectIfNull))
			value = __inject__(org.cyk.utility.file.FileBuilder.class);
		return value;
	}

	@Override
	public FileBuilder setValue(org.cyk.utility.file.FileBuilder value) {
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
		getValue(Boolean.TRUE).setClazz(clazz);
		return this;
	}

	@Override
	public FileBuilder setValueBytes(byte[] bytes) {
		getValue(Boolean.TRUE).setBytes(bytes);
		return this;
	}
	
	@Override
	public FileBuilder setValueUniformResourceLocator(String uniformResourceLocator) {
		getValue(Boolean.TRUE).setUniformResourceLocator(uniformResourceLocator);
		return this;
	}
	
	@Override
	public FileBuilder setValueMimeType(String mimeType) {
		getValue(Boolean.TRUE).setMimeType(mimeType);
		return this;
	}
	
	/**/
	
}
