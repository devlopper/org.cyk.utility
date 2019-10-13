package org.cyk.utility.__kernel__.configuration;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Variable implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Object value;
	private Location location;
	
}
