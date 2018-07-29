package org.cyk.utility.condition;

import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ConditionBuilderNullUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildWithoutField() {
		Condition condition = __inject__(ConditionBuilderNull.class).setIdentifier(12).setValue(Boolean.TRUE).setMessage("la valeur est nulle").execute().getOutput();
		assertionHelper.assertEquals(12, condition.getIdentifier()).assertTrue(condition.getValue())
		.assertEquals("la valeur est nulle", condition.getMessage());
	}
	
	@Test
	public void buildWithField() {
		ConditionBuilderNull conditionBuilderNull = __inject__(ConditionBuilderNull.class);
		conditionBuilderNull.setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass()).setField("f1"));
		Condition condition = conditionBuilderNull.setIdentifier(12).setMessage("la valeur du champ f1 est non nulle").execute().getOutput();
		assertionHelper.assertEquals(12, condition.getIdentifier()).assertTrue(condition.getValue())
		.assertEquals("la valeur du champ f1 est non nulle", condition.getMessage());
	}
	
}
