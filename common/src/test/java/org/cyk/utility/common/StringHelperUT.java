package org.cyk.utility.common;

import java.util.Locale;

import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class StringHelperUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	@Test
	public void assertCaseType(){
		assertAppliedCaseType("mY pHraSE", CaseType.NONE,"mY pHraSE");
		assertAppliedCaseType("mY pHraSE", CaseType.FURL,"My phrase");
	}
	
	@Test
	public void assertText(){
		assertEquals("th", StringHelper.getInstance().getNumberSuffix(Locale.ENGLISH, 0));
		assertEquals("st", StringHelper.getInstance().getNumberSuffix(Locale.ENGLISH, 1));
		assertEquals("nd", StringHelper.getInstance().getNumberSuffix(Locale.ENGLISH, 2));
		assertEquals("rd", StringHelper.getInstance().getNumberSuffix(Locale.ENGLISH, 3));
		assertEquals("th", StringHelper.getInstance().getNumberSuffix(Locale.ENGLISH, 4));
	}
	
	private void assertAppliedCaseType(String string,CaseType caseType,String expected){
		assertEquals(expected, StringHelper.getInstance().applyCaseType(string, caseType));
	}
}
