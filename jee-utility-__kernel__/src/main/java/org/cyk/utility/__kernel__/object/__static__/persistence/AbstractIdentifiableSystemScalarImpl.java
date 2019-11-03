package org.cyk.utility.__kernel__.object.__static__.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true)
@MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiableSystemScalarImpl<IDENTIFIER> extends AbstractIdentifiableSystemImpl<IDENTIFIER> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Access(AccessType.PROPERTY) @Id @Column(name=COLUMN_IDENTIFIER)
	@Override
	public IDENTIFIER getIdentifier() {
		return super.getIdentifier();
	}
	
	/**/
	
	public static final String COLUMN_IDENTIFIER = FIELD_IDENTIFIER;
}
