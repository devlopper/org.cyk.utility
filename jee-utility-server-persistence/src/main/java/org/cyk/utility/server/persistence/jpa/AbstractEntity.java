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
public abstract class AbstractEntity extends AbstractIdentifiedByLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	 * System properties
	 */
	
	//private String code;
	
	/*
	 * Business properties
	 */
	@NotNull @Column(nullable=false,unique=true)
	protected String code;

	/**/
	
	public static final String FIELD_CODE = "code";
}