package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @AllArgsConstructor
public class MessageDto extends AbstractObject implements Serializable {

	private Object identifier;
	private String summary;
	private String details;
	
}
