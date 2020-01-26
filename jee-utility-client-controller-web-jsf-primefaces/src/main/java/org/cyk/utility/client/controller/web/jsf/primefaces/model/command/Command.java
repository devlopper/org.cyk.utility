package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;

import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Command extends AbstractObject implements Serializable {

	private String value,update,process;
	
	/**/
	
	
}
