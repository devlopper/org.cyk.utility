package org.cyk.utility.common;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import org.cyk.utility.common.formatter.DateFormatter;
import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.joda.time.DateTime;
import org.junit.Test;

import lombok.Getter;

public class DateFormatterUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void assertDate(){
		assertDate(2,5,2016);	
		
		MyGeneratableClass g = new MyGeneratableClass();
		g.generate();
		System.out.println(g.getC());
	
	}
	
	private void assertDate(Integer day,Integer month,Integer year){
		for(Locale locale : new Locale[]{Locale.FRENCH,Locale.ENGLISH}){
			Date date = date(2016, 5, 2, 10, 30, 0, 0);
			DateTime dateTime = new DateTime(date);
			
			DateFormatter.String formatter = new DateFormatter.String.Adapter.Default(date,null);
			formatter.setPart(Constant.Date.Part.DATE_ONLY).setLength(Constant.Date.Length.SHORT).setLocale(locale);
			assertEquals(String.format("%02d/%02d/%04d", day,month,year), formatter.execute());
			
			formatter.setLength(Constant.Date.Length.LONG);
			assertEquals(String.format("%s , %02d %s %04d", dateTime.dayOfWeek().getAsText(locale),day,dateTime.monthOfYear().getAsText(locale),year), formatter.execute());
		}
	}
	
	/**/
	
	@Getter
	public static class MyGeneratableClass extends AbstractGeneratable<MyGeneratableClass> implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String c;

		@Override
		public void generate() {
			c = formatDate(new Date(), Constant.Date.Part.DATE_AND_TIME, Constant.Date.Length.LONG);
		}
		
	}
}
