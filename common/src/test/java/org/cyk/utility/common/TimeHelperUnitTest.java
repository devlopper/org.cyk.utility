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
		AssertionHelper.Listener.COLLECTION.add(new AssertionHelper.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;

			@Override
			public void assertEquals(String message, Object expected,Object actual) {
				Assert.assertEquals(message, expected, actual);
			}
		});
	}
	
	@Test
	public void buildFromString(){
		Date date = new TimeHelper.Builder.String.Adapter.Default("19/11/2015").execute();
		assertEquals(19, TimeHelper.getInstance().getDayOfMonth(date));
		assertEquals(11, TimeHelper.getInstance().getMonthOfYear(date));
		assertEquals(2015, TimeHelper.getInstance().getYear(date));
	}
	
	@Test
	public void buildFromPart(){
		new AssertionHelper.Assertion.Equals.Date.Adapter.Default(new TimeHelper.Builder.Part.Adapter.Default().execute(),TimeHelper.Builder.Part.Adapter.Default.YEAR
				,TimeHelper.Builder.Part.Adapter.Default.MONTHOFYEAR,TimeHelper.Builder.Part.Adapter.Default.DAYOFMONTH,null,null,null,null).execute();
		
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
		new AssertionHelper.Assertion.Equals.Adapter.Default<String>(String.class,new TimeHelper.Stringifier.Unit.Adapter.Default()
			.execute(),null).execute();
		
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
