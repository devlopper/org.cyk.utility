package org.cyk.utility.common;

import org.cyk.utility.common.helper.ConditionHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.ConditionHelper.Condition;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

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
	
	private void assertCondition(Condition condition,Boolean expectedValue,String expectedMessage){
		assertEquals("value is not equals", expectedValue, condition.getValue());
		if(Boolean.TRUE.equals(expectedValue)){
			assertEquals("messages are not equal", expectedMessage, condition.getMessage());
		}else{
			assertThat("message is not null", expectedMessage == condition.getMessage());
		}
	}
	
}
