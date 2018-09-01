package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.assertion.Assertion;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class AssertionBuilderNullUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsNull_valueIsTrue() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setValue(Boolean.TRUE).execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNotNull___affirmationIsNull_valueIsFalse() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setValue(Boolean.FALSE).execute().getOutput();
		assertionHelper.assertFalse(assertion.getValue()).assertNotNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNotNull___affirmationIsFalse_valueIsTrue() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setIsAffirmation(Boolean.FALSE).setValue(Boolean.TRUE).execute().getOutput();
		assertionHelper.assertFalse(assertion.getValue()).assertNotNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsFalse_valueIsFalse() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setIsAffirmation(Boolean.FALSE).setValue(Boolean.FALSE).execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_valueIsTrue() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setValue(Boolean.TRUE).execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNotNull___affirmationIsTrue_valueIsFalse() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setValue(Boolean.FALSE).execute().getOutput();
		assertionHelper.assertFalse(assertion.getValue()).assertNotNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsNull_valueIsTrue_fromField() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass()).setField("f1"))
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsNull_valueIsFalse_fromField() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass()).setField("f2"))
				.execute().getOutput();
		assertionHelper.assertFalse(assertion.getValue()).assertNotNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsFalse_valueIsTrue_fromField() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setIsAffirmation(Boolean.FALSE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass()).setField("f1"))
				.execute().getOutput();
		assertionHelper.assertFalse(assertion.getValue()).assertNotNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsFalse_valueIsFalse_fromField() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setIsAffirmation(Boolean.FALSE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass()).setField("f2"))
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_valueIsTrue_fromField() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setIsAffirmation(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass()).setField("f1"))
				.execute().getOutput();
		assertionHelper.assertTrue(assertion.getValue()).assertNull(assertion.getMessageWhenValueIsNotTrue());
	}
	
	@Test
	public void isMessageWhenValueIsNotTrueNull___affirmationIsTrue_valueIsFalse_fromField() {
		Assertion assertion = __inject__(AssertionBuilderNull.class).setIsAffirmation(Boolean.TRUE).setFieldValueGetter(__inject__(FieldValueGetter.class).setObject(new MyClass()).setField("f2"))
				.execute().getOutput();
		assertionHelper.assertFalse(assertion.getValue()).assertNotNull(assertion.getMessageWhenValueIsNotTrue());
	}

}
