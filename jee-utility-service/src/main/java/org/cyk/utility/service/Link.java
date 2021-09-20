package org.cyk.utility.service;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Link implements Serializable{

	private String identifier;
	private String value;
	
	@Override
	public String toString() {
		return identifier+":"+value;
	}
}