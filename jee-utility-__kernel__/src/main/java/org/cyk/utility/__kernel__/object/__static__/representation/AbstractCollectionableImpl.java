package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractCollectionableImpl<ELEMENT> extends AbstractObjectImpl implements Collectionable<ELEMENT>,Serializable {
	private static final long serialVersionUID = 120190653223178348L;

	@JsonbTransient
	@XmlTransient
	private Class<ELEMENT> elementClass;
	
	@Override
	public String toString() {
		java.util.Collection<ELEMENT> elements = getElements();
		if(elements == null)
			return super.toString();
		return elements.toString();
	}
}
