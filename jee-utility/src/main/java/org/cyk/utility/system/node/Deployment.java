package org.cyk.utility.system.node;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Deployment extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String uniformResourceIdentifierPathRoot;
	
}
