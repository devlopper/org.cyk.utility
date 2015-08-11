package org.cyk.utility.common.test;

import java.math.BigDecimal;
import java.util.Collection;

import org.hamcrest.Matcher;

public class TestEnvironmentAdapter implements TestEnvironmentListener{

	@Override
	public void assertEquals(String message, Object expected, Object actual) {notImplemented();}

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
	public void hasProperty(Object object, String name, Object value) {notImplemented();}

	@Override
	public void hasProperties(Object object, Object... entries) {notImplemented();}

	@Override
	public <T> void contains(Class<T> aClass, Collection<T> list, Object[] names, Object[][] values) {notImplemented();}
	
	/**/
	
	private void notImplemented(){
		throw new RuntimeException("Not Yet Implemented");
	}
}
	
	