package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiedByStringAndLinkedByStringAndNamed extends AbstractIdentifiedByStringAndLinkedByString implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient @JsonbProperty protected String name;

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