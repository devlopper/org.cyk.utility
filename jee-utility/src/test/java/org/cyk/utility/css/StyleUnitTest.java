package org.cyk.utility.css;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class StyleUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getClassesAsString() {
		Style style = __inject__(Style.class);
		assertionHelper.assertEquals(null, style.getClassesAsString());
		style.getClasses(Boolean.TRUE).add("c1");
		assertionHelper.assertEquals("c1", style.getClassesAsString());
		style.getClasses(Boolean.TRUE).add("c2");
		assertionHelper.assertEquals("c1 c2", style.getClassesAsString());
		style.getClasses(Boolean.TRUE).add("c3","c4");
		assertionHelper.assertEquals("c1 c2 c3 c4", style.getClassesAsString());
	}
	
	@Test
	public void getValuesAsString() {
		Style style = __inject__(Style.class);
		assertionHelper.assertEquals(null, style.getValuesAsString());
		style.getValues(Boolean.TRUE).add("c1");
		assertionHelper.assertEquals("c1", style.getValuesAsString());
		style.getValues(Boolean.TRUE).add("c2");
		assertionHelper.assertEquals("c1;c2", style.getValuesAsString());
		style.getValues(Boolean.TRUE).add("c3","c4");
		assertionHelper.assertEquals("c1;c2;c3;c4", style.getValuesAsString());
	}
}
