package org.cyk.utility.server.representation.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class NodeHierarchyDto extends AbstractEntityFromPersistenceEntity implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Override
	public NodeHierarchyDto setIdentifier(String identifier) {
		return (NodeHierarchyDto) super.setIdentifier(identifier);
	}
	
}
