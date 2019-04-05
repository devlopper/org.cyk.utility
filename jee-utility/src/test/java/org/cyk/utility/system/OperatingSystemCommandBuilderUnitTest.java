package org.cyk.utility.system;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class OperatingSystemCommandBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void cmd_c_dir_whenDirTerminalStartableNo() {
		assertionHelper.assertEquals("cmd /c dir", __inject__(OperatingSystemCommandBuilder.class).setCommand("dir").execute().getOutput());
	}
	
	@Test
	public void cmd_c_dir_whenDirTerminalStartableYes() {
		assertionHelper.assertEquals("cmd /c start \"List directory\" /b dir", __inject__(OperatingSystemCommandBuilder.class).setCommand("dir").setIsTerminalStartable(Boolean.TRUE)
				.setTerminalTitle("List directory").execute().getOutput());
	}
	
	@Test
	public void cmd_c_dir_whenDirTerminalStartableYesWorkingDirectoryIsMyDir() {
		assertionHelper.assertEquals("cmd /c start \"List directory\" /b /d \"my local directory\" dir", __inject__(OperatingSystemCommandBuilder.class).setCommand("dir").setIsTerminalStartable(Boolean.TRUE)
				.setTerminalTitle("List directory").setWorkingDirectory("my local directory").execute().getOutput());
	}
}
