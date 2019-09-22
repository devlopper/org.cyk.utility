package org.cyk.utility.value;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString
public class ValueDto extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String value;
	private Container container;
	private Type type;
	private ValueUsageType usageType;
	
	/**/
	
	public static enum Container {
		NONE,COLLECTION,MAP
	}
	
	public static enum Type {
		STRING,INTEGER,LONG
	}
}
