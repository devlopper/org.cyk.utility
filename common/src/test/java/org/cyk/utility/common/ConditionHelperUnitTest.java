package org.cyk.utility.common;

import java.io.Serializable;

import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.ConditionHelper.Condition;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class ConditionHelperUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();	
	}
	
	@Test
	public void duplicate(){
		assertCondition(new ConditionHelper.Condition.Builder.Duplicate.Adapter.Default().setValueNameIdentifier("code").setValueCount(0l)
				.setDomainNameIdentifier("person").setInput(123).execute(), Boolean.FALSE, null);
		
		assertCondition(new ConditionHelper.Condition.Builder.Duplicate.Adapter.Default().setValueNameIdentifier("code").setValueCount(1l)
				.setDomainNameIdentifier("person").setInput(123).execute(), Boolean.TRUE, "Un enregistrement de type personne avec pour code <<123>> existe déja.");
		
		assertCondition(new ConditionHelper.Condition.Builder.Duplicate.Adapter.Default().setValueNameIdentifier("code").setValueCount(1l)
				.setDomainNameIdentifier("person").setInput("ABC").execute(), Boolean.TRUE, "Un enregistrement de type personne avec pour code <<ABC>> existe déja.");

	}
	
	@Test
	public void assertNotNull(){
		assertCondition(new ConditionHelper.Condition.Builder.NotNull.Adapter.Default()
				.setFieldObject(new PhoneNumber()).setFieldName("code")
				.execute(), Boolean.TRUE, "La valeur du champ <<code>> du type <<numéro de téléphone>> est obligatoire.");
	}
	
	@Test
	public void assertNumberComparison(){
		assertEquals(Boolean.FALSE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(1).setNumber2(2).setEqual(null).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(1).setNumber2(2).setEqual(Boolean.FALSE).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(2).setNumber2(2).setEqual(Boolean.TRUE).execute().getValue());
		
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(1).setNumber2(2).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "La balance(1) doit être égale à 2.");		
		
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(1).setNumber2(2).setGreater(Boolean.FALSE).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "La balance(1) doit être supérieure ou égale à 2.");		
	}
	
	@Test
	public void assertDateComparison(){
		assertEquals(Boolean.FALSE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(1).setNumber2(2).setEqual(null).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(1).setNumber2(2).setEqual(Boolean.FALSE).execute().getValue());
		
		assertEquals(Boolean.TRUE,new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(2).setNumber2(2).setEqual(Boolean.TRUE).execute().getValue());
		
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(1).setNumber2(2).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "La balance(1) doit être égale à 2.");		
		
		assertCondition(new ConditionHelper.Condition.Builder.Comparison.Adapter.Default().setValueNameIdentifier("balance")
				.setDomainNameIdentifier("sale").setNumber1(1).setNumber2(2).setGreater(Boolean.FALSE).setEqual(Boolean.FALSE).execute(), Boolean.TRUE
				, "La balance(1) doit être supérieure ou égale à 2.");		
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
	
	@Getter @Setter
	public static class PhoneNumber implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String code;
		
	}
	
}
