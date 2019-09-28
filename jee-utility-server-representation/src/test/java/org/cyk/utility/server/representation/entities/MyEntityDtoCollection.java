package org.cyk.utility.server.representation.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractCollectionOfIdentifiedByStringAndCodedAndNamedImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @NoArgsConstructor
@XmlSeeAlso(MyEntityDto.class) @Getter @Setter @Accessors(chain=true) 
public class MyEntityDtoCollection extends AbstractCollectionOfIdentifiedByStringAndCodedAndNamedImpl<MyEntityDto> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Class<MyEntityDto> elementClass;
	private Collection<MyEntityDto> elements;
	
}
