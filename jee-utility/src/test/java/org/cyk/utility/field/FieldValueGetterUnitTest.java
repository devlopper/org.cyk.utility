package org.cyk.utility.field;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class FieldValueGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getMyEntityIdentifier(){
		assertThat(__inject__(FieldValueGetter.class).setObject(new MyEntity().setCode("c01")).setField("code").execute().getOutput())
			.isEqualTo("c01");
	}
	
	@Test
	public void getStringNotField(){
		assertThat(__inject__(FieldValueGetter.class).setObject(new MyEntity().setIdentifier(27).setCode("c01")).setField("identifierAndCode").execute().getOutput())
			.isEqualTo("27c01");
	}
	
	@Test
	public void getI01F01(){
		assertThat(__inject__(FieldValueGetter.class).setField(FieldUtils.getField(I01.class, "F01")).execute().getOutput())
			.isEqualTo("v01");
	}
	
	/**/
	
	public static interface I01 {
		String F01 = "v01";
	}
}
