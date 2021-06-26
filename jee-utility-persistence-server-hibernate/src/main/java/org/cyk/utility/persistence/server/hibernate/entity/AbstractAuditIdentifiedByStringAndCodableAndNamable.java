package org.cyk.utility.persistence.server.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@MappedSuperclass @Getter @Setter @Accessors(chain=true)
public abstract class AbstractAuditIdentifiedByStringAndCodableAndNamable extends AbstractAuditIdentifiedByStringAndCodable implements Serializable {

	@Column(name = COLUMN_NAME)
	protected String name;
	
	/**/
	
	public static final String FIELD_NAME = "name";
	
	/**/
	
	public static final String COLUMN_NAME = "name";
}