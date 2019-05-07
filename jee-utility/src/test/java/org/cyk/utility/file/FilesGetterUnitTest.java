package org.cyk.utility.file;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.cyk.utility.time.DurationStringBuilder;
import org.junit.Test;

public class FilesGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void get_sequantially() {
		FilesGetter filesGetter = __inject__(FilesGetter.class);
		filesGetter.addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		Files files = filesGetter.execute().getOutput();
		assertThat(files).withFailMessage("No file found").isNotNull();
		assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(4).contains("f01.txt");
		files.removeDuplicateByChecksum();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(3).contains("f01.txt");
	}
	
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
	
	@Test
	public void checkPerformance_computeChecksum_sequentially() {
		String directory = System.getProperty("org.cyk.utility.file.FilesGetterUnitTest.performance.directory");
		if(__inject__(StringHelper.class).isNotBlank(directory)) {
			System.out.println("Performance(Compute checksum) testing with directory : "+directory);
			FilesGetter filesGetter = __inject__(FilesGetter.class);
			filesGetter.addDirectories(directory);
			Files files = filesGetter.execute().getOutput();
			assertThat(files).withFailMessage("No file found").isNotNull();
			assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
			System.out.println("Number of file read for performance testing 01 : "+__inject__(CollectionHelper.class).getSize(files));
			DurationStringBuilder durationStringBuilder = __inject__(DurationStringBuilder.class);
			durationStringBuilder.getDurationBuilder(Boolean.TRUE).setBeginToNow();
			files.computeChecksum(Boolean.TRUE);
			durationStringBuilder.getDurationBuilder(Boolean.TRUE).setEndNow();
			System.out.println("Checksum computed : "+durationStringBuilder.execute().getOutput());
			files.removeDuplicateByChecksum();
			System.out.println("Number of file read for performance testing 02 : "+__inject__(CollectionHelper.class).getSize(files));
		}else {
			System.out.println("No performance testing done for FilesGetter");
		}
	}
	
	@Test
	public void checkPerformance_computeChecksum() {
		String directory = System.getProperty("org.cyk.utility.file.FilesGetterUnitTest.performance.directory");
		if(__inject__(StringHelper.class).isNotBlank(directory)) {
			System.out.println("Performance(Compute checksum) testing with directory : "+directory);
			FilesGetter filesGetter = __inject__(FilesGetter.class);
			filesGetter.addDirectories(directory);
			filesGetter.setIsFileChecksumComputable(Boolean.TRUE).setIsFilterByFileChecksum(Boolean.TRUE);
			Files files = filesGetter.execute().getOutput();
			assertThat(files).withFailMessage("No file found").isNotNull();
			assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
			System.out.println("Number of file read for performance testing : "+__inject__(CollectionHelper.class).getSize(files));
		}else {
			System.out.println("No performance testing done for FilesGetter");
		}
	}
	
	@Test
	public void checkPerformance_doNotComputeChecksum() {
		String directory = System.getProperty("org.cyk.utility.file.FilesGetterUnitTest.performance.directory");
		if(__inject__(StringHelper.class).isNotBlank(directory)) {
			System.out.println("Performance(Do not compute checksum) testing with directory : "+directory);
			FilesGetter filesGetter = __inject__(FilesGetter.class);
			filesGetter.addDirectories(directory);
			filesGetter.setIsFileChecksumComputable(Boolean.FALSE);
			Files files = filesGetter.execute().getOutput();
			assertThat(files).withFailMessage("No file found").isNotNull();
			assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
			System.out.println("Number of file read for performance testing : "+__inject__(CollectionHelper.class).getSize(files));
		}else {
			System.out.println("No performance testing done for FilesGetter");
		}
	}
	
}
