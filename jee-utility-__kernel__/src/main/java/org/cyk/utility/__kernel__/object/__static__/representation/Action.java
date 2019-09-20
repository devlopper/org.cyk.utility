package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@XmlRootElement(name=Action.__ROOT_NAME__)
@JsonbPropertyOrder(value = {"identifier","uniformResourceLocator","method"})
public class Action implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonbProperty(value=__ATTRIBUTE_IDENTIFIER__)
	private String identifier;
	
	@JsonbProperty(value=__ATTRIBUTE_UNIFORM_RESOURCEL_OCATOR__)
	private String uniformResourceLocator;
	
	@JsonbProperty(value=__ATTRIBUTE_METHOD__)
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
	public static final String IDENTIFIER_DELETE_BY_IDENTIFIERS = "delete.by.identifiers";
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
