package org.cyk.utility.client.controller.data.hierarchy;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.annotation.Output;
import org.cyk.utility.client.controller.component.annotation.OutputString;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractDataIdentifiedByStringAndLinkedByStringImpl<NODE> extends AbstractDataIdentifiedByStringImpl<NODE> implements DataIdentifiedByStringAndLinkedByString<NODE>,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Output @OutputString @OutputStringText
	private String link;
		
	@Override
	public String toString() {
		return super.toString()+"/"+getLink();
	}
	
}
