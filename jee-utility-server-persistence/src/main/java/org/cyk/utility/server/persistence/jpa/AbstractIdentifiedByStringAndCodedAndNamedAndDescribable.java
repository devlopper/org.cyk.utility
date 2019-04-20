package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiedByStringAndCodedAndNamedAndDescribable extends AbstractIdentifiedByStringAndCodedAndNamed implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name=COLUMN_DESCRIPTION)
	protected String description;
	
	/**/
	
	public static final String FIELD_DESCRIPTION = "description";
	
	public static final String COLUMN_DESCRIPTION = FIELD_DESCRIPTION;
}