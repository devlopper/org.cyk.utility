package org.cyk.utility.playground.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Entity;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCodedAndNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
public class MyEntity extends AbstractIdentifiedByStringAndCodedAndNamed implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long long1;
	private Long long2;
	
	@Override
	public MyEntity setIdentifier(String identifier) {
		return (MyEntity) super.setIdentifier(identifier);
	}
	
	@Override
	public MyEntity setCode(String code) {
		return (MyEntity) super.setCode(code);
	}
	
	@Override
	public MyEntity setName(String name) {
		return (MyEntity) super.setName(name);
	}
	
	@Override
	public String toString() {
		return getCode();
	}
}
