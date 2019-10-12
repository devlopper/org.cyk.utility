package org.cyk.utility.__kernel__.file;

import java.io.InputStream;

import org.cyk.utility.__kernel__.AbstractAsFunctionParameterIdentified;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class FileAsFunctionParameter extends AbstractAsFunctionParameterIdentified<File,Object> {

	private UniformResourceIdentifierAsFunctionParameter uniformResourceLocator;
	private String path;
	private String name;
	private String extension;
	private String mimeType;
	private byte[] bytes;
	private InputStream inputStream;
	private Class<?> classToGetResourceAsStream;
	private Long size;
	private String checksum;
	private Boolean isChecksumComputable;
	
	public UniformResourceIdentifierAsFunctionParameter getUniformResourceLocator(Boolean injectIfNull) {
		if(uniformResourceLocator == null && Boolean.TRUE.equals(injectIfNull))
			uniformResourceLocator = new UniformResourceIdentifierAsFunctionParameter();
		return uniformResourceLocator;
	}
	
	public FileAsFunctionParameter setUniformResourceLocatorValue(String value) {
		getUniformResourceLocator(Boolean.TRUE).setValue(value);
		return this;
	}
	
}
