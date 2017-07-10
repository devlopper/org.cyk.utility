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
		StringHelper.ToStringMapping.DATASOURCES.add(new StringHelper.ToStringMapping.Datasource.UserDefined.Adapter.Default());
		StringHelper.ToStringMapping.Datasource.Adapter.Default.UserDefined.REPOSITORY.put("stringid1", "string_result_one");
		StringHelper.ToStringMapping.Datasource.Adapter.Default.UserDefined.REPOSITORY.put("stringid2", "string_result_2");
		StringHelper.ToStringMapping.Datasource.Adapter.Default.UserDefined.REPOSITORY.put("stringid3", "third");
		
		StringHelper.ToStringMapping.DATASOURCES.add(new StringHelper.ToStringMapping.Datasource.Cache.Adapter.Default());
		StringHelper.ToStringMapping.Datasource.Adapter.Default.Cache.REPOSITORY.put("cache001", "cache value one");
		
		StringHelper.ToStringMapping.DATASOURCES.add(new StringHelper.ToStringMapping.Datasource.ResourceBundle.Adapter.Default());
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.utility.common.testmsg", StringHelper.class.getClassLoader());
	}
	
	@Test
	public void applyCaseType(){
		assertThat("string is not null", StringHelper.getInstance().applyCaseType(null, CaseType.NONE)==null);
		assertThat("string is not null", StringHelper.getInstance().applyCaseType(null, CaseType.FU)==null);
		assertThat("string is not null", StringHelper.getInstance().applyCaseType(null, CaseType.FURL)==null);
		
		assertEquals("", StringHelper.getInstance().applyCaseType("", CaseType.NONE));
		assertEquals("", StringHelper.getInstance().applyCaseType("", CaseType.FU));
		assertEquals("", StringHelper.getInstance().applyCaseType("", CaseType.FURL));
		
		assertEquals("S", StringHelper.getInstance().applyCaseType("S", CaseType.NONE));
		assertEquals("S", StringHelper.getInstance().applyCaseType("s", CaseType.FU));
		assertEquals("S", StringHelper.getInstance().applyCaseType("s", CaseType.FURL));
		
		assertEquals("sAlut géRArd!", StringHelper.getInstance().applyCaseType("sAlut géRArd!", CaseType.NONE));
		assertEquals("SAlut géRArd!", StringHelper.getInstance().applyCaseType("sAlut géRArd!", CaseType.FU));
		assertEquals("Salut gérard!", StringHelper.getInstance().applyCaseType("sAlut géRArd!", CaseType.FURL));
	}
	
	@Test
	public void executeMapStringFromUserDefinedDatasource(){
		assertEquals("string_result_one", new StringHelper.ToStringMapping.Adapter.Default("stringid1").execute());
		assertEquals("string_result_2", new StringHelper.ToStringMapping.Adapter.Default("stringid2").execute());
		assertEquals("third", new StringHelper.ToStringMapping.Adapter.Default("stringid3").execute());
	}
	
	@Test
	public void executeMapStringFromResourceBundleDatasource(){
		assertEquals("string 1", new StringHelper.ToStringMapping.Adapter.Default("mid1").execute());
		assertEquals("string 2", new StringHelper.ToStringMapping.Adapter.Default("mid2").execute());
		assertEquals("string 2 avec string 1", new StringHelper.ToStringMapping.Adapter.Default("mid1_2").execute());
	}
	
	@Test
	public void executeMapStringFromResourceBundleDatasourceWithParameters(){
		assertEquals("hi v1 , it is v2", new StringHelper.ToStringMapping.Adapter.Default("myparamid1").addManyParameters("v1","v2").execute());
		assertEquals("hi komenan , it is mom", new StringHelper.ToStringMapping.Adapter.Default("myparamid1").addManyParameters("komenan","mom").execute());
		assertEquals("hi KOMENAN , it is mOm", new StringHelper.ToStringMapping.Adapter.Default("myparamid1").addManyParameters("KOMENAN","mOm").execute());
		assertEquals("Hi komenan , it is mom", new StringHelper.ToStringMapping.Adapter.Default("myparamid1").setCaseType(CaseType.FURL).addManyParameters("komenan","mom")
				.execute());
		assertEquals("Hi komenan , it is mom", new StringHelper.ToStringMapping.Adapter.Default("myparamid1").setCaseType(CaseType.FURL).addManyParameters("KOMENAN","mOm")
				.execute());
		assertEquals("Hi KOMENAN , it is mOm", new StringHelper.ToStringMapping.Adapter.Default("myparamid1").setCaseType(CaseType.FU)
				.addManyParameters("KOMENAN","mOm").execute());
	}
	
	@Test
	public void executeMapStringFromCacheDatasource(){
		assertEquals("cache value one", new StringHelper.ToStringMapping.Adapter.Default("cache001").execute());
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
