package org.cyk.utility.__kernel__;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractAsFunctionParameterIdentified<VALUE,IDENTIFIER> extends AbstractAsFunctionParameter<VALUE> {

	private IDENTIFIER identifier;
	
}
