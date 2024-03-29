package org.cyk.utility.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCoded;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true) @ToString
public class MyEntity extends AbstractIdentifiedByStringAndCoded implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Long long1;
	private Long long2;
	private Integer integerValue;
	
	@Override
	public MyEntity setIdentifier(String identifier) {
		return (MyEntity) super.setIdentifier(identifier);
	}
	
	@Override
	public MyEntity setCode(String code) {
		return (MyEntity) super.setCode(code);
	}
	
}
