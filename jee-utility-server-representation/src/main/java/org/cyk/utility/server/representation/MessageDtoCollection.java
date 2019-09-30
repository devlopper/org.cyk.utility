package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractCollectionImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlRootElement @XmlSeeAlso(MessageDto.class)
public class MessageDtoCollection extends AbstractCollectionImpl<MessageDto> implements  Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<MessageDto> elements;
	
}
