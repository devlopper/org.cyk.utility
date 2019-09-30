package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractCollectionImpl<ELEMENT> extends org.cyk.utility.__kernel__.object.AbstractObject implements Collection<ELEMENT>,Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	@JsonbTransient
	@Getter @Setter @Accessors(chain=true)
	private Class<ELEMENT> elementClass;
	
	@Override
	public String toString() {
		java.util.Collection<ELEMENT> elements = getElements();
		return elements == null ? null : elements.toString();
	}
}
