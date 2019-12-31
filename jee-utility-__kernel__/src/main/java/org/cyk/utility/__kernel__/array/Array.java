package org.cyk.utility.__kernel__.array;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @Dependent
public class Array<T> extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Object value;
	protected Class<?> elementClass;
	
}
