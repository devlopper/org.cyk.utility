package org.cyk.utility.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity @Access(AccessType.FIELD) @Getter @Setter @Accessors(chain=true) @ToString 
public class ParentChild extends AbstractIdentifiedByString implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne private Parent parent;
	@ManyToOne private Child child;
	
	@Override
	public ParentChild setIdentifier(String identifier) {
		return (ParentChild) super.setIdentifier(identifier);
	}
	
	public static final String FIELD_PARENT = "parent";
	public static final String FIELD_CHILD = "child";
}
