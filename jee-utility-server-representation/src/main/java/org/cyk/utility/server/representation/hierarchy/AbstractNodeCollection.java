package org.cyk.utility.server.representation.hierarchy;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractNodeCollection<ENTITY extends AbstractNodeCodedAndNamed<ENTITY,?>> extends org.cyk.utility.server.representation.AbstractEntityCollection<ENTITY> implements Serializable {
	private static final long serialVersionUID = 1L;
	
}
