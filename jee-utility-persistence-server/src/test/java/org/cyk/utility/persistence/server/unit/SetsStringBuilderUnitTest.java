package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.SetStringBuilder;
import org.cyk.utility.persistence.server.query.string.SetsStringBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SetsStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void set(){
		assertThat(SetsStringBuilder.getInstance().build(new SetStringBuilder.Arguments().setFieldName("code").setValue("a"))).isEqualTo("SET t.code = 'a'");
		assertThat(SetsStringBuilder.getInstance().build(new SetStringBuilder.Arguments().setFieldName("number").setValue(1))).isEqualTo("SET t.number = 1");
		
		assertThat(SetsStringBuilder.getInstance().build(
				new SetStringBuilder.Arguments().setFieldName("code").setValue("a")
				,new SetStringBuilder.Arguments().setFieldName("number").setValue(1)
				)).isEqualTo("SET t.code = 'a',t.number = 1");
	}
}