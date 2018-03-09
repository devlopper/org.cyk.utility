package org.cyk.utility.common;

import java.io.Serializable;
import java.util.Date;

import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.ConditionHelper.Condition;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ConditionHelperUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();	
		StringHelper.ToStringMapping.Datasource.Adapter.Default.ResourceBundle.REPOSITORY.put("org.cyk.utility.common.testmsg", StringHelper.class.getClassLoader());
	}
	
	@Test
	public void assertNotNullUsingField(){
		assertCondition(ConditionHelper.Condition.getBuilderNull(new MyEntity(), "f1")
				.execute(), Boolean.TRUE, "La valeur de l'attribut <<f1>> de l'entité <<mon entité>> doit être non nulle.");
	}
	
	@Test
	public void assertNullUsingField(){
		assertCondition(ConditionHelper.Condition.getBuilderNull(new MyEntity().setF1("myf1"),"f1").setIsNegateConditionValue(Boolean.TRUE)
				.execute(), Boolean.TRUE, "La valeur(myf1) de l'attribut <<f1>> de l'entité <<mon entité>> doit être nulle.");
	}
	
	@Test
	public void assertNotNullNotUsingField(){
		assertCondition(new ConditionHelper.Condition.Builder.Null.Adapter.Default()
				.setDomainNameIdentifier("domain").setValueNameIdentifier("code")
				.execute(), Boolean.TRUE, "La valeur de l'attribut <<code>> de l'entité <<domaine>> doit être non nulle.");
	}
	
	/*@Test
	public void assertCountUsingField(){
		assertCondition(new ConditionHelper.Condition.Builder.Count.Adapter.Default()
				.setFieldObject(new MyEntity()).setFieldName("f1").setValueCount(2l)
				.execute(), Boolean.TRUE, "La valeur de l'attribut <<f1>> de l'entité <<mon entité>> doit être unique.");
	}
	
	//@Test
	public void duplicate(){
		assertCondition(new ConditionHelper.Condition.Builder.Count.Adapter.Default().setValueNameIdentifier("code").setValueCount(0l)
				.setDomainNameIdentifier("person").setInput(123).execute(), Boolean.FALSE, null);
		
		assertCondition(new ConditionHelper.Condition.Builder.Count.Adapter.Default().setValueNameIdentifier("code").setValueCount(1l)
				.setDomainNameIdentifier("person").setInput(123).execute(), Boolean.TRUE, "Un enregistrement de type personne avec pour code <<123>> existe déja.");
		
		assertCondition(new ConditionHelper.Condition.Builder.Count.Adapter.Default().setValueNameIdentifier("code").setValueCount(1l)
				.setDomainNameIdentifier("person").setInput("ABC").execute(), Boolean.TRUE, "Un enregistrement de type personne avec pour code <<ABC>> existe déja.");

	}*/
	
	@Test
	public void assertNumberComparisonUsingField(){
		assertEquals(Boolean.FALSE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("int1")
				.setFieldObject(new MyEntity().setInt1(1)).setValue2(2).setEqual(null).execute().getValue());
		
		assertEquals(Boolean.FALSE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("int1")
				.setFieldObject(new MyEntity().setInt1(3)).setValue2(2).setEqual(null).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("int1")
				.setFieldObject(new MyEntity().setInt1(1)).setValue2(2).setGreater(Boolean.FALSE).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("int1")
				.setFieldObject(new MyEntity().setInt1(3)).setValue2(2).setGreater(Boolean.TRUE).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("int1")
				.setFieldObject(new MyEntity().setInt1(1)).setValue2(2).setEqual(Boolean.FALSE).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("int1")
				.setFieldObject(new MyEntity().setInt1(2)).setValue2(2).setEqual(Boolean.TRUE).execute().getValue());
		
		assertCondition(ConditionHelper.Condition.getBuilderComparison(new Sale().setBalance(1), 2, null, Boolean.FALSE, "balance").execute()
				, Boolean.TRUE, "La valeur(1) de l'attribut <<balance>> de l'entité <<vente>> doit être égale à 2.");
		
		assertCondition(ConditionHelper.Condition.getBuilderComparison(new Sale().setTotal(1),2,Boolean.FALSE,Boolean.FALSE,"total").execute()
				, Boolean.TRUE, "La valeur(1) de l'attribut <<total>> de l'entité <<vente>> doit être supérieure ou égale à 2.");	
	}
	
	@Test
	public void assertDateComparisonUsingField(){
		assertEquals(Boolean.FALSE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("date")
				.setFieldObject(new Sale().setDate(TimeHelper.getInstance().getDateFromString("1/1/2000")))
				.setValue2(TimeHelper.getInstance().getDateFromString("2/1/2000")).setEqual(null).execute().getValue());
		
		assertEquals(Boolean.FALSE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("date")
				.setFieldObject(new Sale().setDate(TimeHelper.getInstance().getDateFromString("3/1/2000")))
				.setValue2(TimeHelper.getInstance().getDateFromString("2/1/2000")).setEqual(null).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("date")
				.setFieldObject(new Sale().setDate(TimeHelper.getInstance().getDateFromString("1/1/2000")))
				.setValue2(TimeHelper.getInstance().getDateFromString("2/1/2000")).setGreater(Boolean.FALSE).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("date")
				.setFieldObject(new Sale().setDate(TimeHelper.getInstance().getDateFromString("3/1/2000")))
				.setValue2(TimeHelper.getInstance().getDateFromString("2/1/2000")).setGreater(Boolean.TRUE).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("date")
				.setFieldObject(new Sale().setDate(TimeHelper.getInstance().getDateFromString("1/1/2000")))
				.setValue2(TimeHelper.getInstance().getDateFromString("2/1/2000")).setEqual(Boolean.FALSE).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("date")
				.setFieldObject(new Sale().setDate(TimeHelper.getInstance().getDateFromString("2/1/2000")))
				.setValue2(TimeHelper.getInstance().getDateFromString("2/1/2000")).setEqual(Boolean.TRUE).execute().getValue());
		
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("date")
				.setFieldObject(new Sale().setDate(TimeHelper.getInstance().getDateFromString("1/1/2000")))
				.setValue2(TimeHelper.getInstance().getDateFromString("2/1/2000")).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "La valeur(01/01/2000 00:00) de l'attribut <<date>> de l'entité <<vente>> doit être égale à 02/01/2000 00:00.");
		
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setFieldName("date")
				.setFieldObject(new Sale().setDate(TimeHelper.getInstance().getDateFromString("1/1/2000")))
				.setValue2(TimeHelper.getInstance().getDateFromString("2/1/2000")).setGreater(Boolean.FALSE).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "La valeur(01/01/2000 00:00) de l'attribut <<date>> de l'entité <<vente>> doit être supérieure ou égale à 02/01/2000 00:00.");		
	}
	
	@Test
	public void assertCountNotUsingField(){
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Count.Adapter.Default().setDomainNameIdentifier("sale").setValueNameIdentifier("code")
				.setFieldValue("ABC").setValue1(1).setValue2(2).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "Le nombre d'enregistrement de l'entité <<vente>> où la valeur de l'attribut <<code>> est ABC doit être égale à 2.");
		
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Count.Adapter.Default().setDomainNameIdentifier("sale").setValueNameIdentifier("code")
				.setFieldValue("ABC").setValue1(1).setValue2(2).setGreater(Boolean.FALSE).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "Le nombre d'enregistrement de l'entité <<vente>> où la valeur de l'attribut <<code>> est ABC doit être supérieure ou égale à 2.");	
	}
	
	@Test
	public void assertCountUsingField(){
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Count.Adapter.Default().setFieldObject(new Sale().setCode("AZE")).setFieldName("code")
				.setValue1(1).setValue2(2).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "Le nombre d'enregistrement de l'entité <<vente>> où la valeur de l'attribut <<code>> est AZE doit être égale à 2.");
		
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Count.Adapter.Default().setFieldObject(new Sale().setCode("AZE")).setFieldName("code")
				.setValue1(1).setValue2(2).setGreater(Boolean.FALSE).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "Le nombre d'enregistrement de l'entité <<vente>> où la valeur de l'attribut <<code>> est AZE doit être supérieure ou égale à 2.");	
	}
	
	/**/
	
	private void assertCondition(Condition condition,Boolean expectedValue,String expectedMessage){
		assertEquals("value is not equals", expectedValue, condition.getValue());
		if(Boolean.TRUE.equals(expectedValue)){
			assertEquals("messages are not equal", expectedMessage, condition.getMessage());
		}else{
			assertEquals("message is not equal", expectedMessage, condition.getMessage());
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyEntity implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String f1;
		private Integer int1;
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Sale implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String code;
		private Integer balance;
		private Integer total;
		private Date date;
		
	}
	
}
