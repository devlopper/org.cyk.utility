package org.cyk.utility.__kernel__.user.interface_.message;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Message extends AbstractObject implements Serializable {

	private String summary;
	private String details;
	private Severity severity;
	
}
