package org.cyk.utility.persistence.server.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@MappedSuperclass @Getter @Setter @Accessors(chain=true)
public abstract class AbstractAuditIdentifiedByString extends AbstractAudit implements Serializable {

	@Id @Column(name = COLUMN_IDENTIFIER)
	protected String identifier;
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	
	/**/
	
	public static final String COLUMN_IDENTIFIER = "identifier";
}