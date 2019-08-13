package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@XmlRootElement(name=Action.__ROOT_NAME__)
@JsonRootName(value=Action.__ROOT_NAME__)
public class Action implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty(index=0,value=__ATTRIBUTE_IDENTIFIER__,required=true)
	private String identifier;
	
	@JsonProperty(index=1,value=__ATTRIBUTE_UNIFORM_RESOURCEL_OCATOR__,required=true)
	private String uniformResourceLocator;
	
	@JsonProperty(index=2,value=__ATTRIBUTE_METHOD__,required=true)
	private String method;
	
	/**/
	
	@XmlAttribute(name=__ATTRIBUTE_IDENTIFIER__,required=true)
	public String getIdentifier() {
		return identifier;
	}
	
	@XmlAttribute(name=__ATTRIBUTE_UNIFORM_RESOURCEL_OCATOR__,required=true)
	public String getUniformResourceLocator() {
		return uniformResourceLocator;
	}
	
	@XmlAttribute(name=__ATTRIBUTE_METHOD__,required=true)
	public String getMethod() {
		return method;
	}
	
	/**/
	
	public static final String __ROOT_NAME__ = "link";
	
	private static final String __ATTRIBUTE_IDENTIFIER__ = "rel";
	private static final String __ATTRIBUTE_UNIFORM_RESOURCEL_OCATOR__ = "href";
	private static final String __ATTRIBUTE_METHOD__ = "method";
	
	/**/
	
	public static final String IDENTIFIER_CREATE = "create";
	public static final String IDENTIFIER_READ = "read";
	public static final String IDENTIFIER_UPDATE = "update";
	public static final String IDENTIFIER_DELETE = "delete";
	public static final String IDENTIFIER_DOWNLOAD = "download";
	public static final String IDENTIFIER_UPLOAD = "upload";
	public static final String IDENTIFIER_LOAD = "load";
	public static final String IDENTIFIER_EXPORT = "export";
	public static final String IDENTIFIER_IMPORT = "import";
	
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_DELETE = "DELETE";
	public static final String METHOD_PATCH = "PATCH";
}
