package org.cyk.utility.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.cyk.utility.__kernel__.object.__static__.identifiable.IdentifiablePersistable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @MappedSuperclass @Access(AccessType.FIELD)
public class IdentifiedBy<IDENTIFIER> extends IdentifiablePersistable.BaseClass<IDENTIFIER> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Access(AccessType.PROPERTY) @Id
	public IDENTIFIER getIdentifier(){
		return super.getIdentifier();
	}
	
	@Override
	public IdentifiedBy<IDENTIFIER> setIdentifier(IDENTIFIER identifier) {
		super.setIdentifier(identifier);
		return this;
	}
	
}