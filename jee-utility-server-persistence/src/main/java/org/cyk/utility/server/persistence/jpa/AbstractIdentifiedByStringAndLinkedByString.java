package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @MappedSuperclass @Access(AccessType.FIELD)
public abstract class AbstractIdentifiedByStringAndLinkedByString extends AbstractIdentifiedByString implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient protected String link;

	@Override
	protected String getRuntimeIdentifier() {
		return getIdentifier()+"/"+getLink();
	}
	
	@Override
	public String toString() {
		return getIdentifier()+"/"+getLink();
	}
	
	/**/
	
	public static final String FIELD_LINK = "link";
	
}