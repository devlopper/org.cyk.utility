package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import javax.inject.Singleton;

import org.apache.commons.lang3.time.DateUtils;
import org.cyk.utility.common.Action;
import org.joda.time.DateTime;

@Singleton
public class DateHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static DateHelper INSTANCE;
	
	public static DateHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new DateHelper();
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
	
	/**/
	
	public static  interface Builder<INPUT> extends org.cyk.utility.common.Builder<INPUT, java.util.Date> {
		
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
		
		
		
	}
	
	public static interface Stringifier<INPUT> extends org.cyk.utility.common.Builder.Stringifier<INPUT> {
		
		public static class Adapter<INPUT> extends org.cyk.utility.common.Builder.Stringifier.Adapter.Default<INPUT> implements DateHelper.Stringifier<INPUT>,Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<INPUT> inputClass, INPUT input) {
				super(inputClass, input);
			}
			
			public static class Default<INPUT> extends DateHelper.Stringifier.Adapter<INPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<INPUT> inputClass, INPUT input) {
					super(inputClass, input);
				}
			}
		}
		
		/**/
		
		public static interface Duration extends DateHelper.Stringifier<Long> {
			
			public static class Adapter extends DateHelper.Stringifier.Adapter.Default<Long> implements Duration,Serializable {
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
	}
}
