package org.cyk.utility.server.representation.entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.field.container.FieldContainerCollectionChildren;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractIdentifiedByStringAndCodedImpl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class ParentDto extends AbstractIdentifiedByStringAndCodedImpl implements FieldContainerCollectionChildren<ChildDto>,Serializable {	
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastNames;
	private ParentTypeDto type;
	private ArrayList<ChildDto> children;
	
	@Override
	public ParentDto setIdentifier(String identifier) {
		return (ParentDto) super.setIdentifier(identifier);
	}
	
	@Override
	public ParentDto setCode(String code) {
		return (ParentDto) super.setCode(code);
	}
	
	@Override
	public Class<ChildDto> getChildrenClass() {
		return ChildDto.class;
	}
	
}
