package org.cyk.utility.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.StringHelper.CaseType;
import org.cyk.utility.common.helper.StringHelper.Location;
import org.cyk.utility.common.helper.StringHelper.Transformer;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.joda.time.DateTime;
import org.junit.Test;


public class StringHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;

	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
		
		StringHelper.ToStringMapping.Datasource.Adapter.Default.UserDefined.REPOSITORY.put("stringid1", "string_result_one");
		StringHelper.ToStringMapping.Datasource.Adapter.Default.UserDefined.REPOSITORY.put("stringid2", "string_result_2");
		StringHelper.ToStringMapping.Datasource.Adapter.Default.UserDefined.REPOSITORY.put("stringid3", "third");
		
		StringHelper.ToStringMapping.Datasource.Adapter.Default.Cache.REPOSITORY.put("fr_cache001_NONE", "cache value one");
		
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.utility.common.testmsg", StringHelper.class.getClassLoader());
	}
	
	@Test
	public void assertWordArticle(){
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticleAll(Boolean.FALSE, Boolean.FALSE), "toute").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticleAll(Boolean.TRUE, Boolean.FALSE), "tout").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticleAll(Boolean.FALSE, Boolean.TRUE), "toutes").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticleAll(Boolean.TRUE, Boolean.TRUE), "tous").execute();
		
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticle(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE), "la").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticle(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE), "une").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticle(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE), "les").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticle(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE), "des").execute();
		
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticle(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE), "le").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticle(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE), "un").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticle(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE), "les").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(StringHelper.getInstance().getWordArticle(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE), "des").execute();
		
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("year")
				.setGender(Boolean.FALSE).execute(), "année").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("year")
				.setGender(Boolean.TRUE).execute(), "l'année").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("year")
				.setGender(Boolean.TRUE).setGenderAny(Boolean.FALSE).execute(), "l'année").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("year")
				.setGender(Boolean.TRUE).setGenderAny(Boolean.TRUE).execute(), "une année").execute();
		
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("year")
				.setGender(Boolean.TRUE).setGenderAny(Boolean.FALSE).setWordArticleAll(Boolean.TRUE).execute(), "toute l'année").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("year")
				.setGender(Boolean.TRUE).setGenderAny(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute(), "toute une année").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("year")
				.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute(), "toutes les années").execute();
		
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("month")
				.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute(), "tous les mois").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("day")
				.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute(), "tous les jours").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("hour")
				.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute(), "toutes les heures").execute();
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("minute")
				.setGender(Boolean.TRUE).setPlural(Boolean.TRUE).setWordArticleAll(Boolean.TRUE).execute(), "toutes les minutes").execute();
		
	}
	
	@Test
	public void assertPlural(){
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("year")
				.setPlural(Boolean.FALSE).execute(), "année").execute();;
		new AssertionHelper.Assertion.Equals.String.Adapter.Default(new StringHelper.ToStringMapping.Adapter.Default("year")
				.setPlural(Boolean.TRUE).execute(), "années").execute();;
		
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
		
		assertEquals("##notfound##", new StringHelper.ToStringMapping.Adapter.Default("notfound").execute());
	}
	
	@Test
	public void executeMapStringFromResourceBundleDatasource(){
		assertStringMapping("mid1", "string 1", StringHelper.ToStringMapping.Datasource.ResourceBundle.class);
		assertStringMapping("mid1", "string 1", StringHelper.ToStringMapping.Datasource.Cache.class);
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
	public void executeBuildStringClassIdentifier(){
		assertEquals("model.entity.classA", new StringHelper.Builder.ClassIdentifier.Adapter.Default().setInput(ClassA.class).execute());
		
	}
		
	@Test
	public void assertCaseType(){
		assertAppliedCaseType("mY pHraSE", CaseType.NONE,"mY pHraSE");
		assertAppliedCaseType("mY pHraSE", CaseType.FURL,"My phrase");
	}
	
	@Test
	public void assertText(){
		assertEquals("exaequo", StringHelper.getInstance().get("exaequo",null,null,Locale.ENGLISH));
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
	
	@Test
	public void assertWord(){
		AssertionHelper.getInstance().assertEquals("supérieure", StringHelper.getInstance().getWord("superior",Boolean.FALSE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("supérieures", StringHelper.getInstance().getWord("superior",Boolean.FALSE, Boolean.TRUE));
		AssertionHelper.getInstance().assertEquals("supérieur", StringHelper.getInstance().getWord("superior",Boolean.TRUE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("supérieurs", StringHelper.getInstance().getWord("superior",Boolean.TRUE, Boolean.TRUE));
		
		AssertionHelper.getInstance().assertEquals("inférieure", StringHelper.getInstance().getWord("inferior",Boolean.FALSE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("inférieures", StringHelper.getInstance().getWord("inferior",Boolean.FALSE, Boolean.TRUE));
		AssertionHelper.getInstance().assertEquals("inférieur", StringHelper.getInstance().getWord("inferior",Boolean.TRUE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("inférieurs", StringHelper.getInstance().getWord("inferior",Boolean.TRUE, Boolean.TRUE));
		
		AssertionHelper.getInstance().assertEquals("égale", StringHelper.getInstance().getWord("equal",Boolean.FALSE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("égales", StringHelper.getInstance().getWord("equal",Boolean.FALSE, Boolean.TRUE));
		AssertionHelper.getInstance().assertEquals("égal", StringHelper.getInstance().getWord("equal",Boolean.TRUE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("égaux", StringHelper.getInstance().getWord("equal",Boolean.TRUE, Boolean.TRUE));
	}
	
	@Test
	public void assertResponse(){
		assertEquals("non défini", StringHelper.getInstance().getResponse(null));
		assertEquals("oui", StringHelper.getInstance().getResponse(Boolean.TRUE));
		assertEquals("non", StringHelper.getInstance().getResponse(Boolean.FALSE));
		
		assertEquals("non défini", StringHelper.getInstance().getResponse(null,Locale.FRENCH));
		assertEquals("oui", StringHelper.getInstance().getResponse(Boolean.TRUE,Locale.FRENCH));
		assertEquals("non", StringHelper.getInstance().getResponse(Boolean.FALSE,Locale.FRENCH));
		
		assertEquals("undefined", StringHelper.getInstance().getResponse(null,Locale.ENGLISH));
		assertEquals("yes", StringHelper.getInstance().getResponse(Boolean.TRUE,Locale.ENGLISH));
		assertEquals("no", StringHelper.getInstance().getResponse(Boolean.FALSE,Locale.ENGLISH));
	}
	
	@Test
	public void assertComparisonOperator(){
		AssertionHelper.getInstance().assertEquals("supérieur ou égal", StringHelper.getInstance().getComparisonOperator(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("supérieur", StringHelper.getInstance().getComparisonOperator(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("inférieur ou égal", StringHelper.getInstance().getComparisonOperator(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("inférieur", StringHelper.getInstance().getComparisonOperator(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE));
		
		AssertionHelper.getInstance().assertEquals("supérieure ou égale", StringHelper.getInstance().getComparisonOperator(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("supérieure", StringHelper.getInstance().getComparisonOperator(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("inférieure ou égale", StringHelper.getInstance().getComparisonOperator(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));
		AssertionHelper.getInstance().assertEquals("inférieure", StringHelper.getInstance().getComparisonOperator(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
		
		AssertionHelper.getInstance().assertEquals("supérieurs ou égaux", StringHelper.getInstance().getComparisonOperator(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE));
		AssertionHelper.getInstance().assertEquals("supérieurs", StringHelper.getInstance().getComparisonOperator(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE));
		AssertionHelper.getInstance().assertEquals("inférieurs ou égaux", StringHelper.getInstance().getComparisonOperator(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE));
		AssertionHelper.getInstance().assertEquals("inférieurs", StringHelper.getInstance().getComparisonOperator(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE));
		
	}
	
	@Test
	public void convert(){
		assertEquals(new String("yao"), StringHelper.getInstance().convert("yao", String.class));
		assertEquals(new String("12"), StringHelper.getInstance().convert("12", String.class));
		assertEquals(new BigDecimal("12.36"), StringHelper.getInstance().convert("12.36", BigDecimal.class));
		assertEquals(new Long("159"), StringHelper.getInstance().convert("159", Long.class));
		assertEquals(new Integer("951"), StringHelper.getInstance().convert("951", Integer.class));
		assertEquals(new Byte("5"), StringHelper.getInstance().convert("5", Byte.class));
		assertEquals(new DateTime(2000, 2, 1, 0, 0).toDate(), StringHelper.getInstance().convert("1/2/2000", Date.class));
		//assertEquals(null, StringHelper.getInstance().convert("951", Object.class));
	}
	
	@Test
	public void transform(){
		assertEquals("aBcDe", new Transformer.Adapter.Default().addSequenceReplacement("1", "").addSequenceReplacement("2", "")
				.addSequenceReplacement("3", "").addSequenceReplacement("4", "").addTokens("a1B1c2D3e4").execute());
		assertEquals("a__B__cDeZZ", new Transformer.Adapter.Default().addSequenceReplacement("1", "__").addSequenceReplacement("2", "")
				.addSequenceReplacement("3", "").addSequenceReplacement("4", "ZZ").addTokens("a1B1c2D3e4").execute());
	}
	
	private void assertAppliedCaseType(String string,CaseType caseType,String expected){
		assertEquals(expected, StringHelper.getInstance().applyCaseType(string, caseType));
	}
	
	private void assertStringMapping(String identifier,String expectedString,Class<? extends StringHelper.ToStringMapping.Datasource> expectedDatasourceInterface){
		StringHelper.ToStringMapping mapping = new StringHelper.ToStringMapping.Adapter.Default(identifier);
		assertEquals(expectedString, mapping.execute());
		assertThat("datasource is supposed to be "+expectedDatasourceInterface.getSimpleName()+" but "+mapping.getDatasourcesExecutor().getMatchingListener().getClass().getSimpleName()+" found"
				, expectedDatasourceInterface.isAssignableFrom(mapping.getDatasourcesExecutor().getMatchingListener().getClass()));
	}
	
	/**/
	
	public static class ClassA {
		
	}
}
