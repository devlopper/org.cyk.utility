package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityCreator;
import org.junit.jupiter.api.Test;

public class EntityCreatorUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void create_one(){
		Long count = EntityCounter.getInstance().count(DataType.class);
		DataType dataType = new DataType();
		dataType.setCode("01");
		EntityCreator.getInstance().createOneInTransaction(dataType);
		assertThat(EntityCounter.getInstance().count(DataType.class)).isEqualTo(NumberHelper.add(count,1).longValue());
		assertThat(dataType.getIdentifier()).isEqualTo("01");
		assertThat(dataType.getCode()).isEqualTo("01");
		assertThat(dataType.getName()).isEqualTo("01");
	}
}