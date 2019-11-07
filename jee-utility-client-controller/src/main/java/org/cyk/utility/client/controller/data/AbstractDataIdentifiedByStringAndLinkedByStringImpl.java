package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractDataIdentifiedByStringAndLinkedByStringImpl extends AbstractDataIdentifiedByStringImpl implements DataIdentifiedByStringAndLinkedByString,Serializable {
	private static final long serialVersionUID = 1L;
	
	//@Output @OutputString @OutputStringText
	private String link;
		
	@Override
	public String toString() {
		return super.toString()+"/"+getLink();
	}
	
}
