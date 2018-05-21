package org.cyk.utility.common.persistence.jpa;

import javax.persistence.Entity;

import org.cyk.utility.common.model.identifiable.IdentifiablePersistable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity @Getter @Setter @Accessors(chain=true)
public class EntityWithExtendsAndMethodAnnotated extends IdentifiablePersistable.ByLong.BaseClass.JavaPersistenceEntity {
	private static final long serialVersionUID = 1L;
	
}
