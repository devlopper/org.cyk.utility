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
		assertEquals("exaequo", StringHelper.getInstance().getText(Locale.ENGLISH, "exaequo",null));
		assertEquals("th", StringHelper.getInstance().getOrdinalNumberSuffix(Locale.ENGLISH, 0));
		assertEquals("st", StringHelper.getInstance().getOrdinalNumberSuffix(Locale.ENGLISH, 1));
		assertEquals("nd", StringHelper.getInstance().getOrdinalNumberSuffix(Locale.ENGLISH, 2));
		assertEquals("rd", StringHelper.getInstance().getOrdinalNumberSuffix(Locale.ENGLISH, 3));
		assertEquals("th", StringHelper.getInstance().getOrdinalNumberSuffix(Locale.ENGLISH, 4));
	}
	
	@Test
	public void assertNumber(){
		assertEquals("first", StringHelper.getInstance().getOrdinalNumber(Locale.ENGLISH, 1));
		assertEquals("second", StringHelper.getInstance().getOrdinalNumber(Locale.ENGLISH, 2));
		assertEquals("third", StringHelper.getInstance().getOrdinalNumber(Locale.ENGLISH, 3));
		
		assertEquals("premier", StringHelper.getInstance().getOrdinalNumber(Locale.FRENCH, 1));
		assertEquals("deuxième", StringHelper.getInstance().getOrdinalNumber(Locale.FRENCH, 2));
		assertEquals("troisième", StringHelper.getInstance().getOrdinalNumber(Locale.FRENCH, 3));
	}
	
	private void assertAppliedCaseType(String string,CaseType caseType,String expected){
		assertEquals(expected, StringHelper.getInstance().applyCaseType(string, caseType));
	}
}
