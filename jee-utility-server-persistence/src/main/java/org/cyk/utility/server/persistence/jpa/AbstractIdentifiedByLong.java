package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentifiedPersistableByLong;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiedByLong extends AbstractIdentifiedPersistableByLong implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Access(AccessType.PROPERTY) @Id @GeneratedValue
	@Override
	public Long getIdentifier() {
		return super.getIdentifier();
	}
	
	@Override
	public AbstractIdentifiedByLong setIdentifier(Long identifier) {
		return (AbstractIdentifiedByLong) super.setIdentifier(identifier);
	}
	
}