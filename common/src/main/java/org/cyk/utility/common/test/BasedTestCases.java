package org.cyk.utility.common.test;

import java.io.Serializable;
import java.util.Date;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.TimeHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class BasedTestCases<TESTCASE extends TestCase> extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<TESTCASE> testCaseClass;
	
	@SuppressWarnings("unchecked")
	protected TESTCASE instanciateOneCase(){
		return (TESTCASE) ClassHelper.getInstance().instanciateOne(testCaseClass == null ? TestCase.class : testCaseClass);
	}
	
	/* Time */
	
	public TimeHelper getTimeHelper(){
		return TimeHelper.getInstance();
	}
	
	public Date getDateFromString(String date){
		return getTimeHelper().getDateFromString(date);
	}
	
	public Date getDate(Integer year,Integer monthOfYear,Integer dayOfMonth){
		return getTimeHelper().getDate(year, monthOfYear, dayOfMonth);
	}
	
	public Date getDate(Integer year,Integer monthOfYear,Integer dayOfMonth,Integer hourOfDay,Integer minuteOfHour){
		return getTimeHelper().getDate(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour);
	}
	
	/* Assertions */
	
	public AssertionHelper getAssertionHelper(){
		return AssertionHelper.getInstance();
	}

	public void assertNull(String message, Object object) {
		getAssertionHelper().assertNull(message, object);
	}
	
	public void assertNull(Object object) {
		assertNull("object is null", object);
	}
	
	public void assertNotNull(String message, Object object) {
		getAssertionHelper().assertNotNull(message, object);
	}
	
	public void assertNotNull(Object object) {
		assertNotNull("object is not null", object);
	}
	
	public void assertTrue(String message, Boolean condition) {
		getAssertionHelper().assertTrue(message, condition);
	}
	
	public void assertTrue(Boolean condition) {
		assertTrue("it is false", condition);
	}
	
	public void assertFalse(String message, Boolean condition) {
		getAssertionHelper().assertFalse(message, condition);
	}
	
	public void assertFalse(Boolean condition) {
		assertTrue("it is true", condition);
	}
	
	public void assertEquals(String message, Object expected,Object actual) {
		getAssertionHelper().assertEquals(message, expected,actual);
	}
	
	public void assertEquals(Object expected,Object actual) {
		getAssertionHelper().assertEquals(expected,actual);
	}
	
	public void assertEqualsNumber(String message, Object expected,Object actual) {
		getAssertionHelper().assertEqualsNumber(message, expected,actual);
	}
	
	public void assertEqualsNumber(Object expected,Object actual) {
		getAssertionHelper().assertEqualsNumber(expected,actual);
	}
	
}
