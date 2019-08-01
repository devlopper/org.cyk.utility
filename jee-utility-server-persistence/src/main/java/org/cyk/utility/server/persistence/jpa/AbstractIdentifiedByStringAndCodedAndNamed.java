package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiedByStringAndCodedAndNamed extends AbstractIdentifiedByStringAndCoded implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull @Column(name=COLUMN_NAME,nullable=false)
	protected String name;
	
	/**/
	
	public static final String FIELD_NAME = "name";
	
	public static final String COLUMN_NAME = FIELD_NAME;
}