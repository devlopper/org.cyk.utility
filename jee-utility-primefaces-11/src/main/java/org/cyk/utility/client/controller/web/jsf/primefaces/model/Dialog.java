package org.cyk.utility.client.controller.web.jsf.primefaces.model;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.command.Commandable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Dialog extends AbstractObject implements Serializable {

	private String title;
	private Commandable commandable;
}
