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

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.joda.time.DateTime;

@Singleton
public class TimeHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

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

					public static Integer YEAR=2000,MONTHOFYEAR=1,DAYOFMONTH=1,HOUROFDAY=0,MINUTEOFHOUR=0,SECONDOFMINUTE=0,MILLISOFSECOND=0;
					
					public Default(java.lang.String input) {
						super(input);
					}
					
					@Override
					protected Date __execute__() {
						try {
							return DateUtils.parseDate(getInput(), "dd/MM/yyyy");
						} catch (ParseException e) {
							e.printStackTrace();
							return null;
						}
						/*
						InstanceHelper instanceHelper = InstanceHelper.getInstance();
						return new DateTime(instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_YEAR),YEAR)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_MONTHOFYEAR),MONTHOFYEAR)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_DAYOFMONTH),DAYOFMONTH)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_HOUROFDAY),HOUROFDAY)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_MINUTEOFHOUR),MINUTEOFHOUR)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_SECONDOFMINUTE),SECONDOFMINUTE)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_MILLISOFSECOND),MILLISOFSECOND)).toDate();
								*/
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

					public static Integer YEAR=2000,MONTHOFYEAR=1,DAYOFMONTH=1,HOUROFDAY=0,MINUTEOFHOUR=0,SECONDOFMINUTE=0,MILLISOFSECOND=0;
					
					@Override
					protected Date __execute__() {
						InstanceHelper instanceHelper = InstanceHelper.getInstance();
						return new DateTime(instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_YEAR),YEAR)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_MONTHOFYEAR),MONTHOFYEAR)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_DAYOFMONTH),DAYOFMONTH)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_HOUROFDAY),HOUROFDAY)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_MINUTEOFHOUR),MINUTEOFHOUR)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_SECONDOFMINUTE),SECONDOFMINUTE)
								, instanceHelper.getIfNotNullElseDefault((Integer)getProperty(PROPERTY_NAME_MILLISOFSECOND),MILLISOFSECOND)).toDate();		
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
						Integer year = (Integer) getProperty(PROPERTY_NAME_YEAR);
						Integer monthOfYear = (Integer) getProperty(PROPERTY_NAME_MONTHOFYEAR);
						Integer dayOfMonth = (Integer) getProperty(PROPERTY_NAME_DAYOFMONTH);
						Integer hourOfDay = (Integer) getProperty(PROPERTY_NAME_HOUROFDAY);
						Integer minuteOfHour = (Integer) getProperty(PROPERTY_NAME_MINUTEOFHOUR);
						/*
						Integer secondOfMinute = (Integer) getProperty(PROPERTY_NAME_SECONDOFMINUTE);
						Integer millisecondOfMinute = (Integer) getProperty(PROPERTY_NAME_MILLISOFSECOND);
						*/
						if(dayOfMonth!=null && monthOfYear!=null && year!=null && hourOfDay!=null && minuteOfHour!=null /*&& secondOfMinute!=null && millisecondOfMinute!=null*/){
							strings.add(new TimeHelper.Stringifier.Date.Adapter.Default(new TimeHelper.Builder.Part.Adapter.Default().setProperties(getProperties()).execute()).execute());
						}else{
							if(dayOfMonth!=null && monthOfYear!=null && year!=null){
								strings.add(new TimeHelper.Stringifier.Date.Adapter.Default(new TimeHelper.Builder.Part.Adapter.Default().setProperties(getProperties()).execute())
									.setProperty(PROPERTY_NAME_TIME_PART, Constant.Date.Part.DATE_ONLY).execute());
							}else{
								strings.add(String.format(Constant.DAY_MONTH_YEAR_PATTERN_FORMAT, 
									dayOfMonth == null ? new StringHelper.ToStringMapping.Adapter.Default("day.all").execute() : String.valueOf(dayOfMonth)
									,monthOfYear == null ? new StringHelper.ToStringMapping.Adapter.Default("month.all").execute() : String.valueOf(monthOfYear)
									,year == null ? new StringHelper.ToStringMapping.Adapter.Default("year.all").execute() : String.valueOf(year)));
							}
							
							if(hourOfDay!=null && minuteOfHour!=null/* && secondOfMinute!=null && millisecondOfMinute!=null*/){
								strings.add(new TimeHelper.Stringifier.Date.Adapter.Default(new TimeHelper.Builder.Part.Adapter.Default().setProperties(getProperties()).execute())
									.setProperty(PROPERTY_NAME_TIME_PART, Constant.Date.Part.TIME_ONLY).execute());
							}else{
								strings.add(String.format(Constant.HOUR_MINUTE_PATTERN_FORMAT, 
										hourOfDay == null ? new StringHelper.ToStringMapping.Adapter.Default("hour.all").execute() : String.valueOf(hourOfDay)
										,minuteOfHour == null ? new StringHelper.ToStringMapping.Adapter.Default("minute.all").execute() : String.valueOf(minuteOfHour)));
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
}
