package org.cyk.utility.system;


import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class OperatingSystemCommandExecutorUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void hasOutput_whenCommandIsDirAndNoTerminal() {
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("dir").setIsTerminalStartable(Boolean.FALSE);
		operatingSystemCommandExecutor.execute();
		assertionHelper.assertTrue("output not found",StringHelper.isNotBlank(operatingSystemCommandExecutor.getResult()));
	}
	
	@Test
	public void hasNoOutput_whenCommandIsDirAndHasTerminalAndTerminalClosedImmediately() {
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("dir").setIsTerminalStartable(Boolean.TRUE).setIsTerminalShowable(Boolean.TRUE).setIsStartedTerminalClosable(Boolean.TRUE);
		operatingSystemCommandExecutor.execute();
		assertionHelper.assertTrue("output found",StringHelper.isBlank(operatingSystemCommandExecutor.getResult()));
	}
	
	@Test
	public void hasNoOutput_whenCommandIsDirAndHasTerminalAndTerminalNotClosedImmediately() {
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("timeout 3").setIsTerminalStartable(Boolean.TRUE).setIsStartedTerminalClosable(Boolean.TRUE);
		operatingSystemCommandExecutor.execute();
		assertionHelper.assertTrue("output found",StringHelper.isBlank(operatingSystemCommandExecutor.getResult()));
	}
}
