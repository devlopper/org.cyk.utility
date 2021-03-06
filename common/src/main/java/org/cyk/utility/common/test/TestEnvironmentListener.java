package org.cyk.utility.common.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.common.ClassRepository.ClassField;
import org.cyk.utility.common.ObjectFieldValues;
import org.cyk.utility.common.cdi.BeanListener;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import lombok.Getter;
import lombok.Setter;

public interface TestEnvironmentListener {

	Collection<TestEnvironmentListener> COLLECTION = new ArrayList<>();
	
	/**/
	
	void assertEquals(String message,Object expected,Object actual);

	void assertNull(String message,Object object);
	
	void assertCodeExists(Class<?> aClass,String code);
	
	void assertBigDecimalEquals(String message,BigDecimal expected,BigDecimal actual);
	
	void assertThat(String reason,Boolean assertion);
	
	<T> void assertThat(T actual,Matcher<? super T> matcher);
	
	<T> void assertThat(String reason,T actual,Matcher<? super T> matcher);
	
	void assertEquals(Object actualValues,ObjectFieldValues expectedValues);
	
	void hasProperty(Object object,String name,Object value);
	
	void hasProperties(Object object,Object...entries);
	
	<T> void contains(Class<T> aClass,Collection<T> list,Object[] names,Object[][] values);
	
	/**/
	
	String formatBigDecimal(BigDecimal value);
	void throwableCaught(Throwable throwable,String expectedMessage);
	/**/
	
	public static class Adapter extends BeanListener.Adapter implements TestEnvironmentListener,Serializable{

		private static final long serialVersionUID = 926931368237515224L;

		@Override
		public void assertEquals(String message, Object expected, Object actual) {notImplemented();}

		@Override
		public void assertNull(String message, Object object) {notImplemented();}
		
		@Override
		public void assertCodeExists(Class<?> aClass, String code) {}
		
		@Override
		public void assertBigDecimalEquals(String message, BigDecimal expected, BigDecimal actual) {notImplemented();}

		@Override
		public String formatBigDecimal(BigDecimal value) {notImplemented();return null;}

		@Override
		public void assertThat(String reason, Boolean assertion) {notImplemented();}

		@Override
		public <T> void assertThat(T actual, Matcher<? super T> matcher) {notImplemented();}

		@Override
		public <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {notImplemented();}
		
		@Override
		public void assertEquals(Object actualValues,ObjectFieldValues expectedValues) {}

		@Override
		public void hasProperty(Object object, String name, Object value) {notImplemented();}

		@Override
		public void hasProperties(Object object, Object... entries) {notImplemented();}

		@Override
		public <T> void contains(Class<T> aClass, Collection<T> list, Object[] names, Object[][] values) {notImplemented();}
		
		@Override
		public void throwableCaught(Throwable throwable,String expectedMessage) {}
		
		protected Throwable getThrowable(Throwable throwable){
			return throwable;
		}
		
		protected String getMessage(Throwable throwable){
			if(throwable.getCause()!=null)
				throwable = throwable.getCause();
			return throwable.getMessage();
		}
		
		protected void assertThrowableMessage(String expected,String actual){
			assertEquals("Throwable message", expected, actual);
		}
		/**/
		
		private void notImplemented(){
			throw new RuntimeException("Not Yet Implemented");
		}
		
		/**/
		
		public static class Default extends TestEnvironmentListener.Adapter implements Serializable {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void assertBigDecimalEquals(String message, BigDecimal expected, BigDecimal actual) {
				assertEquals(message, formatBigDecimal(expected),formatBigDecimal(actual));
			}
			
			@Override
			public void assertNull(String message, Object object) {
				assertEquals(message, null, object);
			}
			
			@Override
			public String formatBigDecimal(BigDecimal value) {
				return value == null ? null : value.toString();
			}
			
			@Override
			public void throwableCaught(Throwable throwable, String expectedMessage) {
				Throwable reason = getThrowable(throwable);
				if(reason==null)
					reason = throwable;
				assertThrowableMessage(expectedMessage, getMessage(reason));
			}
			
			/**/
			
			/* Hamcrest Short cuts*/
			
			@Override
			public void assertThat(String reason,Boolean assertion){
				MatcherAssert.assertThat(reason, assertion);
			}
			
			@Override
			public <T> void assertThat(T actual,Matcher<? super T> matcher){
				MatcherAssert.assertThat(actual, matcher);
			}
			
			@Override
			public <T> void assertThat(String reason,T actual,Matcher<? super T> matcher){
				MatcherAssert.assertThat(reason,actual, matcher);
			}
			
			@Override
			public void assertEquals(Object actualValues,ObjectFieldValues expectedValues) {
				for(Entry<ClassField, Object> entry : expectedValues.getValuesMap().entrySet()){
					String message = entry.getKey().toString();
					Object expectedValue = entry.getValue();
					Object actualValue = commonUtils.readProperty(actualValues, entry.getKey().getName());
					if(String.class.equals(entry.getKey().getField().getType()))
						assertEquals(message, expectedValue, (String)actualValue);	
					else if(BigDecimal.class.equals(entry.getKey().getField().getType()))
						assertBigDecimalEquals(message, new BigDecimal(expectedValue.toString()), (BigDecimal)actualValue);
					else if(Date.class.equals(entry.getKey().getField().getType()))
						assertEquals(message, expectedValue, actualValue);
					else
						assertEquals(entry.getKey().getField().getType()+" not yet handled", Boolean.TRUE, Boolean.FALSE);
				}
			}
			
			@Override
			public void hasProperty(Object object,String name,Object value){
				assertThat(object, hasPropertyMatcher(name, value));
			}
			
			@Override
			public void hasProperties(Object object,Object...entries){
				for(int i=0;i<entries.length;i=i+2)
					hasProperty(object, (String) entries[i], entries[i+1]);
			}
			
			@Override
			public <T> void contains(Class<T> aClass,Collection<T> list,Object[] names,Object[][] values){
				MatcherAssert.assertThat(list,Matchers.contains(hasPropertiesMatchers(aClass,names, values)));
			}
			
			
			/**/
			
			/* Harmcrest Matchers*/
			
			protected static Matcher<Object> hasPropertyMatcher(String name,Object value){
				return Matchers.hasProperty(name,Matchers.is(value));
			}
			
			protected static <T> List<Matcher<? super T>> hasPropertiesMatchers(Class<T> aClass,Object[] names,Object[][] values){
				List<Matcher<? super T>> matchers = new ArrayList<>();
				for(Object[] objectValues : values){
					List<Matcher<? super T>> objectMatchers = new ArrayList<>();
					for(int i=0;i<objectValues.length;i++){
						Object infos = names[i];
						String name;
						Class<?> type = null;
						Object value = objectValues[i];
						if(infos instanceof Object[]){
							name = (String) ((Object[]) infos)[0];
							if(((Object[]) infos).length>1){
								type = (Class<?>) ((Object[]) infos)[1];
							}
						}else{
							name = (String) names[i];
						}
						if(type==null)
							type = FieldUtils.getField(aClass, name, Boolean.TRUE).getType();
						if(value!=null && !value.getClass().equals(type)){
							if(BigDecimal.class.equals(type))
								if(value instanceof String)
									value = new BigDecimal((String)value);
								else if(value instanceof Long)
									value = new BigDecimal((Long)value);
								else if(value instanceof Integer)
									value = new BigDecimal((Integer)value);
								else if(value instanceof Float)
									value = new BigDecimal((Float)value);
								else if(value instanceof Double)
									value = new BigDecimal((Double)value);
						}
						
						objectMatchers.add(hasPropertyMatcher(name, value));
					}
					matchers.add(Matchers.allOf(objectMatchers));
				}
				return matchers;
			}
			
		}
	}
	/**/
	
	@Getter @Setter
	public static abstract class Try implements Serializable{
		private static final long serialVersionUID = -4483490165697187680L;
		private String expectedMessage;
		public Try(String expectedMessage) {
			super();
			this.expectedMessage = expectedMessage;
		}
		
		public void execute(){
			try { 
				code();
				throw new RuntimeException("None exception has been threw");
			} 
			catch (Exception exception) { 
				for(TestEnvironmentListener listener : COLLECTION)
					listener.throwableCaught(exception,expectedMessage);
			}  
		}
		protected abstract void code();
		
	}
}
