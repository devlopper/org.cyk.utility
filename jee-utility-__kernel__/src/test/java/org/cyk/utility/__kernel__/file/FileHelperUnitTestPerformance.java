package org.cyk.utility.__kernel__.file;

import static org.cyk.utility.__kernel__.file.FileHelper.computeChecksum;
import static org.cyk.utility.__kernel__.file.FileHelper.getPaths;
import static org.cyk.utility.__kernel__.file.FileHelper.get;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

public class FileHelperUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void getPaths_(){
		execute("Get paths of big directory",1,1000,new Runnable() {
			@Override
			public void run() {
				Collection<Path> paths = getPaths(Arrays.asList("C:\\Users\\CYK\\Downloads\\Partitions"),null, Boolean.FALSE, null, null);
				System.out.println("#paths : "+paths.size());
			}
		});
	}	
	
	@Test
	public void get_(){
		execute("Get files big directory",1,3000,new Runnable() {
			@Override
			public void run() {
				Collection<Path> paths = getPaths(Arrays.asList("C:\\Users\\CYK\\Downloads\\Partitions"),null, Boolean.FALSE, null, null);
				Files files = get(paths, null);
				System.out.println("#files : "+files.getSize());
			}
		});
	}	
	
	@Test
	public void computeChecksum_(){
		execute("Get field",1,1000*60,new Runnable() {
			@Override
			public void run() {
				Collection<Path> paths = getPaths(Arrays.asList("C:\\Users\\CYK\\Downloads\\Partitions"),null, Boolean.FALSE, null, null);
				Files files = get(paths, null);
				computeChecksum(files);
				System.out.println("#checksums : "+files.getSize());
				files.removeDuplicateByChecksum();
				System.out.println("#checksums not duplicated : "+files.getSize());
			}
		});
	}	
	
}
