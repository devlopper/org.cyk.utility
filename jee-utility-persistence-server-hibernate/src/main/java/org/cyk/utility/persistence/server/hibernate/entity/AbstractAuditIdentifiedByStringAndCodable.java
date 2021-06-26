package org.cyk.utility.persistence.server.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@MappedSuperclass @Getter @Setter @Accessors(chain=true)
public abstract class AbstractAuditIdentifiedByStringAndCodable extends AbstractAuditIdentifiedByString implements Serializable {

	@Column(name = COLUMN_CODE)
	protected String code;
	
	/**/
	
	public static final String FIELD_CODE = "code";
	
	/**/
	
	public static final String COLUMN_CODE = "code";
}