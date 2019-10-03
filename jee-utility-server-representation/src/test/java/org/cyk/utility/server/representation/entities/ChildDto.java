package org.cyk.utility.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiedByStringAndCodedAndNamedImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class ChildDto extends AbstractIdentifiedByStringAndCodedAndNamedImpl implements Serializable {	
	private static final long serialVersionUID = 1L;

	private ParentDto parent;
	private String firstName;
	private String lastNames;
	
	@Override
	public ChildDto setIdentifier(String identifier) {
		return (ChildDto) super.setIdentifier(identifier);
	}
	
	@Override
	public ChildDto setCode(String code) {
		return (ChildDto) super.setCode(code);
	}
	
	@Override
	public ChildDto setName(String name) {
		return (ChildDto) super.setName(name);
	}
	
}
