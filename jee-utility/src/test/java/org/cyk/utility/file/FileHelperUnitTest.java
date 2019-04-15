package org.cyk.utility.file;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class FileHelperUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isTxt_whenNameDotTxt() {
		assertionHelper.assertEquals("txt", __inject__(FileHelper.class).getExtension("name.txt"));
	}
	
	@Test
	public void isTxt_whenDotTxt() {
		assertionHelper.assertEquals("txt", __inject__(FileHelper.class).getExtension(".txt"));
	}
	
	@Test
	public void isTxt_whenTxt() {
		assertionHelper.assertEquals(null, __inject__(FileHelper.class).getExtension("txt"));
	}
}
