package org.cyk.utility.service.entity;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractIdentifiableSystemImpl<IDENTIFIER> extends AbstractObject implements IdentifiableSystem<IDENTIFIER>,Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String asString;
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_AS_STRING = "asString";
}
