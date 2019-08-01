package org.cyk.utility.server.persistence.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractHierarchy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true) @Access(AccessType.FIELD) @ToString
@Table(uniqueConstraints= {
		@UniqueConstraint(name=NodeHierarchy.UNIQUE_CONSTRAINT_PARENT_CHILD_NAME,columnNames= {NodeHierarchy.COLUMN_PARENT,NodeHierarchy.COLUMN_CHILD}
		)})
public class NodeHierarchy extends AbstractHierarchy<Node> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public NodeHierarchy setParent(Node parent) {
		return (NodeHierarchy) super.setParent(parent);
	}
	
	@Override
	public NodeHierarchy setChild(Node child) {
		return (NodeHierarchy) super.setChild(child);
	}

}
