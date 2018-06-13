package org.cyk.utility.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.cyk.utility.__kernel__.object.__static__.identifiable.IdentifiablePersistable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @MappedSuperclass @Access(AccessType.FIELD)
public class IdentifiedByLong extends IdentifiablePersistable.ByLong.BaseClass implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Access(AccessType.PROPERTY) @Id
	@Override
	public Long getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override
	public IdentifiedByLong setIdentifier(Long identifier) {
		return (IdentifiedByLong) super.setIdentifier(identifier);
	}
	
}