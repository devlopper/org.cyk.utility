package org.cyk.utility.common;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.OrderBy;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Where.Between;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Where.In;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Where.Like;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class StructuredQueryLanguageHelperUnitTest extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void jpqlWhere(){
		AssertionHelper.getInstance().assertEquals("c1=v1", new StructuredQueryLanguageHelper.Where.Adapter.Default.JavaPersistenceQueryLanguage()
			.addTokens("c1","=","v1").execute());
		AssertionHelper.getInstance().assertEquals("c1=v1", new StructuredQueryLanguageHelper.Where.Adapter.Default.JavaPersistenceQueryLanguage()
				.addTokens("c1=v1").execute());
	}
	
	@Test
	public void jpqlBetween(){
		Between between = new Between.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").where());
		AssertionHelper.getInstance().assertEquals("r.instantInterval.from.year BETWEEN :fromYear AND :toYear"
				, between.setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.from.year").execute());
		AssertionHelper.getInstance().assertEquals("r.instantInterval.from.monthOfYear BETWEEN :fromMonthOfYear AND :toMonthOfYear"
				, between.clear().setProperty(Between.PROPERTY_NAME_FIELD_NAME, "r.instantInterval.from.monthOfYear").execute());
	}
	
	@Test
	public void jpqlLike(){
		Like like = new Like.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").where());
		AssertionHelper.getInstance().assertEquals("(((field IS NULL ) AND (LENGTH(:fieldString) = 0)) OR ((field IS NOT NULL ) AND (LOWER(field) LIKE LOWER(:fieldLike))))"
				, like.setProperty(Like.PROPERTY_NAME_FIELD_NAME, "field").execute());
		AssertionHelper.getInstance().assertEquals("(((t.globalIdentifier.code IS NULL ) AND (LENGTH(:codeString) = 0)) OR ((t.globalIdentifier.code IS NOT NULL ) "
				+ "AND (LOWER(t.globalIdentifier.code) LIKE LOWER(:codeLike))))"
				, like.clear().setProperty(Like.PROPERTY_NAME_FIELD_NAME, "t.globalIdentifier.code").execute());
	}
	
	@Test
	public void jpqlIn(){
		In in = new In.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").where());
		AssertionHelper.getInstance().assertEquals("(field IN :fieldIn)", in.setProperty(In.PROPERTY_NAME_FIELD_NAME, "field").execute());
		AssertionHelper.getInstance().assertEquals("(t.globalIdentifier.code IN :codeIn)"
				, in.clear().setProperty(In.PROPERTY_NAME_FIELD_NAME, "t.globalIdentifier.code").execute());
		AssertionHelper.getInstance().assertEquals("(field IS NULL OR field NOT IN :fieldIn)"
				, in.clear().setProperty(In.PROPERTY_NAME_FIELD_NAME, "field").setProperty(In.PROPERTY_NAME_NOT, Boolean.TRUE).execute());
		AssertionHelper.getInstance().assertEquals("(t.globalIdentifier.code IS NULL OR t.globalIdentifier.code NOT IN :codeIn)"
				, in.clear().setProperty(In.PROPERTY_NAME_FIELD_NAME, "t.globalIdentifier.code").setProperty(In.PROPERTY_NAME_NOT, Boolean.TRUE).execute());
	}
	
	@Test
	public void jpqlOrderBy(){
		AssertionHelper.getInstance().assertEquals("c1=v1", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
			.addTokens("c1","=","v1").execute());
		AssertionHelper.getInstance().assertEquals("c1=v1", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.addTokens("c1=v1").execute());
	}
	
	@Test
	public void jpql(){
		assertEquals("SELECT t FROM Language t WHERE t.field=:param", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where().eq("field","param").getParent().execute());
		assertEquals("SELECT r FROM Language r WHERE r.field=:param", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class,"r").where().eq("field","param").getParent().execute());
		
		assertEquals("SELECT t FROM T1 t WHERE SELECT r FROM T2 r WHERE t.code=r.mycode AND r.a=:b", StructuredQueryLanguageHelper.getInstance().getBuilder("T1")
				.createBuilder("T2","r","code","mycode").where().eq("a", "b").getParent().addToParentWhereTokens().getParent().execute());
		/*
		assertEquals("SELECT t FROM T1 t WHERE SELECT r FROM T2 r WHERE t1.code=t2.mycode", StructuredQueryLanguageHelper.getInstance().getBuilder("T1")
				.where().addTokens(StructuredQueryLanguageHelper.getInstance().getBuilder("T2","r").where().join("t1.code","t2.mycode").getParent().execute()).getParent().execute());
		*/
		AssertionHelper.getInstance().assertEquals("SELECT t FROM ScheduleItem t WHERE (t.instantInterval.from.date BETWEEN :fromDate AND :toDate"
				+ " OR t.instantInterval.to.date BETWEEN :fromDate AND :toDate) OR (t.instantInterval.from.date<=:fromDate AND t.instantInterval.to.date>=:toDate)"
				, StructuredQueryLanguageHelper.getInstance().getBuilder(ScheduleItem.class).setFieldName("instantInterval").where().lp().bw("from.date").or().bw("to.date").rp()
				.or().lp().lte("from.date","fromDate").and().gte("to.date","toDate").rp().getParent().execute());
		
		//SELECT record FROM Language record WHERE  (((record.globalIdentifier.code IS NULL ) AND (:codeLength = 0)) OR ((record.globalIdentifier.code IS NOT NULL ) AND (LOWER(record.globalIdentifier.code) LIKE LOWER(:code))))  OR  (((record.globalIdentifier.name IS NULL ) AND (:nameLength = 0)) OR ((record.globalIdentifier.name IS NOT NULL ) AND (LOWER(record.globalIdentifier.name) LIKE LOWER(:name)))) 
	
		assertEquals("SELECT t FROM Language t WHERE (((t.globalIdentifier.code IS NULL ) AND (LENGTH(:codeString) = 0)) OR ((t.globalIdentifier.code IS NOT "
				+ "NULL ) AND (LOWER(t.globalIdentifier.code) LIKE LOWER(:codeLike))))"
				, StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).setFieldName("globalIdentifier").where().lk("code").getParent().execute());
		
		assertEquals("SELECT t FROM Language t WHERE (((t.globalIdentifier.code IS NULL ) AND (LENGTH(:codeString) = 0)) OR ((t.globalIdentifier.code IS NOT "
				+ "NULL ) AND (LOWER(t.globalIdentifier.code) LIKE LOWER(:codeLike)))) OR (((t.globalIdentifier.name IS NULL ) AND (LENGTH(:nameString) = 0)) "
				+ "OR ((t.globalIdentifier.name IS NOT NULL ) AND (LOWER(t.globalIdentifier.name) LIKE LOWER(:nameLike))))"
				, StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).setFieldName("globalIdentifier").where().lk("code").or().lk("name").getParent().execute());
		
		assertEquals("SELECT t FROM Language t WHERE (t.globalIdentifier.code IS NULL OR t.globalIdentifier.code NOT IN :codeIn) AND ((((t.globalIdentifier.code IS NULL ) AND (LENGTH(:codeString) = 0)) OR ((t.globalIdentifier.code IS NOT "
				+ "NULL ) AND (LOWER(t.globalIdentifier.code) LIKE LOWER(:codeLike)))) OR (((t.globalIdentifier.name IS NULL ) AND (LENGTH(:nameString) = 0)) "
				+ "OR ((t.globalIdentifier.name IS NOT NULL ) AND (LOWER(t.globalIdentifier.name) LIKE LOWER(:nameLike)))))"
				, StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).setFieldName("globalIdentifier").where().notIn("code").and().lp()
				.lk("code").or().lk("name").rp().getParent().execute());
		
		
	}
	
	/**/
	
	public void nativeSqlCreateTable(){
		assertEquals("", "");
	}
	
	/**/
	
	public static class ScheduleItem {
		
	}
	
	public static class Language {
		
	}
}
