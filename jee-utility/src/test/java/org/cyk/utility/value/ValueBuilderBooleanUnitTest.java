package org.cyk.utility.value;

import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ValueBuilderBooleanUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildWithoutFieldNull() {
		assertionHelper.assertEquals(null, __inject__(ValueBuilderBoolean.class).setValue(null).execute().getOutput());
	}
	
	@Test
	public void buildWithoutFieldFalse() {
		assertionHelper.assertEquals(Boolean.FALSE, __inject__(ValueBuilderBoolean.class).setValue(Boolean.FALSE).execute().getOutput());
	}
	
	@Test
	public void buildWithoutFieldTrue() {
		assertionHelper.assertEquals(Boolean.TRUE, __inject__(ValueBuilderBoolean.class).setValue(Boolean.TRUE).execute().getOutput());
	}

	@Test
	public void buildWithFieldNull() {
		assertionHelper.assertEquals(null, __inject__(ValueBuilderBoolean.class).setFieldValueGetter(__inject__(FieldValueGetter.class)
				.setObject(new MyClass()).setField("booleanField")).execute().getOutput());
	}
	
	@Test
	public void buildWithFieldFalse() {
		assertionHelper.assertEquals(Boolean.FALSE, __inject__(ValueBuilderBoolean.class).setFieldValueGetter(__inject__(FieldValueGetter.class)
				.setObject(new MyClass().setBooleanField(Boolean.FALSE)).setField("booleanField")).execute().getOutput());
	}
	
	@Test
	public void buildWithFieldTrue() {
		assertionHelper.assertEquals(Boolean.TRUE, __inject__(ValueBuilderBoolean.class).setFieldValueGetter(__inject__(FieldValueGetter.class)
				.setObject(new MyClass().setBooleanField(Boolean.TRUE)).setField("booleanField")).execute().getOutput());
	}
}
