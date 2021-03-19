package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.QueryManager;
import org.cyk.utility.persistence.server.DataType;
import org.junit.jupiter.api.Test;

public class EntityCounterUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void countDataType_registerNamedQuery_auto(){
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(0);
		assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(4);
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(1);
		assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(4);
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(1);
	}
}