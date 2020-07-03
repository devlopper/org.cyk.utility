package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@JsonInclude(Include.NON_NULL) @Getter @Setter @Accessors(chain=true)
public abstract class AbstractObjectImpl extends AbstractObject implements Object,Serializable {
	private static final long serialVersionUID = 1L;

	protected Boolean __deletable__;
	
	/**/
	
	public static final String FIELD___DELETABLE__ = "__deletable__";
}
