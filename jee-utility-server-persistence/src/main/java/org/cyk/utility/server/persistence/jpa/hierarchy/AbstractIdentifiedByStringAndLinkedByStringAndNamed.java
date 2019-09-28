package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @Access(AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractIdentifiedByStringAndLinkedByStringAndNamed<ENTITY> extends AbstractIdentifiedByStringAndLinkedByString<ENTITY> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient protected String name;

	@Override
	protected String getRuntimeIdentifier() {
		return getIdentifier()+"/"+getName();
	}
	
	@Override
	public String toString() {
		return getIdentifier()+"/"+getName();
	}
	
	/**/
	
	public static final String FIELD_NAME = "name";
}