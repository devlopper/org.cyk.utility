package org.cyk.utility.common;

import java.util.Locale;

import org.cyk.utility.common.formatter.Formatter.CharacterSet;
import org.cyk.utility.common.formatter.NumberFormatter;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class NumberFormatterUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
		
	@Test
	public void assertRankDigit(){
		NumberFormatter.String formatter = new NumberFormatter.String.Adapter.Default(null,null);
		formatter.setIsRank(Boolean.TRUE);
		formatter.setLocale(Locale.ENGLISH);
		formatter.setIsAppendNumberSuffix(Boolean.TRUE);
		formatter.setInput(1);
		assertEquals("1st", formatter.execute());
		
		formatter.setInput(2);
		assertEquals("2nd", formatter.execute());
		
		formatter.setInput(3);
		assertEquals("3rd", formatter.execute());
		
		formatter.setInput(3);
		formatter.setIsAppendExaequo(Boolean.TRUE);
		assertEquals("3rd exaequo", formatter.execute());
	}
	
	@Test
	public void assertRankLetter(){
		NumberFormatter.String formatter = new NumberFormatter.String.Adapter.Default(null,null);
		formatter.setIsRank(Boolean.TRUE);
		formatter.setLocale(Locale.ENGLISH);
		formatter.setCharacterSet(CharacterSet.LETTER);
		formatter.setIsAppendNumberSuffix(Boolean.TRUE);
		formatter.setInput(1);
		assertEquals("first", formatter.execute());
		
		formatter.setInput(2);
		assertEquals("second", formatter.execute());
		
		formatter.setInput(3);
		assertEquals("third", formatter.execute());
		
		formatter.setInput(3);
		formatter.setIsAppendExaequo(Boolean.TRUE);
		assertEquals("third exaequo", formatter.execute());
	}
	
}
