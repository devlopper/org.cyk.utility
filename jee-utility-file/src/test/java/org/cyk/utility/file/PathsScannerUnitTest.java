package org.cyk.utility.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class PathsScannerUnitTest extends AbstractWeldUnitTest {

	private String rootPath = "src\\test\\resources\\org\\cyk\\utility\\file\\";
	
	@Test
	public void scan_directory() {
		Collection<Path> paths = PathsScanner.getInstance().scan(new PathsScanner.Arguments().addPathsFromNames(rootPath));
		assertThat(paths).isNotEmpty();
		assertThat(paths.stream().map(x -> StringUtils.substringAfter(x.toFile().getAbsolutePath(),rootPath)).collect(Collectors.toList()))
			.containsExactly("d1","d2","d2\\d1","d2\\f1.txt","f1.txt");
	}
	
	@Test
	public void scan_acceptedPathNameRegularExpression() {
		Collection<Path> paths = PathsScanner.getInstance().scan(new PathsScanner.Arguments().addPathsFromNames(rootPath).setAcceptedPathNameRegularExpression(".txt"));
		assertThat(paths).isNotEmpty();
		assertThat(paths.stream().map(x -> StringUtils.substringAfter(x.toFile().getAbsolutePath(),rootPath)).collect(Collectors.toList()))
			.containsExactly("d2\\f1.txt","f1.txt");
		/*
		paths = PathsScanner.getInstance().scan(new PathsScanner.Arguments().addPathsFromNames(rootPath).setAcceptedPathNameRegularExpression("d2"));
		assertThat(paths).isNotEmpty();
		assertThat(paths.stream().map(x -> StringUtils.substringAfter(x.toFile().getAbsolutePath(),rootPath)).collect(Collectors.toList()))
			.containsExactly("d2\\d1","d2\\f1.txt");
		*/
	}
	
	@Test
	public void scan_directory_returnFilesOnly() {
		Collection<Path> paths = PathsScanner.getInstance().scan(new PathsScanner.Arguments().addPathsFromNames(rootPath).setIsDiretoryReturnable(Boolean.FALSE)
				.setIsFileReturnable(Boolean.TRUE));
		assertThat(paths).isNotEmpty();
		assertThat(paths.stream().map(x -> StringUtils.substringAfter(x.toFile().getAbsolutePath(),rootPath)).collect(Collectors.toList()))
			.containsExactly("d2\\f1.txt","f1.txt");
	}
	
	@Test
	public void scan_directory_returnDirectoriesOnly() {
		Collection<Path> paths = PathsScanner.getInstance().scan(new PathsScanner.Arguments().addPathsFromNames(rootPath).setIsDiretoryReturnable(Boolean.TRUE)
				.setIsFileReturnable(Boolean.FALSE));
		assertThat(paths).isNotEmpty();
		assertThat(paths.stream().map(x -> StringUtils.substringAfter(x.toFile().getAbsolutePath(),rootPath)).collect(Collectors.toList()))
			.containsExactly("d1","d2","d2\\d1");
	}
	
	@Test
	public void scan_directory_returnNothing() {
		Collection<Path> paths = PathsScanner.getInstance().scan(new PathsScanner.Arguments().addPathsFromNames(rootPath).setIsDiretoryReturnable(Boolean.FALSE)
				.setIsFileReturnable(Boolean.FALSE));
		assertThat(paths).isNull();
	}
	
	@Test
	public void scan_file() {
		Collection<Path> paths = PathsScanner.getInstance().scan(new PathsScanner.Arguments().addPathsFromNames(rootPath+"\\f1.txt"));
		assertThat(paths).isNotEmpty();
		assertThat(paths.stream().map(x -> StringUtils.substringAfter(x.toFile().getAbsolutePath(),rootPath)).collect(Collectors.toList())).containsExactly("f1.txt");
	}
}
