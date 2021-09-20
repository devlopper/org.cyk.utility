package org.cyk.utility.persistence.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @EqualsAndHashCode(callSuper = false,of = "identifier")
@MappedSuperclass @Access(AccessType.FIELD) @NoArgsConstructor
public abstract class AbstractIdentifiableSystemScalarStringImpl extends AbstractIdentifiableSystemScalarImpl<String> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name=COLUMN_IDENTIFIER)
	protected String identifier;
	
	public AbstractIdentifiableSystemScalarStringImpl(String identifier) {
		this.identifier = identifier;
	}
	
}
