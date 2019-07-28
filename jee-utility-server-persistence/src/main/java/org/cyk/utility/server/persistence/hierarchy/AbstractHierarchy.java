package org.cyk.utility.server.persistence.hierarchy;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.cyk.utility.server.persistence.jpa.AbstractIdentifiedByString;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@MappedSuperclass @Getter @Setter @Accessors(chain=true) @Access(AccessType.FIELD) @ToString
public class AbstractHierarchy<ENTITY extends AbstractIdentifiedByStringAndCodedAndNamedAndHierarchy<?,?>> extends AbstractIdentifiedByString implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne @JoinColumn(name=COLUMN_PARENT) @NotNull private ENTITY parent;
	@ManyToOne @JoinColumn(name=COLUMN_CHILD) @NotNull private ENTITY child;
	
	/**/
	
	public static final String FIELD_PARENT = "parent";
	public static final String FIELD_CHILD = "child";
	
	public static final String COLUMN_PARENT = "parent";
	public static final String COLUMN_CHILD = "child";

	public static final String UNIQUE_CONSTRAINT_PARENT_CHILD_NAME = COLUMN_PARENT+COLUMN_CHILD;
}
