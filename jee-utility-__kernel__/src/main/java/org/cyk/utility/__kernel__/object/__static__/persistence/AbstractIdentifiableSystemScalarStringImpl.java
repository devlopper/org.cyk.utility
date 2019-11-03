package org.cyk.utility.__kernel__.object.__static__.persistence;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
@MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiableSystemScalarStringImpl extends AbstractIdentifiableSystemScalarImpl<String> implements Serializable {
	private static final long serialVersionUID = 1L;
	
}
