package org.cyk.utility.__kernel__.security;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Credentials extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	private Object secret;
	
}
