package org.cyk.utility.common.test;

import java.math.BigDecimal;
import java.util.Collection;

import org.hamcrest.Matcher;

public interface TestEnvironmentListener {

	void assertEquals(String message,Object expected,Object actual);

	void assertBigDecimalEquals(String message,BigDecimal expected,BigDecimal actual);
	
	void assertThat(String reason,Boolean assertion);
	
	<T> void assertThat(T actual,Matcher<? super T> matcher);
	
	<T> void assertThat(String reason,T actual,Matcher<? super T> matcher);
	
	void hasProperty(Object object,String name,Object value);
	
	void hasProperties(Object object,Object...entries);
	
	<T> void contains(Class<T> aClass,Collection<T> list,Object[] names,Object[][] values);
	
	/**/
	
	String formatBigDecimal(BigDecimal value);
}
