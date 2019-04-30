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
	
	@Test
	public void isNameDotTxt_whenNameIsNameAndExtensionIsTxt() {
		assertionHelper.assertEquals("name.txt", __inject__(FileHelper.class).concatenateNameAndExtension("name", "txt"));
	}
	
	@Test
	public void isDotTxt_whenExtensionIsTxt() {
		assertionHelper.assertEquals(".txt", __inject__(FileHelper.class).concatenateNameAndExtension(null, "txt"));
	}
	
	@Test
	public void isName_whenNameIsName() {
		assertionHelper.assertEquals("name", __inject__(FileHelper.class).concatenateNameAndExtension("name", null));
	}
}
