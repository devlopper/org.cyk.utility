package org.cyk.utility.file;

import static org.cyk.utility.file.FileHelperImpl.__computeChecksum__;
import static org.cyk.utility.file.FileHelperImpl.__getPaths__;
import static org.cyk.utility.file.FileHelperImpl.__get__;

import java.util.Arrays;

import org.cyk.utility.test.weld.AbstractWeldUnitTestPerformance;
import org.junit.jupiter.api.Test;

public class FileHelperUnitTestPerformance extends AbstractWeldUnitTestPerformance {
	private static final long serialVersionUID = 1L;

	@Test
	public void getPaths(){
		execute("Get paths of big directory",1,1000,new Runnable() {
			@Override
			public void run() {
				Paths paths = __getPaths__(Arrays.asList("C:\\Users\\CYK\\Downloads\\Partitions"),null, Boolean.FALSE, null, null);
				System.out.println("#paths : "+paths.getSize());
			}
		});
	}	
	
	@Test
	public void get(){
		execute("Get files big directory",1,3000,new Runnable() {
			@Override
			public void run() {
				Paths paths = __getPaths__(Arrays.asList("C:\\Users\\CYK\\Downloads\\Partitions"),null, Boolean.FALSE, null, null);
				Files files = __get__(paths, null);
				System.out.println("#files : "+files.getSize());
			}
		});
	}	
	
	@Test
	public void computeChecksum(){
		execute("Get field",1,1000*60,new Runnable() {
			@Override
			public void run() {
				Paths paths = __getPaths__(Arrays.asList("C:\\Users\\CYK\\Downloads\\Partitions"),null, Boolean.FALSE, null, null);
				Files files = __get__(paths, null);
				__computeChecksum__(files);
				System.out.println("#checksums : "+files.getSize());
				files.removeDuplicateByChecksum();
				System.out.println("#checksums not duplicated : "+files.getSize());
			}
		});
	}	
	
}
