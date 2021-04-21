package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.GroupStringBuilder;
import org.cyk.utility.persistence.server.query.string.GroupStringBuilder.Group;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class GroupStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void group(){
		assertThat(GroupStringBuilder.getInstance().build(new Group().add("t.f"))).isEqualTo("GROUP BY t.f");
		assertThat(GroupStringBuilder.getInstance().build(new Group().add("t.f","t.d"))).isEqualTo("GROUP BY t.f,t.d");
	}
}