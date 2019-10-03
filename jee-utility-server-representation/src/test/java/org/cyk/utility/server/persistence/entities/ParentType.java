package org.cyk.utility.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCodedAndNamed;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity @Access(AccessType.FIELD) @Getter @Setter @Accessors(chain=true) @ToString
public class ParentType extends AbstractIdentifiedByStringAndCodedAndNamed implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public ParentType setIdentifier(String identifier) {
		return (ParentType) super.setIdentifier(identifier);
	}
	
	@Override
	public ParentType setCode(String code) {
		return (ParentType) super.setCode(code);
	}
	
	@Override
	public ParentType setName(String name) {
		return (ParentType) super.setName(name);
	}
}
