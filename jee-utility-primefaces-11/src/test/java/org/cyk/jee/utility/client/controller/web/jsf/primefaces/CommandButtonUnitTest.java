package org.cyk.jee.utility.client.controller.web.jsf.primefaces;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class CommandButtonUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;

	@Test
	public void build() {
		CommandButton commandButton = Builder.build(CommandButton.class);
		assertThat(commandButton.getIdentifier()).isNotBlank();
	}
	
}
