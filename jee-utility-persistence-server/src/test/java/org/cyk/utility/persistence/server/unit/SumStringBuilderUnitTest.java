package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.SumStringBuilder;
import org.cyk.utility.persistence.server.query.string.CaseStringBuilder;
import org.cyk.utility.persistence.server.query.string.CaseStringBuilder.Case;
import org.cyk.utility.persistence.server.query.string.SumStringBuilder.Sum;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class SumStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void sum(){
		assertThat(SumStringBuilder.getInstance().build(new Sum().add("f1"))).isEqualTo("SUM(f1)");
		assertThat(SumStringBuilder.getInstance().build("f1")).isEqualTo("SUM(f1)");
		assertThat(SumStringBuilder.getInstance().build(new Sum().add("f1","f2"))).isEqualTo("SUM(f1+f2)");
		
		assertThat(SumStringBuilder.getInstance().build(new Sum().add(
				CaseStringBuilder.getInstance().build(Case.instantiateWhenFieldIsNullThenZeroElseField("f1"))
		))).isEqualTo("SUM(CASE WHEN f1 IS NULL THEN 0 ELSE f1 END)");
	}
}