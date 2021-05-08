package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.UpdateStringBuilder;
import org.cyk.utility.persistence.server.query.string.UpdateStringBuilder.Tuple;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class UpdateStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void update(){
		assertThat(UpdateStringBuilder.getInstance().build(new Tuple().add("T t"))).isEqualTo("UPDATE T t");
		assertThat(UpdateStringBuilder.getInstance().build("T t")).isEqualTo("UPDATE T t");
	}
}