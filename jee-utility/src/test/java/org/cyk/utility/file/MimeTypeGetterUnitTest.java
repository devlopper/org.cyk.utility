package org.cyk.utility.file;

import org.cyk.utility.file.MimeTypeGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class MimeTypeGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isTextPlain_whenTxt() {
		assertionHelper.assertEquals("text/plain", __inject__(MimeTypeGetter.class).setExtension("txt").execute().getOutput());
	}
	
	
}
