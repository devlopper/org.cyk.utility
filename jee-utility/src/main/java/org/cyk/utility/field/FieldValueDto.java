package org.cyk.utility.field;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString
public class FieldValueDto extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String value;
	private ValueType valueType;
	
	/**/
	
	public static enum ValueType {
		STRING,INTEGER
	}
}