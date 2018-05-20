package org.cyk.utility.common.persistence.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
public class EntityWithoutExtendsAndPropertyAnnotated {

	@Id
	private Long identifier;
	
}
