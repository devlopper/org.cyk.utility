package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.SetStringBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SetStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void set(){
		assertThat(SetStringBuilder.getInstance().build("code","'a'")).isEqualTo("t.code = 'a'");
		assertThat(SetStringBuilder.getInstance().build("number","1")).isEqualTo("t.number = 1");
	}
}