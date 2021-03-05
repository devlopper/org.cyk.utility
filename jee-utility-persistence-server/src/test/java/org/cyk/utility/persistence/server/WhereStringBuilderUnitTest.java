package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.WhereStringBuilder;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class WhereStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void where(){
		assertThat(WhereStringBuilder.getInstance().build(new Predicate().add("t.f = :a"))).isEqualTo("WHERE t.f = :a");
		assertThat(WhereStringBuilder.getInstance().build("t.a is NULL")).isEqualTo("WHERE t.a is NULL");
		assertThat(WhereStringBuilder.getInstance().build(new Predicate().add("t.f > 1","AND","t.d < 5"))).isEqualTo("WHERE t.f > 1 AND t.d < 5");
		assertThat(WhereStringBuilder.getInstance().build(new Predicate().add("t.f > 1").and().add("t.d < 5"))).isEqualTo("WHERE t.f > 1 AND t.d < 5");
		assertThat(WhereStringBuilder.getInstance().build(new Predicate().add("t.f > 1").or().add("t.d < 5"))).isEqualTo("WHERE t.f > 1 OR t.d < 5");
	}
}