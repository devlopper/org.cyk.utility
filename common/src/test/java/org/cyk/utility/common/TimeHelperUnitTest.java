package org.cyk.utility.common;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import lombok.Getter;

public class TimeHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	@Test
	public void buildFromString(){
		assertDate(new TimeHelper.Builder.String.Adapter.Default("19/11/2015").execute(), 2015,11,19,0,0,0,0);
		assertDate(new TimeHelper.Builder.String.Adapter.Default("19/11/2015 14:29").execute(), 2015,11,19,14,29,0,0);
		assertDate(new TimeHelper.Builder.String.Adapter.Default("19/11/2015 14:29:17:999").execute(), 2015,11,19,14,29,17,999);
		assertDate(new TimeHelper.Builder.String.Adapter.Default("14:29").execute(), 1970,1,1,14,29,0,0);
	}
	
	@Test
	public void buildFromPart(){
		new AssertionHelper.Assertion.Equals.Date.Adapter.Default(new TimeHelper.Builder.Part.Adapter.Default().execute(),TimeHelper.YEAR
				,TimeHelper.MONTHOFYEAR,TimeHelper.DAYOFMONTH,null,null,null,null).execute();
		
		new AssertionHelper.Assertion.Equals.Date.Adapter.Default(new TimeHelper.Builder.Part.Adapter.Default()
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_YEAR, 2015).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MONTHOFYEAR, 7)
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_DAYOFMONTH, 15).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_HOUROFDAY, 13)
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MINUTEOFHOUR, 31).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_SECONDOFMINUTE, 57)
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MILLISOFSECOND, 518)
				.execute(),2015,7,15,13,31,57,518).execute();
	}
	
	@Test
	public void stringfyDuration(){
		assertNull(new TimeHelper.Stringifier.Duration.Adapter.Default(0).execute());
		assertEquals("1 seconde", new TimeHelper.Stringifier.Duration.Adapter.Default(1000).execute());
	}
	
	@Test
	public void stringfyDate(){
		java.util.Date date = new TimeHelper.Builder.Part.Adapter.Default()
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_YEAR, 2015).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MONTHOFYEAR, 7)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_DAYOFMONTH, 15).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_HOUROFDAY, 13)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MINUTEOFHOUR, 31).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_SECONDOFMINUTE, 57)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MILLISOFSECOND, 518)
		.execute();
		new AssertionHelper.Assertion.Equals.Adapter.Default<String>(String.class,new TimeHelper.Stringifier.Date.Adapter.Default(date).execute(),"15/07/2015 13:31").execute();
	}
	
	@Test
	public void stringfyInterval(){
		java.util.Date date1 = new TimeHelper.Builder.Part.Adapter.Default()
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_YEAR, 2015).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MONTHOFYEAR, 7)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_DAYOFMONTH, 15).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_HOUROFDAY, 13)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MINUTEOFHOUR, 31).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_SECONDOFMINUTE, 57)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MILLISOFSECOND, 518)
		.execute();
		java.util.Date date2 = new TimeHelper.Builder.Part.Adapter.Default()
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_YEAR, 2016).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MONTHOFYEAR, 2)
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_DAYOFMONTH, 7).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_HOUROFDAY, 8)
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MINUTEOFHOUR, 15).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_SECONDOFMINUTE, 0)
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MILLISOFSECOND, 0)
				.execute();
		new AssertionHelper.Assertion.Equals.Adapter.Default<String>(String.class,new TimeHelper.Stringifier.Dates.Adapter.Default(new TimeHelper.Stringifier.Date[]{
				new TimeHelper.Stringifier.Date.Adapter.Default(date1),new TimeHelper.Stringifier.Date.Adapter.Default(date2)
		}).execute(),"du 15/07/2015 13:31 au 07/02/2016 08:15").execute();
	}
	
	@Test
	public void stringfyUnit(){
		//new AssertionHelper.Assertion.Equals.Adapter.Default<String>(String.class,new TimeHelper.Stringifier.Unit.Adapter.Default()
		//	.execute(),null).execute();
		
		new AssertionHelper.Assertion.Equals.Adapter.Default<String>(String.class,new TimeHelper.Stringifier.Unit.Adapter.Default()
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_YEAR, TimeHelper.YEAR_ALL).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MONTHOFYEAR, TimeHelper.MONTHOFYEAR_ALL)
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_DAYOFMONTH, TimeHelper.DAYOFMONTH_ALL).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_HOUROFDAY, TimeHelper.HOUROFDAY_ALL)
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MINUTEOFHOUR, TimeHelper.MINUTEOFHOUR_ALL).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_SECONDOFMINUTE, TimeHelper.SECONDOFMINUTE_ALL)
				.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MILLISOFSECOND, TimeHelper.MILLISOFSECOND_ALL)
				.execute(),"tous les jours/tous les mois/toutes les années toutes les heures:toutes les minutes").execute();
		
		new AssertionHelper.Assertion.Equals.Adapter.Default<String>(String.class,new TimeHelper.Stringifier.Unit.Adapter.Default()
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_YEAR, 2015).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MONTHOFYEAR, 7)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_DAYOFMONTH, 15).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_HOUROFDAY, 13)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MINUTEOFHOUR, 31).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_SECONDOFMINUTE, 57)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MILLISOFSECOND, 518)
		.execute(),"15/07/2015 13:31").execute();
		
		new AssertionHelper.Assertion.Equals.Adapter.Default<String>(String.class,new TimeHelper.Stringifier.Unit.Adapter.Default()
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_YEAR, 2015).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MONTHOFYEAR, 7)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_DAYOFMONTH, 15)
		.execute(),"15/07/2015").execute();
		
		new AssertionHelper.Assertion.Equals.Adapter.Default<String>(String.class,new TimeHelper.Stringifier.Unit.Adapter.Default()
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_HOUROFDAY, 13).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MINUTEOFHOUR, 31)
		.setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_SECONDOFMINUTE, 57).setProperty(TimeHelper.Builder.Part.PROPERTY_NAME_MILLISOFSECOND, 518)
		.execute(),"13:31").execute();
	}
	
	@Test
	public void collection(){
		TimeHelper.Collection collection = new TimeHelper.Collection();
		new AssertionHelper.Assertion.Equals.Adapter.Default<Long>(Long.class,collection.getDuration(),0l).execute();
		new AssertionHelper.Assertion.Equals.Adapter.Default<Long>(Long.class,collection.add(0l).getDuration(),0l).execute();
		new AssertionHelper.Assertion.Equals.Adapter.Default<Long>(Long.class,collection.add(17l).getDuration(),17l).execute();
		new AssertionHelper.Assertion.Equals.Adapter.Default<Long>(Long.class,collection.add(20l).getDuration(),3l).execute();
		new AssertionHelper.Assertion.Equals.Adapter.Default<Long>(Long.class,collection.add(21l).getDuration(),1l).execute();
	}
	
	@Test
	public void getNumberOfMillisecondWithDurationTypeFull() {		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 59, TimeHelper.getInstance().getNumberOfMillisecond(TimeHelper.DurationType.FULL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0)).intValue());
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 59, TimeHelper.getInstance().getNumberOfMillisecond(TimeHelper.DurationType.FULL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 30)).intValue());
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 59, TimeHelper.getInstance().getNumberOfMillisecond(TimeHelper.DurationType.FULL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0)).intValue());
	}
	
	@Test
	public void getNumberOfMillisecondWithDurationTypePartial() {		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 58, TimeHelper.getInstance().getNumberOfMillisecond(TimeHelper.DurationType.PARTIAL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 1)).intValue());
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 50, TimeHelper.getInstance().getNumberOfMillisecond(TimeHelper.DurationType.PARTIAL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 9)).intValue());
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.NUMBER_OF_MILLISECOND_BY_MINUTE * 29, TimeHelper.getInstance().getNumberOfMillisecond(TimeHelper.DurationType.PARTIAL
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 0), TimeHelper.getInstance().getDate(2000, 1, 1, 1, 59)
				, TimeHelper.getInstance().getDate(2000, 1, 1, 1, 30)).intValue());
	}
	
	@Test
	public void getEarliestOfTheDay() {		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 0, 0, 0)
				, TimeHelper.getInstance().getEarliestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 0)));
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 0)
				, TimeHelper.getInstance().getEarliestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 1)));
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 0)
				, TimeHelper.getInstance().getEarliestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 30)));
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 0)
				, TimeHelper.getInstance().getEarliestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 59)));
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 0)
				, TimeHelper.getInstance().getEarliestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 10, 30)));
	}
	
	@Test
	public void getLatestOfTheDay() {		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 23, 59, 59, 999)
				, TimeHelper.getInstance().getLatestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 0)));
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 23, 59, 59, 999)
				, TimeHelper.getInstance().getLatestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 1)));
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 23, 59, 59, 999)
				, TimeHelper.getInstance().getLatestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 30)));
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 23, 59, 59, 999)
				, TimeHelper.getInstance().getLatestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 0, 59)));
		
		AssertionHelper.getInstance().assertEquals(TimeHelper.getInstance().getDate(2000, 1, 1, 23, 59, 59, 999)
				, TimeHelper.getInstance().getLatestOfTheDay(TimeHelper.getInstance().getDate(2000, 1, 1, 23, 59)));
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
