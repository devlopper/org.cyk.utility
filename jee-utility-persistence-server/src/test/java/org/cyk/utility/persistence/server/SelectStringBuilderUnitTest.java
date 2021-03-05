package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.SelectStringBuilder;
import org.cyk.utility.persistence.server.query.string.SelectStringBuilder.Projection;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SelectStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void select(){
		assertThat(SelectStringBuilder.getInstance().buildFromTuple("t", "code")).isEqualTo("SELECT t.code");
		assertThat(SelectStringBuilder.getInstance().buildFromTuple("t", "code","name")).isEqualTo("SELECT t.code,t.name");
		assertThat(SelectStringBuilder.getInstance().build(new Projection().addFromTuple("t", "code","name").add("SUM(t.age)"))).isEqualTo("SELECT t.code,t.name,SUM(t.age)");
		assertThat(SelectStringBuilder.getInstance().build("t")).isEqualTo("SELECT t");
	}

}