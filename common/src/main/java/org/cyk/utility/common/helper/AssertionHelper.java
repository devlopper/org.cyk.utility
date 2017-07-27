package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.junit.Assert;

@Singleton
public class AssertionHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	private static AssertionHelper INSTANCE;
	
	public static AssertionHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new AssertionHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	public static interface Assertion<INPUT> extends org.cyk.utility.common.Action<INPUT,java.lang.Object>  {
		
		public static class Adapter<INPUT> extends org.cyk.utility.common.Action.Adapter.Default<INPUT,java.lang.Object> implements Assertion<INPUT> , Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<INPUT> inputClass,INPUT input) {
				super("assert",inputClass,input,java.lang.Object.class);
			}
			
			/**/
			
			public static class Default<INPUT> extends Assertion.Adapter<INPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<INPUT> inputClass,INPUT input,INPUT expected,String message) {
					super(inputClass,input);
					setProperty(PROPERTY_NAME_EXPECTED, expected);
					setProperty(PROPERTY_NAME_MESSAGE, message);
					setIsInputRequired(Boolean.FALSE);
				}
				
				public Default(Class<INPUT> inputClass,INPUT input,INPUT expected) {
					this(inputClass,input,expected,null);
				}
				
				@Override
				protected java.lang.Object __execute__() {
					INPUT input = getInput();
					@SuppressWarnings("unchecked")
					INPUT expected = (INPUT) getProperty(PROPERTY_NAME_EXPECTED);
					String message = (String) getProperty(PROPERTY_NAME_MESSAGE);
					__assert__(message,expected, input);
					return input;
				}
				
				protected void __assert__(String message,INPUT expected,INPUT actual){
					//Assert.assertEquals(message, expected, actual);
					ThrowableHelper.getInstance().throwNotYetImplemented();
				}
				
			}
		}
		
		/**/
		
		public static interface Equals<INPUT> extends Assertion<INPUT>  {
			
			public static class Adapter<INPUT> extends Assertion.Adapter.Default<INPUT> implements Equals<INPUT> , Serializable {
				private static final long serialVersionUID = 1L;

				public Adapter(Class<INPUT> inputClass,INPUT input,INPUT expected,String message) {
					super(inputClass,input,expected,message);
				}
				
				public Adapter(Class<INPUT> inputClass,INPUT input,INPUT expected) {
					super(inputClass,input,expected);
				}
				
				/**/
				
				public static class Default<INPUT> extends Equals.Adapter<INPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<INPUT> inputClass,INPUT input,INPUT expected,String message) {
						super(inputClass,input,expected,message);
					}
					
					public Default(Class<INPUT> inputClass,INPUT input,INPUT expected) {
						super(inputClass,input,expected);
					}
					
					@Override
					protected void __assert__(java.lang.String message, INPUT expected, INPUT actual) {
						Assert.assertEquals(message, expected, actual);
					}
				}
			}
			
			/**/
			
			public static interface Date extends Equals<java.util.Date>  {
				
				public static class Adapter extends Equals.Adapter.Default<java.util.Date> implements Date , Serializable {
					private static final long serialVersionUID = 1L;

					public Adapter(java.util.Date input,Integer expectedYear,Integer expectedMonthOfYear,Integer expectedDayOfMonth,Integer expectedHourOfDay,Integer expectedMinuteOfHour,Integer expectedSecondOfMinute,Integer expectedMillisecondOfMinute) {
						super(java.util.Date.class,input,null,null);
						setProperty(PROPERTY_NAME_YEAR, expectedYear);
						setProperty(PROPERTY_NAME_MONTHOFYEAR, expectedMonthOfYear);
						setProperty(PROPERTY_NAME_DAYOFMONTH, expectedDayOfMonth);
						setProperty(PROPERTY_NAME_HOUROFDAY, expectedHourOfDay);
						setProperty(PROPERTY_NAME_MINUTEOFHOUR, expectedMinuteOfHour);
						setProperty(PROPERTY_NAME_SECONDOFMINUTE, expectedSecondOfMinute);
						setProperty(PROPERTY_NAME_MILLISOFSECOND, expectedMillisecondOfMinute);
					}
					
					/**/
					
					public static class Default extends Date.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Default(java.util.Date input,Integer expectedYear,Integer expectedMonthOfYear,Integer expectedDayOfMonth,Integer expectedHourOfDay,Integer expectedMinuteOfHour,Integer expectedSecondOfMinute,Integer expectedMillisecondOfMinute) {
							super(input,expectedYear,expectedMonthOfYear,expectedDayOfMonth,expectedHourOfDay,expectedMinuteOfHour,expectedSecondOfMinute,expectedMillisecondOfMinute);
						}
						
						@Override
						protected void __assert__(java.lang.String message, java.util.Date expected, java.util.Date actual) {
							Integer expectedYear = (Integer) getProperty(PROPERTY_NAME_YEAR);
							Integer expectedMonthOfYear = (Integer) getProperty(PROPERTY_NAME_MONTHOFYEAR);
							Integer expectedDayOfMonth = (Integer) getProperty(PROPERTY_NAME_DAYOFMONTH);
							Integer expectedHourOfDay = (Integer) getProperty(PROPERTY_NAME_HOUROFDAY);
							Integer expectedMinuteOfHour = (Integer) getProperty(PROPERTY_NAME_MINUTEOFHOUR);
							Integer expectedSecondOfMinute = (Integer) getProperty(PROPERTY_NAME_SECONDOFMINUTE);
							Integer expectedMillisecondOfMinute = (Integer) getProperty(PROPERTY_NAME_MILLISOFSECOND);
							if(expectedYear!=null)
								Assert.assertEquals(expectedYear, TimeHelper.getInstance().getYear(actual));
							if(expectedMonthOfYear!=null)
								Assert.assertEquals(expectedMonthOfYear, TimeHelper.getInstance().getMonthOfYear(actual));
							if(expectedDayOfMonth!=null)
								Assert.assertEquals(expectedDayOfMonth, TimeHelper.getInstance().getDayOfMonth(actual));
							if(expectedHourOfDay!=null)
								Assert.assertEquals(expectedHourOfDay, TimeHelper.getInstance().getHourOfDay(actual));
							if(expectedMinuteOfHour!=null)
								Assert.assertEquals(expectedMinuteOfHour, TimeHelper.getInstance().getMinuteOfHour(actual));
							if(expectedSecondOfMinute!=null)
								Assert.assertEquals(expectedSecondOfMinute, TimeHelper.getInstance().getSecondOfMinute(actual));
							if(expectedMillisecondOfMinute!=null)
								Assert.assertEquals(expectedMillisecondOfMinute, TimeHelper.getInstance().getMillisecondOfSecond(actual));
						}
					}
				}
			}
			
		}
	}
	
}
