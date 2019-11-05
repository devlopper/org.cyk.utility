package org.cyk.utility.__kernel__.object.__static__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public abstract class AbstractObjectImpl extends AbstractObject implements Object,Serializable {
	private static final long serialVersionUID = 1L;

}
