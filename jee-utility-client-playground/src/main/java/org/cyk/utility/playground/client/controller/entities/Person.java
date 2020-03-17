package org.cyk.utility.playground.client.controller.entities;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputChoice;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOne;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.annotation.Output;
import org.cyk.utility.client.controller.component.annotation.OutputString;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;
import org.cyk.utility.client.controller.data.AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Person extends AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Input @InputString @InputStringLineOne private String firstName;
	@Input @InputString @InputStringLineOne private String lastNames;
	@Output @OutputString @OutputStringText private String output;
	@Input @InputChoice @InputChoiceOne @InputChoiceOneCombo private PersonType type;

	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_LAST_NAMES = "lastNames";
	public static final String FIELD_TYPE = "type";
	
}
