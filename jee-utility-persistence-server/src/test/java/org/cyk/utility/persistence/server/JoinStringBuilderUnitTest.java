package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.JoinStringBuilder;
import org.cyk.utility.persistence.server.query.string.JoinStringBuilder.Arguments;
import org.cyk.utility.persistence.server.query.string.JoinType;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class JoinStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void join(){
		assertThat(JoinStringBuilder.getInstance().build(new Arguments().setType(JoinType.LEFT).setMasterVariableName("t").setTupleName("N")))
			.isEqualTo("LEFT JOIN N n ON n = t.n");
		assertThat(JoinStringBuilder.getInstance().build(new Arguments().setType(JoinType.LEFT).setMasterVariableName("t").setMasterFieldName("f1")
				.setTupleName("N").setVariableName("i")))
		.isEqualTo("LEFT JOIN N i ON i = t.f1");
	}
}