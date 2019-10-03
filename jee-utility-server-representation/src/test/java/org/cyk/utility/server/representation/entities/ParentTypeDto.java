package org.cyk.utility.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiedByStringAndCodedAndNamedImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class ParentTypeDto extends AbstractIdentifiedByStringAndCodedAndNamedImpl implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Override
	public ParentTypeDto setIdentifier(String identifier) {
		return (ParentTypeDto) super.setIdentifier(identifier);
	}
	
	@Override
	public ParentTypeDto setCode(String code) {
		return (ParentTypeDto) super.setCode(code);
	}
	
	@Override
	public ParentTypeDto setName(String name) {
		return (ParentTypeDto) super.setName(name);
	}
	
}
