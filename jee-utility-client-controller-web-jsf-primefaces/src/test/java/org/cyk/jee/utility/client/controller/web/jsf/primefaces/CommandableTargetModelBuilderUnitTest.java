package org.cyk.jee.utility.client.controller.web.jsf.primefaces;

import static org.junit.Assert.assertNotNull;

import org.cyk.utility.client.controller.component.command.Commandable;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.ApplicationScopeLifeCycleListener;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;
import org.primefaces.component.commandbutton.CommandButton;

public class CommandableTargetModelBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;

	static {
		__inject__(ApplicationScopeLifeCycleListener.class).initialize(null);
	}
	
	@Test
	public void build() {
		CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class).setName("MyServerAction")
				.setCommandFunctionActionClass(SystemActionCreate.class).addCommandFunctionTryRunRunnable(new Runnable() {
					@Override
					public void run() {}
				});
		Commandable commandable = commandableBuilder.execute().getOutput();
		CommandButton commandButton = (CommandButton) commandable.getTargetModel();
		assertNotNull(commandButton);
	}
	
}
