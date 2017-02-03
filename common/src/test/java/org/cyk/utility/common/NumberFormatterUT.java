package org.cyk.utility.common;

import java.util.Locale;

import org.cyk.utility.common.formatter.NumberFormatter;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class NumberFormatterUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Override
	protected void _execute_() {
		super._execute_();
		
	}
	
	@Test
	public void assertRankDigit(){
		NumberFormatter.String formatter = new NumberFormatter.String.Adapter.Default(1,null);
		formatter.setIsRank(Boolean.TRUE);
		formatter.setLocale(Locale.ENGLISH);
		assertEquals("1st", formatter.execute());
		
		formatter = new NumberFormatter.String.Adapter.Default(2,null);
		formatter.setIsRank(Boolean.TRUE);
		formatter.setLocale(Locale.ENGLISH);
		assertEquals("2nd", formatter.execute());
		
		formatter = new NumberFormatter.String.Adapter.Default(3,null);
		formatter.setIsRank(Boolean.TRUE);
		formatter.setLocale(Locale.ENGLISH);
		assertEquals("3rd", formatter.execute());
	}
	
}
