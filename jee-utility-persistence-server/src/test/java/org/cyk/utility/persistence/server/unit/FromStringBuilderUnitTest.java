package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.FromStringBuilder;
import org.cyk.utility.persistence.server.query.string.FromStringBuilder.Tuple;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class FromStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void from(){
		assertThat(FromStringBuilder.getInstance().build(new Tuple().add("T t", "E e"))).isEqualTo("FROM T t,E e");
		assertThat(FromStringBuilder.getInstance().build("T t")).isEqualTo("FROM T t");
		assertThat(FromStringBuilder.getInstance().build(new Tuple().add("T t","E e").addJoins("LEFT JOIN N n ON n.e = e"))).isEqualTo("FROM T t,E e LEFT JOIN N n ON n.e = e");
	}

	@Test
	public void from_leftJoin_tuple(){
		assertThat(FromStringBuilder.getInstance().build(new Tuple("T").leftJoinTuple("N"))).isEqualTo("FROM T t LEFT JOIN N n ON n = t.n");
	}
}