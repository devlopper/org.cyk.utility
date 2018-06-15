package org.cyk.utility.log.message;

import java.io.Serializable;
import java.util.List;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class LogMessage extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String template;
	private List<Object> arguments;
	/*private String value;
	
	@Override
	public String toString() {
		return value == null ? template : value;
	}*/
	
}
