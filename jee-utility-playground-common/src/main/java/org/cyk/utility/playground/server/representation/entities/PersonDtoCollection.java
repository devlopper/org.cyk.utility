package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.playground.server.representation.entities.PersonDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class PersonDtoCollection extends AbstractEntityCollection<PersonDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	
}
