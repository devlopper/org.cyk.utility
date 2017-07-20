package org.cyk.utility.common;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lombok.Getter;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.utility.common.builder.DateBuilder;
import org.cyk.utility.common.builder.DatePeriodStringBuilder;
import org.cyk.utility.common.builder.DateStringBuilder;
import org.cyk.utility.common.formatter.DateFormatter;
import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.helper.DateHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class DateHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void build(){
		Date date = new DateHelper.Builder.String.Adapter.Default("19/11/2015").execute();
		assertEquals(19, DateHelper.getInstance().getDayOfMonth(date));
		assertEquals(11, DateHelper.getInstance().getMonthOfYear(date));
		assertEquals(2015, DateHelper.getInstance().getYear(date));
	}
	
	//@Test
	public void stringBuilder(){
		assertEquals("01/01/2000 00:00", new DateStringBuilder().setDate(1,1,2000,0,0,0).build());
		assertEquals("01/01/2000", new DateStringBuilder().setDate(1,1,2000,0,0,0).setPart(Constant.Date.Part.DATE_ONLY).build());
		assertEquals("2000", new DateStringBuilder().setDate(1,1,2000,0,0,0).setPart(Constant.Date.Part.DATE_YEAR_ONLY).build());
		
		assertEquals(Constant.EMPTY_STRING, new DateStringBuilder().build());
		assertEquals(Constant.EMPTY_STRING, new DateStringBuilder().setDate(null).build());
		
		assertEquals("2000-2001", new DatePeriodStringBuilder(new DateBuilder(1, 1, 2000, 0, 0, 0), new DateBuilder(1, 1, 2001, 0, 0, 0), Constant.Date.Part.DATE_YEAR_ONLY)
			.build());
	}
	
	//@Test
	public void dateFormatter(){
		assertDate(2,5,2016);	
		
		MyGeneratableClass g = new MyGeneratableClass();
		g.generate();
		//System.out.println(g.getC());
	
	}
	
	//@Test
	public void jodaTime(){
		DateTime fromDate = new DateTime(createDate(1, 1, 2000, 10, 0)),toDate = new DateTime(createDate(27, 1, 2000, 11, 30));
		int numberOfDays = Days.daysBetween(fromDate,toDate).getDays();
		List<LocalDate> dates = new ArrayList<LocalDate>();
		for (int i=0; i < numberOfDays; i++)
		    dates.add(fromDate.plusDays(i).toLocalDate());
		
		Assert.assertEquals(26, numberOfDays);
		
		Assert.assertEquals(0,Days.daysIn(new Interval(jdt(1, 1, 2000, 0, 0), jdt(1, 1, 2000, 23, 30))).getDays());
		Assert.assertEquals(0,Days.daysIn(new Interval(jdt(1, 1, 2000, 0, 0), jdt(1, 1, 2000, 23, 59))).getDays());
		Assert.assertEquals(1,Days.daysIn(new Interval(jdt(1, 1, 2000, 10, 0), jdt(2, 1, 2000, 10, 30))).getDays());
		Assert.assertEquals(1,Days.daysIn(new Interval(jdt(1, 1, 2000, 10, 0), jdt(2, 1, 2000, 11, 30))).getDays());
		
	}
	
	//@Test
	public void apache(){
		Date d1=createDate(1,1,1990,10,0),d2=createDate(1,1,1990,8,0),d3=createDate(2,1,1990,13,10)
				,d4=createDate(2,1,1990,0,30),d5=createDate(1,1,1991,0,0);
		
		Assert.assertTrue(DateUtils.isSameDay(d1, d2));
		Assert.assertTrue(DateUtils.isSameDay(d3, d4));
		
		Assert.assertFalse(DateUtils.isSameDay(d1, d3));
		Assert.assertFalse(DateUtils.isSameDay(d1, d4));
		Assert.assertFalse(DateUtils.isSameDay(d1, d5));
	}
	
	/**/
	
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
	
	private Date createDate(int d,int m,int y,int h,int mm){
		try {
			return DateUtils.parseDate(d+"/"+m+"/"+y+" "+h+":"+mm, "dd/MM/yyyy hh:mm");
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private DateTime jdt(int d,int m,int y,int h,int mm){
		return new DateTime(createDate(d,m,y,h,mm));
	}
	
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
