package org.cyk.utility.common;

import org.cyk.utility.common.helper.AssertionHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Builder.Adapter.Default.JavaPersistenceQueryLanguage;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.OrderBy;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.OrderBy.Order;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Where.Between;
import org.cyk.utility.common.helper.StructuredQueryLanguageHelper.Where.Equal;
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
	public void jpqlEqual(){
		Equal equal = new Equal.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").where());
		AssertionHelper.getInstance().assertEquals("((r.closed IS NULL AND :closedEqual = NULL) OR (r.closed IS NOT NULL AND r.closed = :closedEqual))"
				, equal.setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, Boolean.FALSE).execute());
		AssertionHelper.getInstance().assertEquals("(((r.closed IS NOT NULL) AND (:closedEqual = NULL)) OR ((r.closed IS NULL OR r.closed <> :closedEqual) AND (:closedEqual <> NULL)))"
				, equal.clear().setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, Boolean.TRUE).execute());		
		AssertionHelper.getInstance().assertEquals("((r.closed IS NULL) OR (r.closed IS NOT NULL))"
				, equal.clear().setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, null).execute());
	}
	
	@Test
	public void jpqlEqualToOneSpecifiedParameter(){
		Equal equal = new Equal.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").where());
		AssertionHelper.getInstance().assertEquals("((r.closed IS NULL AND :closedEqual1 = NULL) OR (r.closed IS NOT NULL AND r.closed = :closedEqual1))"
				, equal.addParameterNames("closedEqual1").setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, Boolean.FALSE).execute());
		/*
		AssertionHelper.getInstance().assertEquals("(((r.closed IS NOT NULL) AND (:closedEqual = NULL)) OR ((r.closed IS NULL OR r.closed <> :closedEqual) AND (:closedEqual <> NULL)))"
				, equal.clear().setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, Boolean.TRUE).execute());		
		AssertionHelper.getInstance().assertEquals("((r.closed IS NULL) OR (r.closed IS NOT NULL))"
				, equal.clear().setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, null).execute());
		*/		
	}
	
	@Test
	public void jpqlEqualToOneGeneratedParameter(){
		Equal equal = new Equal.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").where());
		AssertionHelper.getInstance().assertEquals("((r.closed IS NULL AND :closedEqual1 = NULL) OR (r.closed IS NOT NULL AND r.closed = :closedEqual1))"
				, equal.setNumberOfParameterNamesToGenerateFromFieldName(1).setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, Boolean.FALSE).execute());
		/*
		AssertionHelper.getInstance().assertEquals("(((r.closed IS NOT NULL) AND (:closedEqual = NULL)) OR ((r.closed IS NULL OR r.closed <> :closedEqual) AND (:closedEqual <> NULL)))"
				, equal.clear().setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, Boolean.TRUE).execute());		
		AssertionHelper.getInstance().assertEquals("((r.closed IS NULL) OR (r.closed IS NOT NULL))"
				, equal.clear().setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, null).execute());
		*/		
	}
	
	@Test
	public void jpqlEqualToTwoGeneratedParameters(){
		Equal equal = new Equal.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").where());
		AssertionHelper.getInstance().assertEquals("((r.closed IS NULL AND (:closedEqual1 = NULL OR :closedEqual2 = NULL)) OR (r.closed IS NOT NULL AND (r.closed = :closedEqual1 OR r.closed = :closedEqual2)))"
				, equal.setNumberOfParameterNamesToGenerateFromFieldName(2).addParameterNames("closedEqual1").addParameterNames("closedEqual2").setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed")
				.setProperty(Equal.PROPERTY_NAME_NOT, Boolean.FALSE).execute());
		/*
		AssertionHelper.getInstance().assertEquals("(((r.closed IS NOT NULL) AND (:closedEqual = NULL)) OR ((r.closed IS NULL OR r.closed <> :closedEqual) AND (:closedEqual <> NULL)))"
				, equal.clear().setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, Boolean.TRUE).execute());		
		AssertionHelper.getInstance().assertEquals("((r.closed IS NULL) OR (r.closed IS NOT NULL))"
				, equal.clear().setProperty(Equal.PROPERTY_NAME_FIELD_NAME, "r.closed").setProperty(Equal.PROPERTY_NAME_NOT, null).execute());
		*/		
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
		AssertionHelper.getInstance().assertEquals("((:fieldSetIsEmpty = true AND :fieldSetIsEmptyMeansAll = true) OR field IN :fieldSet)"
				, in.setProperty(In.PROPERTY_NAME_FIELD_NAME, "field").execute());
		
		in = new In.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","t").setFieldName("entity").where());
		AssertionHelper.getInstance().assertEquals("((:entityFieldSetIsEmpty = true AND :entityFieldSetIsEmptyMeansAll = true) OR t.entity.field IN :entityFieldSet)"
				, in.setProperty(In.PROPERTY_NAME_FIELD_NAME, "t.entity.field").execute());
		
		in = new In.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").setFieldName("globalIdentifier").where());
		AssertionHelper.getInstance().assertEquals("((:globalIdentifierCodeSetIsEmpty = true AND :globalIdentifierCodeSetIsEmptyMeansAll = true) OR t.globalIdentifier.code IN :globalIdentifierCodeSet)"
				, in.clear().setProperty(In.PROPERTY_NAME_FIELD_NAME, "t.globalIdentifier.code").execute());
		
		in = new In.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").where());
		AssertionHelper.getInstance().assertEquals("((:fieldSetIsEmpty = true AND :fieldSetIsEmptyMeansAll = true) OR field IS NULL OR (:fieldSetIsEmpty = false AND field NOT IN :fieldSet))"
				, in.clear().setProperty(In.PROPERTY_NAME_FIELD_NAME, "field").setProperty(In.PROPERTY_NAME_NOT, Boolean.TRUE).execute());
		
		in = new In.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").setFieldName("globalIdentifier").where());
		AssertionHelper.getInstance().assertEquals("((:globalIdentifierCodeSetIsEmpty = true AND :globalIdentifierCodeSetIsEmptyMeansAll = true) OR t.globalIdentifier.code IS NULL OR (:globalIdentifierCodeSetIsEmpty = false AND t.globalIdentifier.code NOT IN :globalIdentifierCodeSet))"
				, in.clear().setProperty(In.PROPERTY_NAME_FIELD_NAME, "t.globalIdentifier.code").setProperty(In.PROPERTY_NAME_NOT, Boolean.TRUE).execute());
		
		in = new In.Adapter.Default.JavaPersistenceQueryLanguage().setParent(new JavaPersistenceQueryLanguage("","").where());
		AssertionHelper.getInstance().assertEquals("((:myCollectionSetIsEmpty = true AND :myCollectionSetIsEmptyMeansAll = true) OR field IN :myCollectionSet)", in.setProperty(In.PROPERTY_NAME_FIELD_NAME, "field")
				.setProperty(In.PROPERTY_NAME_SET_NAME, "myCollection")
				.execute());
		
	}
	
	@Test
	public void jpqlOrderBy(){
		JavaPersistenceQueryLanguage jpql = new JavaPersistenceQueryLanguage("","");
		AssertionHelper.getInstance().assertEquals("myfield ASC", new Order.Adapter.Default.JavaPersistenceQueryLanguage().setParent(jpql)
				.setProperty(Order.PROPERTY_NAME_FIELD_NAME, "myfield").execute());
		
		jpql = new JavaPersistenceQueryLanguage("","");
		AssertionHelper.getInstance().assertEquals("myfield ASC", new Order.Adapter.Default.JavaPersistenceQueryLanguage().setParent(jpql)
				.setProperty(Order.PROPERTY_NAME_FIELD_NAME, "myfield").setProperty(OrderBy.Order.PROPERTY_NAME_NOT, Boolean.FALSE).execute());
		
		jpql = new JavaPersistenceQueryLanguage("","");
		AssertionHelper.getInstance().assertEquals("myfield DESC", new Order.Adapter.Default.JavaPersistenceQueryLanguage().setParent(jpql)
				.setProperty(Order.PROPERTY_NAME_FIELD_NAME, "myfield").setProperty(OrderBy.Order.PROPERTY_NAME_NOT, Boolean.TRUE).execute());
		
		AssertionHelper.getInstance().assertEquals("c1=v1", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.setParent(new JavaPersistenceQueryLanguage("","")).addTokens("c1=v1").execute());
		
		AssertionHelper.getInstance().assertEquals("t.myfield ASC", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.setParent(new JavaPersistenceQueryLanguage("","")).addOrder("myfield", null).execute());
		
		AssertionHelper.getInstance().assertEquals("t.myfield ASC", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.setParent(new JavaPersistenceQueryLanguage("","")).addOrder("myfield", Comparator.Order.ASCENDING).execute());
		
		AssertionHelper.getInstance().assertEquals("t.myfield DESC", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.setParent(new JavaPersistenceQueryLanguage("","")).addOrder("myfield", Comparator.Order.DESCENDING).execute());
		
		AssertionHelper.getInstance().assertEquals("t.myfield ASC", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.setParent(new JavaPersistenceQueryLanguage("","")).addOrder("myfield").execute());
		
		AssertionHelper.getInstance().assertEquals("t.myfield ASC", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.setParent(new JavaPersistenceQueryLanguage("","")).od("myfield").execute());
		
		AssertionHelper.getInstance().assertEquals("t.myfield ASC", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.setParent(new JavaPersistenceQueryLanguage("","")).asc("myfield").execute());
		
		AssertionHelper.getInstance().assertEquals("t.myfield DESC", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.setParent(new JavaPersistenceQueryLanguage("","")).desc("myfield").execute());
		
		AssertionHelper.getInstance().assertEquals("t.f1 ASC,t.f3 ASC,t.f2 DESC,t.f4 ASC", new StructuredQueryLanguageHelper.OrderBy.Adapter.Default.JavaPersistenceQueryLanguage()
				.setParent(new JavaPersistenceQueryLanguage("","")).asc("f1").asc("f3").desc("f2").asc("f4").execute());
	}
	
	@Test
	public void jpql(){
		assertEquals("SELECT t FROM Language t WHERE t.field=:param", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where().eq("field","param").getParent().execute());
		assertEquals("SELECT r FROM Language r WHERE r.field=:param", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class,"r").where().eq("field","param").getParent().execute());
		assertEquals("SELECT r FROM Language r WHERE r.field=:param ORDER BY r.field ASC", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class,"r")
				.where().eq("field","param").getParent().orderBy().asc("field").getParent().execute());
		
		assertEquals("SELECT t FROM Language t WHERE t.field=:field", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where().eq("field").getParent().execute());
		
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
		
		assertEquals("SELECT t FROM Language t WHERE ((:globalIdentifierCodeSetIsEmpty = true AND :globalIdentifierCodeSetIsEmptyMeansAll = true) OR t.globalIdentifier.code IS NULL OR (:globalIdentifierCodeSetIsEmpty = false AND t.globalIdentifier.code NOT IN :globalIdentifierCodeSet)) AND ((((t.globalIdentifier.code IS NULL ) AND (LENGTH(:codeString) = 0)) OR ((t.globalIdentifier.code IS NOT "
				+ "NULL ) AND (LOWER(t.globalIdentifier.code) LIKE LOWER(:codeLike)))) OR (((t.globalIdentifier.name IS NULL ) AND (LENGTH(:nameString) = 0)) "
				+ "OR ((t.globalIdentifier.name IS NOT NULL ) AND (LOWER(t.globalIdentifier.name) LIKE LOWER(:nameLike)))))"
				, StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).setFieldName("globalIdentifier").where().notIn("code").and().lp()
				.lk("code").or().lk("name").rp().getParent().execute());
		
		
		assertEquals("SELECT t FROM Language t WHERE ((t.closed IS NULL) OR (t.closed IS NOT NULL))", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where()
				.addEqual("closed",(Boolean)null,null).getParent().execute());
		
		assertEquals("SELECT t FROM Language t WHERE (((t.closed IS NOT NULL) AND (:closedEqual = NULL)) OR ((t.closed IS NULL OR t.closed <> :closedEqual) AND (:closedEqual <> NULL)))", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where()
				.addEqual("closed",Boolean.TRUE,null).getParent().execute());
		
		assertEquals("SELECT t FROM Language t WHERE ((t.closed IS NULL AND :closedEqual = NULL) OR (t.closed IS NOT NULL AND t.closed = :closedEqual))", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where()
				.addEqual("closed",Boolean.FALSE,null).getParent().execute());
		
		assertEquals("SELECT t FROM Language t WHERE ((t.closed IS NULL) OR (t.closed IS NOT NULL))", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where()
				.addEqual("closed").getParent().execute());
		
		assertEquals("SELECT t FROM Language t WHERE ((t.closed IS NULL AND :closedEqual1 = NULL) OR (t.closed IS NOT NULL AND t.closed = :closedEqual1))", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where()
				.addEqual("closed",Boolean.FALSE,1).getParent().execute());
		
		assertEquals("SELECT t FROM Language t WHERE ((t.closed IS NULL AND (:closedEqual1 = NULL OR :closedEqual2 = NULL)) OR (t.closed IS NOT NULL AND (t.closed = :closedEqual1 OR t.closed = :closedEqual2)))", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where()
				.addEqual("closed",Boolean.FALSE,2).getParent().execute());
		
		assertEquals("SELECT t FROM Language t WHERE ((t.closed IS NULL AND (:closedEqual1 = NULL OR :closedEqual2 = NULL OR :closedEqual3 = NULL)) OR (t.closed IS NOT NULL AND (t.closed = :closedEqual1 OR t.closed = :closedEqual2 OR t.closed = :closedEqual3)))", StructuredQueryLanguageHelper.getInstance().getBuilder(Language.class).where()
				.addEqual("closed",Boolean.FALSE,3).getParent().execute());
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
