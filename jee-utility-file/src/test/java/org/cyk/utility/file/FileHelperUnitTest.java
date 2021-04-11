package org.cyk.utility.file;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class FileHelperUnitTest extends AbstractWeldUnitTest {
	
	@Test
	public void computeSha1() {
		assertThat(FileHelper.computeSha1(new String("Hello").getBytes())).isEqualTo("f7ff9e8b7bb2e09b70935a5d785e0cc5d9d0abf0");	
	}	
}