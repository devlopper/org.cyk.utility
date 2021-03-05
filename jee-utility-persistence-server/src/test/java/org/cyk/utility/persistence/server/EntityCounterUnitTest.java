package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.QueryHelper;
import org.junit.jupiter.api.Test;

public class EntityCounterUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void countDataType_registerNamedQuery_auto(){
		assertThat(QueryHelper.QUERIES.getSize()).isEqualTo(0);
		assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(4);
		assertThat(QueryHelper.QUERIES.getSize()).isEqualTo(1);
		assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(4);
		assertThat(QueryHelper.QUERIES.getSize()).isEqualTo(1);
	}
}