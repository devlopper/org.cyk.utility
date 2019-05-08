package org.cyk.utility.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.List;

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
		filesGetter.getPathsGetter(Boolean.TRUE).addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
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
		filesGetter.getPathsGetter(Boolean.TRUE).addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		filesGetter.setIsFileChecksumComputable(Boolean.TRUE);
		Files files = filesGetter.execute().getOutput();
		assertThat(files).withFailMessage("No file found").isNotNull();
		assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
		assertThat(files.getFirst().getBytes()).withFailMessage("Bytes found").isNull();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(4).contains("f01.txt");
	}
	
	@Test
	public void getWithBytes() {
		FilesGetter filesGetter = __inject__(FilesGetter.class);
		filesGetter.getPathsGetter(Boolean.TRUE).addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
		filesGetter.setIsFileChecksumComputable(Boolean.TRUE).setIsFileBytesComputable(Boolean.TRUE);
		Files files = filesGetter.execute().getOutput();
		assertThat(files).withFailMessage("No file found").isNotNull();
		assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
		assertThat(files.getFirst().getBytes()).withFailMessage("Bytes not found").isNotNull();
		assertThat(files.get().stream().map(x -> x.getNameAndExtension())).hasSize(4).contains("f01.txt");
	}
	
	@Test
	public void getFilterByChecksum() {
		FilesGetter filesGetter = __inject__(FilesGetter.class);
		filesGetter.getPathsGetter(Boolean.TRUE).addDirectoriesByJavaFiles(new java.io.File(new java.io.File(System.getProperty("user.dir")),"src\\test\\resources\\org\\cyk\\utility\\file\\mydirectories"));
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
			filesGetter.getPathsGetter(Boolean.TRUE).addDirectories(directory);
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
			filesGetter.getPathsGetter(Boolean.TRUE).addDirectories(directory);
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
	public void checkPerformance_computeBytesAndComputeChecksum() {
		String directory = System.getProperty("org.cyk.utility.file.FilesGetterUnitTest.performance.directory");
		if(__inject__(StringHelper.class).isNotBlank(directory)) {
			System.out.println("Performance(Compute checksum) testing with directory : "+directory);
			Paths paths = __inject__(PathsGetter.class).addDirectories(directory).setIsDirectoryGettable(Boolean.FALSE).setIsFileGettable(Boolean.TRUE).execute().getOutput();
			System.out.println("Number of files : "+paths.getSize());
			Integer batchSize = 500;
			Integer numberOfPages = paths.getSize() / batchSize + (paths.getSize() % batchSize == 0 ? 0 : 1);
			System.out.println("Page size : "+batchSize+" , number of pages : "+numberOfPages);
			for(Integer index = 0; index < numberOfPages; index = index + 1) {
				Integer from = index * batchSize;
				Integer to = from + batchSize - 1;
				if(to>paths.getSize())
					to = paths.getSize()-1;
				System.out.println("Page from "+from+" to "+to);
				FilesGetter filesGetter = __inject__(FilesGetter.class);
				filesGetter.getPaths(Boolean.TRUE).add( ((List<Path>)paths.get()).subList(from, to+1) );
				filesGetter.setIsFileChecksumComputable(Boolean.TRUE).setIsFilterByFileChecksum(Boolean.TRUE).setIsFileBytesComputable(Boolean.TRUE);
				Files files = filesGetter.execute().getOutput();
				
				assertThat(files).withFailMessage("No file found").isNotNull();
				assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
				assertThat(files.getFirst().getBytes()).withFailMessage("Bytes not found").isNotNull();
				System.out.println("Number of file read for performance testing : "+__inject__(CollectionHelper.class).getSize(files));
			}
			/*
			FilesGetter filesGetter = __inject__(FilesGetter.class);
			filesGetter.getPathsGetter(Boolean.TRUE).addDirectories(directory);
			//filesGetter.setIsFileChecksumComputable(Boolean.TRUE).setIsFilterByFileChecksum(Boolean.TRUE).setIsFileBytesComputable(Boolean.TRUE);
			Files files = filesGetter.execute().getOutput();
			
			assertThat(files).withFailMessage("No file found").isNotNull();
			assertThat(files.get()).withFailMessage("No file found").isNotEmpty();
			assertThat(files.getFirst().getBytes()).withFailMessage("Bytes not found").isNotNull();
			System.out.println("Number of file read for performance testing : "+__inject__(CollectionHelper.class).getSize(files));
			*/
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
			filesGetter.getPathsGetter(Boolean.TRUE).addDirectories(directory);
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
