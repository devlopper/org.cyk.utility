package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.helper.InstanceHelper;
import org.joda.time.DateTime;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
public class DateBuilder extends AbstractBuilder<Date> implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
	
	public static Integer YEAR=2000,MONTHOFYEAR=1,DAYOFMONTH=1,HOUROFDAY=0,MINUTEOFHOUR=0,SECONDOFMINUTE=0,MILLISOFSECOND=0;
	
	private Integer year,monthOfYear,dayOfMonth,hourOfDay,minuteOfHour,secondOfMinute,millisOfSecond;
	
	public DateBuilder(Integer dayOfMonth,Integer monthOfYear,Integer year,Integer hourOfDay,Integer minuteOfHour,Integer millisOfSecond){
		setDate(dayOfMonth, monthOfYear, year, hourOfDay, minuteOfHour, millisOfSecond);
	}
	
	@Override
	public Date build() {
		InstanceHelper instanceHelper = InstanceHelper.getInstance();
		return new DateTime(instanceHelper.getIfNotNullElseDefault(year,YEAR), instanceHelper.getIfNotNullElseDefault(monthOfYear,MONTHOFYEAR)
				, instanceHelper.getIfNotNullElseDefault(dayOfMonth,DAYOFMONTH), instanceHelper.getIfNotNullElseDefault(hourOfDay,HOUROFDAY)
				, instanceHelper.getIfNotNullElseDefault(minuteOfHour,MINUTEOFHOUR), instanceHelper.getIfNotNullElseDefault(secondOfMinute,SECONDOFMINUTE)
				, instanceHelper.getIfNotNullElseDefault(millisOfSecond,MILLISOFSECOND)).toDate();
	}
		
	/**/
	
	public DateBuilder setDate(Integer dayOfMonth,Integer monthOfYear,Integer year,Integer hourOfDay,Integer minuteOfHour,Integer millisOfSecond){
		this.dayOfMonth = dayOfMonth;
		this.monthOfYear = monthOfYear;
		this.year = year;
		this.hourOfDay = hourOfDay;
		this.minuteOfHour = minuteOfHour;
		this.millisOfSecond = millisOfSecond;
		return this;
	}
	
	/**/
	
	public static interface Listener extends AbstractBuilder.Listener<Date> {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		public static class Adapter extends AbstractBuilder.Listener.Adapter.Default<Date> implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
				
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
	/**/
	
	public static final String YES = "yes";
	public static final String NO = "no";
}