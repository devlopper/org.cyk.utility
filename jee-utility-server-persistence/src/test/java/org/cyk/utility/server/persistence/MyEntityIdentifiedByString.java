package org.cyk.utility.server.persistence;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
public class MyEntityIdentifiedByString extends AbstractIdentifiedByString implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer integerValue;
	
	/**/
	
	public static final String FIELD_INTEGER_VALUE = "integerValue";
}
