package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

import javax.inject.Singleton;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;

@Singleton
public class TimeHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static Integer YEAR=2000,MONTHOFYEAR=1,DAYOFMONTH=1,HOUROFDAY=0,MINUTEOFHOUR=0,SECONDOFMINUTE=0,MILLISOFSECOND=0;
	public static Integer YEAR_ALL=-1,MONTHOFYEAR_ALL=-1,DAYOFMONTH_ALL=-1,DAYOFMONTH_LAST=32,HOUROFDAY_ALL=-1,MINUTEOFHOUR_ALL=-1
			,SECONDOFMINUTE_ALL=-1,MILLISOFSECOND_ALL=-1;
	public static final Byte WEEK_NUMBER_OF_DAY = 7;
	
	public static final Integer NUMBER_OF_HOUR_BY_DAY_MINIMUM = 0;
	public static final Integer NUMBER_OF_HOUR_BY_DAY_MAXIMUM = 23;
	
	public static final Integer NUMBER_OF_MINUTE_BY_HOUR_MINIMUM = 0;
	public static final Integer NUMBER_OF_MINUTE_BY_HOUR_MAXIMUM = 59;
	
	public static final Integer NUMBER_OF_MILLISECOND_BY_SECOND = DateTimeConstants.MILLIS_PER_SECOND;
	public static final Integer NUMBER_OF_MILLISECOND_BY_MINUTE = DateTimeConstants.MILLIS_PER_MINUTE;
	public static final Integer NUMBER_OF_MILLISECOND_BY_HOUR = DateTimeConstants.MILLIS_PER_HOUR;
	
	private static TimeHelper INSTANCE;
	
	public static TimeHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new TimeHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public void pause(Number millisecond){
		if(millisecond!=null)
			try {
				Thread.sleep(millisecond.longValue());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public Long getNumberOfMillisecond(Object instant){
		if(NumberHelper.getInstance().isNumber(instant))
			return NumberHelper.getInstance().get(Long.class, instant);
		if(instant instanceof Date)
			return ((Date)instant).getTime();
		return null;
	}
	
	public Long getNumberOfMillisecondBetween(Object instant1,Object instant2){
		return NumberHelper.getInstance().get(Long.class,  NumberHelper.getInstance().subtract(getNumberOfMillisecond(instant2), getNumberOfMillisecond(instant1)));
	}
	
	public Long getNumberOfMillisecond(DurationType durationType, Date expectedFromDate, Date expectedToDate,Date currentFromDate) {
		Long instant1 = getNumberOfMillisecond(currentFromDate),instant2 = null;		
		Long durarion = null;
		if(DurationType.FULL.equals(durationType)) {
			durarion = getNumberOfMillisecondBetween(expectedFromDate, expectedToDate);
		}else if(DurationType.PARTIAL.equals(durationType)) {
			durarion = getNumberOfMillisecondBetween(currentFromDate, expectedToDate);
		}
		instant2 = NumberHelper.getInstance().get(Long.class, NumberHelper.getInstance().add(instant1, durarion));
		return getNumberOfMillisecondBetween(instant1, instant2);
	}
	
	public Long getNumberOfMillisecond(DurationType durationType, Date expectedFromDate, Date expectedToDate) {
		return getNumberOfMillisecond(durationType, expectedFromDate, expectedToDate, TimeHelper.getInstance().getUniversalTimeCoordinated());
	}
	
	public Date getAfter(Object instant,Object numberOfMillisecond){
		return new Date(NumberHelper.getInstance().get(Long.class
				,NumberHelper.getInstance().add(getNumberOfMillisecond(instant), NumberHelper.getInstance().get(Long.class,numberOfMillisecond))));
	}
	
	public Date getAfterNow(Object numberOfMillisecond){
		return getAfter(getUniversalTimeCoordinated(), numberOfMillisecond);
	}
	
	public Date getAfterNowByNumberOfMinute(Object numberOfMinutes){
		return getAfterNow(1000l * 60 * NumberHelper.getInstance().get(Long.class,numberOfMinutes));
	}
	
	public Date getUniversalTimeCoordinated(){
		return new DateTime(DateTimeZone.UTC).toDate();
	}

	public Date getDateFromString(String date){
		if(StringHelper.getInstance().isBlank(date))
			return null;
		return new TimeHelper.Builder.String.Adapter.Default(date).execute();
	}
	
	public Date getEarliestOfTheDay(Date date) {
		return getDate(getYear(date), getMonthOfYear(date), getDayOfMonth(date), NUMBER_OF_HOUR_BY_DAY_MINIMUM, NUMBER_OF_MINUTE_BY_HOUR_MINIMUM);
	}
	
	public Date getLatestOfTheDay(Date date) {
		return getDate(getYear(date), getMonthOfYear(date), getDayOfMonth(date), NUMBER_OF_HOUR_BY_DAY_MAXIMUM, NUMBER_OF_MINUTE_BY_HOUR_MAXIMUM);
	}
	
	public Integer getYear(java.util.Date date){
		if(date==null)
			return null;
		return new DateTime(date).getYear();
	}
	
	public Integer getMonthOfYear(java.util.Date date){
		if(date==null)
			return null;
		return new DateTime(date).getMonthOfYear();
	}
	
	public Integer getDayOfMonth(java.util.Date date){
		if(date==null)
			return null;
		return new DateTime(date).getDayOfMonth();
	}
	
	public Integer getHourOfDay(java.util.Date date){
		if(date==null)
			return null;
		return new DateTime(date).getHourOfDay();
	}
	
	public Integer getMinuteOfHour(java.util.Date date){
		if(date==null)
			return null;
		return new DateTime(date).getMinuteOfHour();
	}
	
	public Integer getSecondOfMinute(java.util.Date date){
		if(date==null)
			return null;
		return new DateTime(date).getSecondOfMinute();
	}
	
	public Integer getMillisecondOfSecond(java.util.Date date){
		if(date==null)
			return null;
		return new DateTime(date).getMillisOfSecond();
	}
	
	public Integer getNumberOfDaysOfMonth(Integer monthOfYear,Integer year){
		return new DateTime(year, monthOfYear, 1, 0, 0).dayOfMonth().getMaximumValue();
	}
	
	public Integer getDayOfWeek(Integer dayOfMonth,Integer monthOfYear,Integer year){
		return new DateTime(year, monthOfYear, dayOfMonth, 0, 0).getDayOfWeek();
	}
	
	public Date getDate(Integer year,Integer monthOfYear,Integer dayOfMonth,Integer dayOfWeek,Integer hour,Integer minute,Integer second,Integer millisecond){
		return new Builder.Instant.Adapter.Default(new Instant(year, monthOfYear, dayOfMonth, dayOfWeek,hour, minute, second, millisecond)).execute();
	}
	
	public Date getDate(Integer year,Integer monthOfYear,Integer dayOfMonth,Integer hour,Integer minute){
		return getDate(year, monthOfYear, dayOfMonth, null, hour, minute, null, null);
	}
	
	public Date getDate(Integer year,Integer monthOfYear,Integer dayOfMonth){
		return getDate(year, monthOfYear, dayOfMonth, null, null, null, null, null);
	}
	
	public Date getDate(Integer hour,Integer minute){
		return getDate(null, null, null, null, hour, minute, null, null);
	}
	
	public Integer compare(Integer year,Integer monthOfYear,Integer dayOfMonth,Date date){
		return getDate(year, monthOfYear, dayOfMonth).compareTo(date);
	}
	
	/**/
	
	public static interface Builder<INPUT> extends org.cyk.utility.common.Builder<INPUT, java.util.Date> {
		
		public static class Adapter<INPUT> extends org.cyk.utility.common.Builder.Adapter.Default<INPUT, java.util.Date> implements Builder<INPUT> , Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<INPUT> inputClass, INPUT input) {
				super(inputClass, input, java.util.Date.class);
			}
			
			/**/
			
			public static class Default<INPUT> extends Builder.Adapter<INPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<INPUT> inputClass, INPUT input) {
					super(inputClass, input);
				}
				
			}
			
		}
		
		/**/
		
		public static  interface String extends Builder<java.lang.String> {
			
			public static class Adapter extends Builder.Adapter.Default<java.lang.String> implements String , Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(java.lang.String input) {
					super(java.lang.String.class, input);
				}
				
				/**/
				
				public static class Default extends String.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public static final java.lang.String FORMAT = "dd/MM/yyyy";
					
					public Default(java.lang.String input) {
						super(input);
					}
					
					@Override
					protected Date __execute__() {
						java.lang.String format = (java.lang.String)getProperty(PROPERTY_NAME_FORMAT);
						if(StringHelper.getInstance().isBlank(format)){
							if(StringUtils.contains(getInput(), "/"))
								if(StringUtils.contains(getInput(), ":"))
									format = Constant.DAY_MONTH_YEAR_HOUR_MINUTE_PATTERN;
								else
									format = Constant.DAY_MONTH_YEAR_PATTERN;
							else
								if(StringUtils.contains(getInput(), ":"))
									format = Constant.HOUR_MINUTE_PATTERN;
								else
									;
						}
						format = StringUtils.defaultString(format,FORMAT);
						try {
							return DateUtils.parseDate(getInput(), format);
						} catch (ParseException e) {
							e.printStackTrace();
							return null;
						}
					}
				}
			}
		}
		
		/**/
		
		public static interface Part extends Builder<java.lang.Object> {
			
			public static class Adapter extends Builder.Adapter.Default<java.lang.Object> implements Part , Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter() {
					super(java.lang.Object.class, null);
					setIsInputRequired(Boolean.FALSE);
				}
				
				/**/
				
				public static class Default extends Part.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					@Override
					protected Date __execute__() {
						return new DateTime(getPropertyAsInteger(PROPERTY_NAME_YEAR,YEAR), getPropertyAsInteger(PROPERTY_NAME_MONTHOFYEAR,MONTHOFYEAR)
								, getPropertyAsInteger(PROPERTY_NAME_DAYOFMONTH,DAYOFMONTH), getPropertyAsInteger(PROPERTY_NAME_HOUROFDAY,HOUROFDAY)
								, getPropertyAsInteger(PROPERTY_NAME_MINUTEOFHOUR,MINUTEOFHOUR), getPropertyAsInteger(PROPERTY_NAME_SECONDOFMINUTE,SECONDOFMINUTE)
								, getPropertyAsInteger(PROPERTY_NAME_MILLISOFSECOND,MILLISOFSECOND)).toDate();		
					}
				}
			}
		}
		
		public static interface Instant extends Builder<TimeHelper.Instant> {
			
			public static class Adapter extends Builder.Adapter.Default<TimeHelper.Instant> implements Instant , Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(TimeHelper.Instant instant) {
					super(TimeHelper.Instant.class, instant);
				}
				
				/**/
				
				public static class Default extends Instant.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(TimeHelper.Instant instant) {
						super(instant);
					}
					
					@Override
					protected Date __execute__() {
						TimeHelper.Instant instant = getInput();
						return new DateTime(NumberHelper.getInstance().getInteger(instant.getYear(), YEAR)
								,NumberHelper.getInstance().getInteger(instant.getMonthOfYear(),MONTHOFYEAR)
								,NumberHelper.getInstance().getInteger(instant.getDayOfMonth(),DAYOFMONTH)
								, NumberHelper.getInstance().getInteger(instant.getHourOfDay(),HOUROFDAY)
								, NumberHelper.getInstance().getInteger(instant.getMinuteOfHour(),MINUTEOFHOUR)
								, NumberHelper.getInstance().getInteger(instant.getSecondOfMinute(),SECONDOFMINUTE)
								, NumberHelper.getInstance().getInteger(instant.getMillisecondOfSecond(),MILLISOFSECOND)).toDate();		
					}
				}
			}
		}
	}
	
	public static interface Stringifier<INPUT> extends org.cyk.utility.common.Builder.Stringifier<INPUT> {
		
		public static class Adapter<INPUT> extends org.cyk.utility.common.Builder.Stringifier.Adapter.Default<INPUT> implements TimeHelper.Stringifier<INPUT>,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<INPUT> inputClass, INPUT input) {
				super(inputClass, input);
			}
			
			public static class Default<INPUT> extends TimeHelper.Stringifier.Adapter<INPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<INPUT> inputClass, INPUT input) {
					super(inputClass, input);
				}
			}
		}
		
		/**/
		
		public static interface Date extends TimeHelper.Stringifier<java.util.Date> {
			
			public static class Adapter extends TimeHelper.Stringifier.Adapter.Default<java.util.Date> implements Date,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(java.util.Date input) {
					super(java.util.Date.class, input);
				}
				
				public static class Default extends Date.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(java.util.Date input) {
						super(input);
					}
					
					@Override
					protected java.lang.String __execute__() {
						Constant.Date.Part part = (org.cyk.utility.common.Constant.Date.Part) InstanceHelper.getInstance().getIfNotNullElseDefault(getProperty(PROPERTY_NAME_TIME_PART),Constant.Date.Part.DATE_AND_TIME);
						Constant.Date.Length length = (org.cyk.utility.common.Constant.Date.Length) InstanceHelper.getInstance().getIfNotNullElseDefault(getProperty(PROPERTY_NAME_TIME_LENGTH),Constant.Date.Length.SHORT);
						Locale locale = InstanceHelper.getInstance().getIfNotNullElseDefault(getLocale(), Locale.FRENCH);
						return new SimpleDateFormat(Constant.Date.getPattern(locale,part, length).getValue(),locale).format(getInput());
					}
				}
			}
		}
		
		public static interface Dates extends TimeHelper.Stringifier<TimeHelper.Stringifier.Date[]> {
			
			public static class Adapter extends TimeHelper.Stringifier.Adapter.Default<TimeHelper.Stringifier.Date[]> implements Dates,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(TimeHelper.Stringifier.Date[] input) {
					super(TimeHelper.Stringifier.Date[].class, input);
				}
				
				public static class Default extends Dates.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(TimeHelper.Stringifier.Date[] input) {
						super(input);
					}
					
					@Override
					protected java.lang.String __execute__() {
						return new StringHelper.ToStringMapping.Adapter.Default("time.interval").addManyParameters(getInput()[0].execute(),getInput()[1].execute()).execute();
					}
				}
			}
		}
		
		public static interface Duration extends TimeHelper.Stringifier<Long> {
			
			public static class Adapter extends TimeHelper.Stringifier.Adapter.Default<Long> implements Duration,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Long input) {
					super(Long.class, input);
				}
				
				public static class Default extends Duration.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default(Long input) {
						super(input);
					}
					
					public Default(Integer input) {
						this(input.longValue());
					}
					
					@Override
					protected java.lang.String __execute__() {
						return new MeasureHelper.Type.Unit.Stringifier.Adapter.Default(getInput()).setProperty(Action.PROPERTY_NAME_MEASURE_TYPE_UNIT
								, MeasureHelper.Type.Time.HOUR).execute();
					}
				}
			}
		}
	
		public static interface Unit extends TimeHelper.Stringifier<java.lang.Object> {
			
			public static class Adapter extends TimeHelper.Stringifier.Adapter.Default<java.lang.Object> implements Unit,Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(java.lang.Object input) {
					super(java.lang.Object.class, input);
				}
				
				public static class Default extends Date.Adapter implements Serializable {
					private static final long serialVersionUID = 1L;

					public Default() {
						super(null);
						setIsInputRequired(Boolean.FALSE);
					}
					
					@Override
					protected java.lang.String __execute__() {
						java.util.Collection<String> strings = new ArrayList<>();
						Integer year = getPropertyAsInteger(PROPERTY_NAME_YEAR);
						Integer monthOfYear = getPropertyAsInteger(PROPERTY_NAME_MONTHOFYEAR);
						Integer dayOfMonth = getPropertyAsInteger(PROPERTY_NAME_DAYOFMONTH);
						Integer hourOfDay = getPropertyAsInteger(PROPERTY_NAME_HOUROFDAY);
						Integer minuteOfHour = getPropertyAsInteger(PROPERTY_NAME_MINUTEOFHOUR);
						/*
						Integer secondOfMinute = (Integer) getProperty(PROPERTY_NAME_SECONDOFMINUTE);
						Integer millisecondOfMinute = (Integer) getProperty(PROPERTY_NAME_MILLISOFSECOND);
						*/
						if(dayOfMonth!=null || monthOfYear!=null || year!=null || hourOfDay!=null || minuteOfHour!=null){
							if(dayOfMonth!=null && monthOfYear!=null && year!=null && hourOfDay!=null && minuteOfHour!=null /*&& secondOfMinute!=null && millisecondOfMinute!=null*/ 
									&& !dayOfMonth.equals(DAYOFMONTH_ALL) && !dayOfMonth.equals(DAYOFMONTH_LAST) && !monthOfYear.equals(MONTHOFYEAR_ALL) && !year.equals(YEAR_ALL) && !hourOfDay.equals(HOUROFDAY_ALL) && !minuteOfHour.equals(MINUTEOFHOUR_ALL)){
								strings.add(new TimeHelper.Stringifier.Date.Adapter.Default(new TimeHelper.Builder.Part.Adapter.Default().setProperties(getProperties()).execute()).execute());
							}else{
								if(dayOfMonth!=null && monthOfYear!=null && year!=null){
									if(!dayOfMonth.equals(DAYOFMONTH_ALL) && !dayOfMonth.equals(DAYOFMONTH_LAST) && !monthOfYear.equals(MONTHOFYEAR_ALL) && !year.equals(YEAR_ALL))
										strings.add(new TimeHelper.Stringifier.Date.Adapter.Default(new TimeHelper.Builder.Part.Adapter.Default().setProperties(getProperties()).execute())
												.setProperty(PROPERTY_NAME_TIME_PART, Constant.Date.Part.DATE_ONLY).execute());
									else
										strings.add(String.format(Constant.DAY_MONTH_YEAR_PATTERN_FORMAT, 
												dayOfMonth.equals(DAYOFMONTH_ALL) ? new StringHelper.ToStringMapping.Adapter.Default("day")
														.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute() : String.valueOf(dayOfMonth)
												,monthOfYear.equals(MONTHOFYEAR_ALL) ? new StringHelper.ToStringMapping.Adapter.Default("month")
														.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute() : String.valueOf(monthOfYear)
												,year.equals(YEAR_ALL) ? new StringHelper.ToStringMapping.Adapter.Default("year")
														.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute() : String.valueOf(year)));
								}
								
								if(hourOfDay!=null && minuteOfHour!=null/* && secondOfMinute!=null && millisecondOfMinute!=null*/){
									if(strings.size()>0)
										strings.add(Constant.CHARACTER_SPACE.toString());
									if(!hourOfDay.equals(HOUROFDAY_ALL) && !minuteOfHour.equals(MINUTEOFHOUR_ALL))
										strings.add(new TimeHelper.Stringifier.Date.Adapter.Default(new TimeHelper.Builder.Part.Adapter.Default().setProperties(getProperties()).execute())
												.setProperty(PROPERTY_NAME_TIME_PART, Constant.Date.Part.TIME_ONLY).execute());
									else
										strings.add(String.format(Constant.HOUR_MINUTE_PATTERN_FORMAT, 
												hourOfDay.equals(HOUROFDAY_ALL) ? new StringHelper.ToStringMapping.Adapter.Default("hour")
														.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute() : String.valueOf(hourOfDay)
												,minuteOfHour.equals(MINUTEOFHOUR_ALL) ? new StringHelper.ToStringMapping.Adapter.Default("minute")
														.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute() : String.valueOf(minuteOfHour)));
								}
							}	
						}
						return CollectionHelper.getInstance().isBlank(strings) ? null : CollectionHelper.getInstance().concatenate(strings, Constant.EMPTY_STRING);
					}
				}
			}
		}
	}
	
	@Getter @Setter
	public static class Collection implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private java.util.Collection<Long> collection;
		
		public Collection add(java.util.Collection<Long> times){
			if(!CollectionHelper.getInstance().isEmpty(times)){
				if(collection == null)
					collection = new LinkedHashSet<>();
				CollectionHelper.getInstance().add(collection,Boolean.TRUE, times);	
			}
			return this;
		}
		
		public Collection add(Long...times){
			if(!ArrayHelper.getInstance().isEmpty(times)){
				add(Arrays.asList(times));
			}
			return this;
		}
		
		public Collection addCurrent(){
			return add(System.currentTimeMillis());
		}
		
		public Collection clear(){
			CollectionHelper.getInstance().clear(collection);
			return this;
		}
		
		public Long getDuration(Integer previousIndex){
			Long result = 0l;
			if(!CollectionHelper.getInstance().isEmpty(collection)){
				//if(collection.size()==1)
				//	addCurrent();
				if(collection.size()>1){
					List<Long> list = new ArrayList<>(collection);
					result = list.get(list.size()-1) - list.get(list.size()-1-previousIndex);
				}
			}
			return result;
		}
		
		public Long getDuration(){
			return getDuration(1);
		}
	}

	@Getter @Setter @NoArgsConstructor @Accessors(chain=true)
	public static class Instant implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Short year;
		private Byte monthOfYear;
		private Byte dayOfMonth;
		private Byte dayOfWeek;
		private Byte hourOfDay;
		private Byte minuteOfHour;
		private Byte secondOfMinute;
		private Short millisecondOfSecond;
		
		private Long millisecond;

		public Instant(Short year, Byte monthOfYear, Byte dayOfMonth,Byte dayOfWeek, Byte hourOfDay, Byte minuteOfHour,Byte secondOfMinute, Short millisecondOfSecond) {
			super();
			this.year = year;
			this.monthOfYear = monthOfYear;
			this.dayOfMonth = dayOfMonth;
			this.dayOfWeek = dayOfWeek;
			this.hourOfDay = hourOfDay;
			this.minuteOfHour = minuteOfHour;
			this.secondOfMinute = secondOfMinute;
			this.millisecondOfSecond = millisecondOfSecond;
		}
		
		public Instant(Integer year, Integer monthOfYear, Integer dayOfMonth,Integer dayOfWeek, Integer hourOfDay, Integer minuteOfHour,Integer secondOfMinute, Integer millisecondOfSecond) {
			this(NumberHelper.getInstance().get(Short.class, year),NumberHelper.getInstance().get(Byte.class, monthOfYear),NumberHelper.getInstance().get(Byte.class, dayOfMonth)
					,NumberHelper.getInstance().get(Byte.class, dayOfWeek),NumberHelper.getInstance().get(Byte.class, hourOfDay),NumberHelper.getInstance().get(Byte.class, minuteOfHour)
					,NumberHelper.getInstance().get(Byte.class, secondOfMinute),NumberHelper.getInstance().get(Short.class, millisecondOfSecond));
		}

		public Instant(Long millisecond) {
			super();
			this.millisecond = millisecond;
		}
		
		public Date getDate(){
			return TimeHelper.getInstance().getDate(year.intValue(), monthOfYear.intValue(), dayOfMonth.intValue());
		}
		
		/**/
		
		@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Accessors(chain=true)
		public static class Interval implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private Instant from,to;
			private Long distanceInMillisecond;
			private Long portionInMillisecond;
			
		}
	}

	/**/

	public static enum DurationType {
		FULL
		,PARTIAL
		
		;
	}
}
