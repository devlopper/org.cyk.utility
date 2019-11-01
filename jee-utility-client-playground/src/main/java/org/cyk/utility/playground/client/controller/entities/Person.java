package org.cyk.utility.playground.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.data.AbstractDataIdentifiableSystemStringAndIdentifiableBusinessStringImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Person extends AbstractDataIdentifiableSystemStringAndIdentifiableBusinessStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne private String firstName;
	@Input @InputString @InputStringLineOne private String lastNames;

}
