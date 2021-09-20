package org.cyk.utility.persistence.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiableSystemScalarStringBoundedContextImpl extends AbstractIdentifiableSystemScalarStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//@Transient protected String sourceIdentifier;
	/*
	@Override
	public String toString() {
		String string = getSourceIdentifier();
		if(StringHelper.isBlank(string))
			return super.toString();
		return string;
	}
	*/
	/**/
	
	//public static final String FIELD_SOURCE_IDENTIFIER = "sourceIdentifier";
	
}
