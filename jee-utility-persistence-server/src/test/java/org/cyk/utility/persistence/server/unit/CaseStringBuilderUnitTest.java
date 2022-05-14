package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.CaseStringBuilder;
import org.cyk.utility.persistence.server.query.string.CaseStringBuilder.Case;
import org.junit.jupiter.api.Test;

public class CaseStringBuilderUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void from(){
		assertThat(CaseStringBuilder.getInstance().build(new Case().add("WHEN t.id = 1 THEN 2"))).isEqualTo("CASE WHEN t.id = 1 THEN 2 END");
		assertThat(CaseStringBuilder.getInstance().build("WHEN t.id = 1 THEN 2")).isEqualTo("CASE WHEN t.id = 1 THEN 2 END");
		assertThat(CaseStringBuilder.getInstance().build(new Case().when("t.id = 1","2"))).isEqualTo("CASE WHEN t.id = 1 THEN 2 END");
		assertThat(CaseStringBuilder.getInstance().build(new Case().when("t.id = 1","2").when("t.code = 'a'", "'x'"))).isEqualTo("CASE WHEN t.id = 1 THEN 2 WHEN t.code = 'a' THEN 'x' END");
		assertThat(CaseStringBuilder.getInstance().build(new Case().when("t.id = 1","2").else_("100"))).isEqualTo("CASE WHEN t.id = 1 THEN 2 ELSE 100 END");
	}
	
	@Test
	public void instantiateWhenFieldIsNullThenZeroElseField(){
		assertThat(CaseStringBuilder.getInstance().build(Case.instantiateWhenFieldIsNullThenZeroElseField("t.id"))).isEqualTo("CASE WHEN t.id IS NULL THEN 0 ELSE t.id END");
	}
	
	@Test
	public void instantiateWhenIsNotGreaterThanOrEqualZeroThenZeroAndBuild(){
		assertThat(Case.instantiateWhenIsNotGreaterThanOrEqualZeroThenZeroAndBuild("t.id")).isEqualTo("CASE WHEN t.id < 0 THEN t.id ELSE 0 END");
	}
	
	@Test
	public void instantiateWhenIsNotLessThanOrEqualZeroThenZeroAndBuild(){
		assertThat(Case.instantiateWhenIsNotLessThanOrEqualZeroThenZeroAndBuild("t.id")).isEqualTo("CASE WHEN t.id > 0 THEN t.id ELSE 0 END");
	}
}