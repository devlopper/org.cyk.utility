package org.cyk.utility.server.representation.hierarchy;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractHierarchy<ENTITY extends AbstractEntityFromPersistenceEntityCodedAndNamed<ENTITY,?>> extends AbstractEntityFromPersistenceEntity implements Serializable {	
	private static final long serialVersionUID = 1L;

	private ENTITY parent;
	private ENTITY child;
	
}
