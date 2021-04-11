package org.cyk.utility.file;

import java.nio.file.Path;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionProcessor;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class PathsProcessorUnitTest extends AbstractWeldUnitTest {

	private String rootPath = "src\\test\\resources\\org\\cyk\\utility\\file\\";
	
	@Test
	public void process() {
		Collection<Path> paths = PathsScanner.getInstance().scan(new PathsScanner.Arguments().addPathsFromNames(rootPath));
		PathsProcessor.getInstance().process(paths,new CollectionProcessor.Arguments.Processing.AbstractImpl<Path>() {
			
			@Override
			protected void __process__(Path path) {
				System.out.println("Processing path : "+path);
			}
		});
		
	}
	
}
