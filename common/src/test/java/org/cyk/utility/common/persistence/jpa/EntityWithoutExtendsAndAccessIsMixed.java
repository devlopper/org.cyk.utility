package org.cyk.utility.common.persistence.jpa;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true) @Access(AccessType.FIELD)
public class EntityWithoutExtendsAndAccessIsMixed {

	private Long identifier;
	private String code;
	
	@Id @Access(AccessType.PROPERTY)
	public Long getIdentifier(){
		return identifier;
	}
	
	
}
