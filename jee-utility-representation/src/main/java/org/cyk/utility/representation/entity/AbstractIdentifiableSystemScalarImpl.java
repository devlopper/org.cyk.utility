package org.cyk.utility.representation.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true)
public abstract class AbstractIdentifiableSystemScalarImpl<IDENTIFIER> extends AbstractIdentifiableSystemImpl<IDENTIFIER> implements Serializable {
	private static final long serialVersionUID = 1L;

}
