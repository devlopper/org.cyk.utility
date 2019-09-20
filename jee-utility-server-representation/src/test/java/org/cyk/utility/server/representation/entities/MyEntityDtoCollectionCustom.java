package org.cyk.utility.server.representation.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
@XmlSeeAlso(MyEntityDto.class)
public class MyEntityDtoCollectionCustom implements org.cyk.utility.__kernel__.object.__static__.representation.Collection<MyEntityDto>, Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<MyEntityDto> myEntityDtos;
	
	public MyEntityDtoCollectionCustom add(String identifier,String code,String name) {
		if(myEntityDtos == null)
			myEntityDtos = new ArrayList<>();
		myEntityDtos.add(new MyEntityDto().setIdentifier(identifier).setCode(code).setName(name));
		return this;
	}

	@Override
	public Collection<MyEntityDto> getElements() {
		return myEntityDtos;
	}

	@Override
	public org.cyk.utility.__kernel__.object.__static__.representation.Collection<MyEntityDto> setElements(Collection<MyEntityDto> elements) {
		this.myEntityDtos = elements;
		return this;
	}
	
}
