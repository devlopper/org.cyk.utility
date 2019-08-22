package org.cyk.utility.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntityCodedAndNamed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class MyEntityDto extends AbstractEntityFromPersistenceEntityCodedAndNamed implements Serializable {	
	private static final long serialVersionUID = 1L;

	private Long long1;
	private Long long2;
	private Integer integerValue;
	
	@Override
	public MyEntityDto setIdentifier(String identifier) {
		return (MyEntityDto) super.setIdentifier(identifier);
	}
	
	@Override
	public MyEntityDto setCode(String code) {
		return (MyEntityDto) super.setCode(code);
	}
	
	@Override
	public MyEntityDto setName(String name) {
		return (MyEntityDto) super.setName(name);
	}
	
	@Override
	public String toString() {
		return getIdentifier()+":"+getCode();
	}
}
