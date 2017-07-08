package org.cyk.utility.common;

import java.util.Locale;

import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.common.helper.StringHelper.Location;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;


public class StringHelperUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	static {
		StringHelper.ToStringMapping.RESOURCE_BUNDLE_MAP.put("org.cyk.utility.common.testmsg", StringHelper.class.getClassLoader());
		StringHelper.ToStringMapping.MAP.put("stringid1", "string_result_one");
		StringHelper.ToStringMapping.MAP.put("stringid2", "string_result_2");
		StringHelper.ToStringMapping.MAP.put("stringid3", "third");
	}
	
	@Test
	public void executeBuildStringCacheIdentifier(){
		assertEquals("fr_stringid1", new StringHelper.Builder.CacheIdentifier.Adapter.Default().setInput("stringid1").execute());
		assertEquals("fr_stringid1_NONE", new StringHelper.Builder.CacheIdentifier.Adapter.Default().setInput("stringid1").addParameters(new Object[]{CaseType.NONE}).execute());
		assertEquals("fr_stringid1_liste_employee_NONE", new StringHelper.Builder.CacheIdentifier.Adapter.Default().setInput("stringid1")
				.addParameters(new Object[]{"liste","employee",CaseType.NONE}).execute());
		assertEquals("fr_stringid1_liste_employee_2016_NONE", new StringHelper.Builder.CacheIdentifier.Adapter.Default().setInput("stringid1")
				.addParameters(new Object[]{"liste","employee",2016,CaseType.NONE}).execute());
	}
	
	@Test
	public void executeToStringMapping(){
		assertEquals("string_result_one", new StringHelper.ToStringMapping.Adapter.Default("stringid1").execute());
		assertEquals("string_result_2", new StringHelper.ToStringMapping.Adapter.Default("stringid2").execute());
		assertEquals("third", new StringHelper.ToStringMapping.Adapter.Default("stringid3").execute());
		assertEquals("string 1", new StringHelper.ToStringMapping.Adapter.Default("mid1").execute());
		assertEquals("string 2", new StringHelper.ToStringMapping.Adapter.Default("mid2").execute());
		assertEquals("string 2 avec string 1", new StringHelper.ToStringMapping.Adapter.Default("mid1_2").execute());
	}
	
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
	
	@Test
	public void assertLocation(){
		assertEquals(Boolean.TRUE, StringHelper.getInstance().isAtLocation("FIELD_F", "FIELD_",Location.START));
		
	}
	
	private void assertAppliedCaseType(String string,CaseType caseType,String expected){
		assertEquals(expected, StringHelper.getInstance().applyCaseType(string, caseType));
	}
}
