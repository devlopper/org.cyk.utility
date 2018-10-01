package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public abstract class AbstractEntity extends AbstractRepresentationObject implements Serializable {
	private static final long serialVersionUID = 1L;
	

}
