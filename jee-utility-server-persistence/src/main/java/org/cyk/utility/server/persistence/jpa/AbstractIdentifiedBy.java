package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractIdentifiedPersistable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiedBy<IDENTIFIER> extends AbstractIdentifiedPersistable<IDENTIFIER> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Access(AccessType.PROPERTY) @Id
	public IDENTIFIER getIdentifier(){
		return super.getIdentifier();
	}
	
	@Override
	public AbstractIdentifiedBy<IDENTIFIER> setIdentifier(IDENTIFIER identifier) {
		super.setIdentifier(identifier);
		return this;
	}
	
}