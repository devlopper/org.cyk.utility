package org.cyk.utility.server.persistence.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.cyk.utility.__kernel__.field.container.FieldContainerCollectionChildren;
import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByStringAndCoded;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity @Access(AccessType.FIELD) @Getter @Setter @Accessors(chain=true) @ToString 
public class Parent extends AbstractIdentifiedByStringAndCoded implements FieldContainerCollectionChildren<Child>,Serializable {
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastNames;
	@ManyToOne private ParentType type;
	@Transient private Collection<Child> children;
	
	@Override
	public Parent setIdentifier(String identifier) {
		return (Parent) super.setIdentifier(identifier);
	}
	
	@Override
	public Parent setCode(String code) {
		return (Parent) super.setCode(code);
	}
	
	@Override
	public Parent addChildren(Child... children) {
		return (Parent) FieldContainerCollectionChildren.super.addChildren(children);
	}
	
	@Override
	public Parent addChildrenByCodes(String... codes) {
		return (Parent) FieldContainerCollectionChildren.super.addChildrenByCodes(codes);
	}
	
	@Override
	public Class<Child> getChildrenClass() {
		return Child.class;
	}
}
