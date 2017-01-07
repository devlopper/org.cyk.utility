package org.cyk.utility.common;

import org.cyk.utility.common.formatter.NumberFormatter;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class FormatterUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void f(){
		assertEquals("123", new NumberFormatter.String.Adapter.Default(123,null).execute().toString());
		//assertEquals("123", new NumberFormatter.String.Adapter.Default(123,null).setCharacterSet(CharacterSet.LETTER).execute().toString());
	}
}
