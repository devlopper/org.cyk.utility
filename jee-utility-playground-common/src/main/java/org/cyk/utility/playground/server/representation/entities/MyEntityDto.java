package org.cyk.utility.playground.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntityCodedAndNamed;
import org.cyk.utility.playground.server.representation.entities.MyEntityDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class MyEntityDto extends AbstractEntityFromPersistenceEntityCodedAndNamed implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Override
	public MyEntityDto setCode(String code) {
		return (MyEntityDto) super.setCode(code);
	}
	
	@Override
	public MyEntityDto setName(String name) {
		return (MyEntityDto) super.setName(name);
	}
}
