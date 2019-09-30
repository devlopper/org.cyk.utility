package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractCollectionOfIdentifiedByStringImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class SelectedNodeDtoCollection extends AbstractCollectionOfIdentifiedByStringImpl<SelectedNodeDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	
}
