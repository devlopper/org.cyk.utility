package org.cyk.utility.file;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class PathsGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getDirecoriesAndFile() {
		PathsGetter pathsGetter = __inject__(PathsGetter.class);
		pathsGetter.addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		Paths paths = pathsGetter.execute().getOutput();
		assertThat(paths).withFailMessage("No path found").isNotNull();
		assertThat(paths.get()).withFailMessage("No path found").isNotEmpty();
		assertThat(paths.get()).hasSize(8);
	}
	
	@Test
	public void getDirectories() {
		PathsGetter pathsGetter = __inject__(PathsGetter.class).setIsDirectoryGettable(Boolean.TRUE).setIsFileGettable(Boolean.FALSE);
		pathsGetter.addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		Paths paths = pathsGetter.execute().getOutput();
		assertThat(paths).withFailMessage("No path found").isNotNull();
		assertThat(paths.get()).withFailMessage("No path found").isNotEmpty();
		assertThat(paths.get()).hasSize(4);
	}
	
	@Test
	public void getFiles() {
		PathsGetter pathsGetter = __inject__(PathsGetter.class).setIsDirectoryGettable(Boolean.FALSE).setIsFileGettable(Boolean.TRUE);
		pathsGetter.addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		Paths paths = pathsGetter.execute().getOutput();
		assertThat(paths).withFailMessage("No path found").isNotNull();
		assertThat(paths.get()).withFailMessage("No path found").isNotEmpty();
		assertThat(paths.get()).hasSize(4);
	}
}
