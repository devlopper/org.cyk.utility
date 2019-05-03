package org.cyk.utility.file;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class FileGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void get() {
		FilesGetter filesGetter = __inject__(FilesGetter.class);
		filesGetter.addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		filesGetter.setIsFileChecksumComputable(Boolean.TRUE);
		Files files = filesGetter.execute().getOutput();
		assertThat(files).withFailMessage("No file found").isNotNull();
		assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(4).contains("f01.txt");
	}
	
	@Test
	public void getFilterByChecksum() {
		FilesGetter filesGetter = __inject__(FilesGetter.class);
		filesGetter.addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		filesGetter.setIsFileChecksumComputable(Boolean.TRUE).setIsFilterByFileChecksum(Boolean.TRUE);
		Files files = filesGetter.execute().getOutput();
		assertThat(files).withFailMessage("No file found").isNotNull();
		assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(3).contains("f01.txt");
	}
	
}
