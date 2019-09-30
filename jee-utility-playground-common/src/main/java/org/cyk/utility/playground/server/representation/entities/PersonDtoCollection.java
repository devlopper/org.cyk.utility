package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractCollectionOfIdentifiedByStringAndCodedImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class PersonDtoCollection extends AbstractCollectionOfIdentifiedByStringAndCodedImpl<PersonDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<PersonDto> elements;
}
