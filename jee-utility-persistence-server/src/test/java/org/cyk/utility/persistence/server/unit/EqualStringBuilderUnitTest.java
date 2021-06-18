package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.EqualStringBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class EqualStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void equal(){
		assertThat(EqualStringBuilder.getInstance().build(new EqualStringBuilder.Arguments().setTupleName("t").setFieldName("name").setParameterName("name")))
				.isEqualTo("t.name = :name");
	}
	
	@Test
	public void equal_negate(){
		assertThat(EqualStringBuilder.getInstance().build(new EqualStringBuilder.Arguments().setTupleName("t").setFieldName("name").setParameterName("name")
				.setNegate(Boolean.TRUE))).isEqualTo("t.name <> :name");
	}
}