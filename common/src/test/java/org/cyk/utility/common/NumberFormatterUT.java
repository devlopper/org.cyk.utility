package org.cyk.utility.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import org.cyk.utility.common.formatter.Formatter.CharacterSet;
import org.cyk.utility.common.formatter.NumberFormatter;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class NumberFormatterUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void assertNumber(){
		NumberFormatter.String formatter = new NumberFormatter.String.Adapter.Default(null,null);
		assertEquals("63", formatter.setInput(new BigDecimal("63.00")).execute());	
	}
	
	@Test
	public void assertOrdinalDigit(){
		NumberFormatter.String formatter = new NumberFormatter.String.Adapter.Default(null,null);
		formatter.setIsOrdinal(Boolean.TRUE);
		formatter.setLocale(Locale.ENGLISH);
		formatter.setIsAppendOrdinalSuffix(Boolean.TRUE);
		formatter.setInput(1);
		assertEquals("1st", formatter.execute());
		
		formatter.setInput(2);
		assertEquals("2nd", formatter.execute());
		
		formatter.setInput(3);
		assertEquals("3rd", formatter.execute());
		
		formatter.setInput(3);
		formatter.setIsAppendExaequo(Boolean.TRUE);
		assertEquals("3rd exaequo", formatter.execute());
		
		formatter.setInput(22);
		formatter.setIsAppendExaequo(Boolean.FALSE);
		assertEquals("22nd", formatter.execute());
	}
	
	@Test
	public void assertOrdinalLetter(){
		NumberFormatter.String formatter = new NumberFormatter.String.Adapter.Default(null,null);
		formatter.setIsOrdinal(Boolean.TRUE);
		formatter.setLocale(Locale.ENGLISH);
		formatter.setCharacterSet(CharacterSet.LETTER);
		formatter.setIsAppendOrdinalSuffix(Boolean.TRUE);
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
	
	@Test
	public void assertEnglishLetter(){
		NumberFormatter.String formatter = new NumberFormatter.String.Adapter.Default(null,null);
		formatter.setLocale(Locale.ENGLISH);
		formatter.setCharacterSet(CharacterSet.LETTER);
		assertEquals("zero", formatter.setInput(0).execute());
		assertEquals("one", formatter.setInput(1).execute());
		assertEquals("two", formatter.setInput(2).execute());
		assertEquals("three", formatter.setInput(3).execute());
		assertEquals("four", formatter.setInput(4).execute());
		assertEquals("five", formatter.setInput(5).execute());
		assertEquals("fifteen", formatter.setInput(15).execute());
		assertEquals("forty nine", formatter.setInput(49).execute());
	}
	
	@Test
	public void assertPercentage(){
		NumberFormatter.String formatter = new NumberFormatter.String.Adapter.Default(null,null);
		formatter.setIsPercentage(Boolean.TRUE);
		formatter.setLocale(Locale.FRENCH);
		assertEquals("33,33 %", formatter.setInput( new BigDecimal("1").divide(new BigDecimal("3"),4,RoundingMode.HALF_DOWN)).execute());
	}
	
}
