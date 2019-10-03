package org.cyk.utility.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCoded;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity @Access(AccessType.FIELD) @Getter @Setter @Accessors(chain=true) @ToString
public class Child extends AbstractIdentifiedByStringAndCoded implements Serializable {
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastNames;
	
	@Override
	public Child setIdentifier(String identifier) {
		return (Child) super.setIdentifier(identifier);
	}
	
	@Override
	public Child setCode(String code) {
		return (Child) super.setCode(code);
	}
	
}
