package org.cyk.utility.representation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Link implements Serializable{

	private String identifier;
	private String value;
	
}