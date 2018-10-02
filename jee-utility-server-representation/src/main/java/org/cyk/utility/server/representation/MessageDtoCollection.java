package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @ToString
@XmlRootElement @XmlSeeAlso(MessageDto.class)
public class MessageDtoCollection extends AbstractEntityCollection<MessageDto> implements  Serializable {
	private static final long serialVersionUID = 1L;
	
}
