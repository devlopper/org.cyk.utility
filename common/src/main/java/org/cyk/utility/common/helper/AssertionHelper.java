package org.cyk.utility.common.helper;

import java.io.Serializable;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

@Singleton
public class AssertionHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.JUnit.class,Boolean.FALSE);
	}
	
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
	
	public AssertionHelper assertNull(String message,Object object){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertNull(message, object);
		return this;
	}
	
	public AssertionHelper assertNull(Object object){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertNull(object);
		return this;
	}
	
	public AssertionHelper assertNotNull(String message,Object object){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertNotNull(message, object);
		return this;
	}
	
	public AssertionHelper assertNotNull(Object object){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertNotNull(object);
		return this;
	}
	
	public AssertionHelper assertTrue(String message,Boolean condition){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertTrue(message, condition);
		return this;
	}
	
	public AssertionHelper assertTrue(Boolean condition){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertTrue(condition);
		return this;
	}
	
	public AssertionHelper assertFalse(String message,Boolean condition){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertFalse(message, condition);
		return this;
	}
	
	public AssertionHelper assertFalse(Boolean condition){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertFalse(condition);
		return this;
	}
	
	public AssertionHelper assertEquals(String message,Object expected,Object actual){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertEquals(message, expected, actual);
		return this;
	}
	
	public AssertionHelper assertEquals(Object expected,Object actual){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertEquals(expected, actual);
		return this;
	}
	
	public AssertionHelper assertNotEquals(String message,Object expected,Object actual){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertNotEquals(message, expected, actual);
		return this;
	}
	
	public AssertionHelper assertNotEquals(Object expected,Object actual){
		ClassHelper.getInstance().instanciateOne(Listener.class).assertNotEquals(expected, actual);
		return this;
	}
	
	public AssertionHelper assertEquals(String expected,String actual,Boolean caseSensitive){
		if(!Boolean.TRUE.equals(caseSensitive)){
			expected = StringUtils.lowerCase(expected);
			actual = StringUtils.lowerCase(actual);
		}
		return assertEquals(expected,actual);
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

				public Adapter(Class<INPUT> inputClass,INPUT input,INPUT expected,java.lang.String message) {
					super(inputClass,input,expected,message);
				}
				
				public Adapter(Class<INPUT> inputClass,INPUT input,INPUT expected) {
					super(inputClass,input,expected);
				}
				
				/**/
				
				public static class Default<INPUT> extends Equals.Adapter<INPUT> implements Serializable {
					private static final long serialVersionUID = 1L;
					
					public Default(Class<INPUT> inputClass,INPUT input,INPUT expected,java.lang.String message) {
						super(inputClass,input,expected,message);
					}
					
					public Default(Class<INPUT> inputClass,INPUT input,INPUT expected) {
						super(inputClass,input,expected);
					}
					
					@Override
					protected void __assert__(java.lang.String message, INPUT expected, INPUT actual) {
						AssertionHelper.getInstance().assertEquals(message, expected, actual);
					}
				}
			}
			
			/**/
			
			public static interface String extends Equals<java.lang.String>  {
				
				public static class Adapter extends Equals.Adapter.Default<java.lang.String> implements String , Serializable {
					private static final long serialVersionUID = 1L;

					public Adapter(java.lang.String input,java.lang.String expected) {
						super(java.lang.String.class,input,expected,null);
					}
					
					/**/
					
					public static class Default extends String.Adapter implements Serializable {
						private static final long serialVersionUID = 1L;
						
						public Default(java.lang.String input,java.lang.String expected) {
							super(input,expected);
						}
						
						@Override
						protected void __assert__(java.lang.String message, java.lang.String expected,java.lang.String actual) {
							AssertionHelper.getInstance().assertEquals(message,expected, actual);
						}
					}
				}
			}
			
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
								AssertionHelper.getInstance().assertEquals(expectedYear, TimeHelper.getInstance().getYear(actual));
							if(expectedMonthOfYear!=null)
								AssertionHelper.getInstance().assertEquals(expectedMonthOfYear, TimeHelper.getInstance().getMonthOfYear(actual));
							if(expectedDayOfMonth!=null)
								AssertionHelper.getInstance().assertEquals(expectedDayOfMonth, TimeHelper.getInstance().getDayOfMonth(actual));
							if(expectedHourOfDay!=null)
								AssertionHelper.getInstance().assertEquals(expectedHourOfDay, TimeHelper.getInstance().getHourOfDay(actual));
							if(expectedMinuteOfHour!=null)
								AssertionHelper.getInstance().assertEquals(expectedMinuteOfHour, TimeHelper.getInstance().getMinuteOfHour(actual));
							if(expectedSecondOfMinute!=null)
								AssertionHelper.getInstance().assertEquals(expectedSecondOfMinute, TimeHelper.getInstance().getSecondOfMinute(actual));
							if(expectedMillisecondOfMinute!=null)
								AssertionHelper.getInstance().assertEquals(expectedMillisecondOfMinute, TimeHelper.getInstance().getMillisecondOfSecond(actual));
						}
					}
				}
			}
		}
	}

	public static interface Listener {
		
		void assertNull(String message,Object object);
		void assertNull(Object object);
		
		void assertNotNull(String message,Object object);
		void assertNotNull(Object object);
		
		void assertTrue(String message,Boolean condition);
		void assertTrue(Boolean condition);
		
		void assertFalse(String message,Boolean condition);
		void assertFalse(Boolean condition);
		
		void assertEquals(String message,Object expected,Object actual);
		void assertEquals(Object expected,Object actual);
		
		void assertNotEquals(String message,Object expected,Object actual);
		void assertNotEquals(Object expected,Object actual);
		
		/*
		void assertCodeExists(Class<?> aClass,String code);
		
		void assertBigDecimalEquals(String message,BigDecimal expected,BigDecimal actual);
		
		void assertThat(String reason,Boolean assertion);
		
		<T> void assertThat(T actual,Matcher<? super T> matcher);
		
		<T> void assertThat(String reason,T actual,Matcher<? super T> matcher);
		
		void assertEquals(Object actualValues,ObjectFieldValues expectedValues);
		
		void hasProperty(Object object,String name,Object value);
		
		void hasProperties(Object object,Object...entries);
		
		<T> void contains(Class<T> aClass,Collection<T> list,Object[] names,Object[][] values);
		*/
		public static class Adapter implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				/**/
				
				public static class JUnit extends Listener.Adapter.Default implements Serializable {
					private static final long serialVersionUID = 1L;
				
					@Override
					public void assertNull(java.lang.String message, Object object) {
						org.junit.Assert.assertNull(message, object);
					}
					
					@Override
					public void assertNull(Object object) {
						org.junit.Assert.assertNull(object);
					}
					
					@Override
					public void assertNotNull(java.lang.String message, Object object) {
						org.junit.Assert.assertNotNull(message, object);
					}
					
					@Override
					public void assertNotNull(Object object) {
						org.junit.Assert.assertNotNull(object);
					}
					
					@Override
					public void assertTrue(java.lang.String message, Boolean condition) {
						org.junit.Assert.assertTrue(message, condition);
					}
					
					@Override
					public void assertTrue(Boolean condition) {
						org.junit.Assert.assertTrue(condition);
					}
					
					@Override
					public void assertFalse(java.lang.String message, Boolean condition) {
						org.junit.Assert.assertFalse(message, condition);
					}
					
					@Override
					public void assertFalse(Boolean condition) {
						org.junit.Assert.assertFalse(condition);
					}
					
					@Override
					public void assertEquals(String message, Object expected,Object actual) {
						org.junit.Assert.assertEquals(message, expected, actual);
					}
					
					@Override
					public void assertEquals(Object expected, Object actual) {
						org.junit.Assert.assertEquals(expected, actual);
					}
					
					@Override
					public void assertNotEquals(String message, Object expected,Object actual) {
						org.junit.Assert.assertNotEquals(message, expected, actual);
					}
					
					@Override
					public void assertNotEquals(Object expected, Object actual) {
						org.junit.Assert.assertNotEquals(expected, actual);
					}
					
				}
				
			}
			
			@Override public void assertNull(java.lang.String message, Object object) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			@Override public void assertNull(Object object) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			
			@Override public void assertNotNull(java.lang.String message, Object object) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			@Override public void assertNotNull(Object object) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			
			@Override public void assertTrue(java.lang.String message, Boolean condition) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			@Override public void assertTrue(Boolean condition) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			
			@Override public void assertFalse(java.lang.String message, Boolean condition) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			@Override public void assertFalse(Boolean condition) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			
			@Override public void assertEquals(String message, Object expected, Object actual) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			@Override public void assertEquals(Object expected, Object actual) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			
			@Override public void assertNotEquals(String message, Object expected, Object actual) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			@Override public void assertNotEquals(Object expected, Object actual) {ThrowableHelper.getInstance().throwNotYetImplemented();}
			
		}
		
	}
	
}
