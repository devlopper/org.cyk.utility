package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @MappedSuperclass
public abstract class AbstractEntity extends AbstractIdentifiedByLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	 * System properties
	 */
	
	//private String code;
	
	/*
	 * Business properties
	 */
	
	private String code;
	
}