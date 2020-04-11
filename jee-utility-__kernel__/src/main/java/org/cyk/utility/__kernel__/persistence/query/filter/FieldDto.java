package org.cyk.utility.__kernel__.persistence.query.filter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString @Deprecated
public class FieldDto extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private org.cyk.utility.__kernel__.field.FieldDto field;
	private Value.Dto value;
	private ArithmeticOperator arithmeticOperator;
	
}
