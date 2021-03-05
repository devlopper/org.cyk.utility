package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.QueryHelper;
import org.junit.jupiter.api.Test;

public class EntityReaderUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void readDataType_registerNamedQuery_auto(){
		assertThat(QueryHelper.QUERIES.getSize()).isEqualTo(0);
		assertThat(EntityReader.getInstance().readMany(DataType.class)).isNotEmpty();
		assertThat(EntityReader.getInstance().readMany(DataType.class).stream().map(x -> x.getCode())).containsExactly("SG","INT","LG","ST");
		assertThat(EntityReader.getInstance().readMany(DataType.class).stream().map(x -> x.getName())).containsExactly("String","Integer","Long","Short");
		assertThat(QueryHelper.QUERIES.getSize()).isEqualTo(1);
		assertThat(EntityReader.getInstance().readMany(DataType.class)).isNotEmpty();
		assertThat(QueryHelper.QUERIES.getSize()).isEqualTo(1);
	}
}