package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.marker.IdentifiableSystem;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractIdentifiableSystemImpl<IDENTIFIER> extends AbstractObjectImpl implements IdentifiableSystem<IDENTIFIER>,Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	public static final String FIELD_IDENTIFIER = "identifier";
}
