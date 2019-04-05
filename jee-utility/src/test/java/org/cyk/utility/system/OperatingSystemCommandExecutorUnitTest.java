package org.cyk.utility.system;

import org.cyk.utility.string.StringHelper;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class OperatingSystemCommandExecutorUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void hasOutput_whenCommandIsDirAndNoTerminal() {
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("dir").setIsTerminalStartable(Boolean.FALSE);
		operatingSystemCommandExecutor.execute();
		assertionHelper.assertTrue("output not found", __inject__(StringHelper.class).isNotBlank(operatingSystemCommandExecutor.getResult()));
	}
	
	@Test
	public void hasNoOutput_whenCommandIsDirAndHasTerminalAndTerminalClosedImmediately() {
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("dir").setIsTerminalStartable(Boolean.TRUE).setIsTerminalShowable(Boolean.TRUE).setIsStartedTerminalClosable(Boolean.TRUE);
		operatingSystemCommandExecutor.execute();
		assertionHelper.assertTrue("output found", __inject__(StringHelper.class).isBlank(operatingSystemCommandExecutor.getResult()));
	}
	
	@Test
	public void hasNoOutput_whenCommandIsDirAndHasTerminalAndTerminalNotClosedImmediately() {
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("timeout 3").setIsTerminalStartable(Boolean.TRUE).setIsStartedTerminalClosable(Boolean.TRUE);
		operatingSystemCommandExecutor.execute();
		assertionHelper.assertTrue("output found", __inject__(StringHelper.class).isBlank(operatingSystemCommandExecutor.getResult()));
	}
}
