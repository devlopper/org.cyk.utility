package org.cyk.utility.representation.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Link implements Serializable{

	private String name;
	private String value;
}