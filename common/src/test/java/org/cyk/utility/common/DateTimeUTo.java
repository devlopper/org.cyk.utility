package org.cyk.utility.common;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Assert;

public class DateTimeUTo extends AbstractUnitTest {

	private static final long serialVersionUID = -2997119646811257337L;

	@Override
	protected void _execute_() {
		super._execute_();
		DateTime fromDate = new DateTime(createDate(1, 1, 2000, 10, 0)),toDate = new DateTime(createDate(27, 1, 2000, 11, 30));
		int numberOfDays = Days.daysBetween(fromDate,toDate).getDays();
		List<LocalDate> dates = new ArrayList<LocalDate>();
		for (int i=0; i < numberOfDays; i++)
		    dates.add(fromDate.plusDays(i).toLocalDate());
		
		Assert.assertEquals(26, numberOfDays);
		
		Assert.assertEquals(1,Days.daysIn(new Interval(jdt(1, 1, 2000, 0, 0), jdt(1, 1, 2000, 23, 30))).getDays());
		Assert.assertEquals(1,Days.daysIn(new Interval(jdt(1, 1, 2000, 0, 0), jdt(1, 1, 2000, 23, 59))).getDays());
		Assert.assertEquals(2,Days.daysIn(new Interval(jdt(1, 1, 2000, 10, 0), jdt(2, 1, 2000, 10, 30))).getDays());
		Assert.assertEquals(2,Days.daysIn(new Interval(jdt(1, 1, 2000, 10, 0), jdt(2, 1, 2000, 11, 30))).getDays());
		
		Date d1=createDate(1,1,1990,10,0),d2=createDate(1,1,1990,8,0),d3=createDate(2,1,1990,13,10)
				,d4=createDate(2,1,1990,0,30),d5=createDate(1,1,1991,0,0);
		
		Assert.assertTrue(DateUtils.isSameDay(d1, d2));
		Assert.assertTrue(DateUtils.isSameDay(d3, d4));
		
		Assert.assertFalse(DateUtils.isSameDay(d1, d3));
		Assert.assertFalse(DateUtils.isSameDay(d1, d4));
		Assert.assertFalse(DateUtils.isSameDay(d1, d5));
	}
	
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
	
	public static void main(String[] args) {
		System.out.println(new DateTime(DateTimeZone.UTC).toDate());
	}

}
