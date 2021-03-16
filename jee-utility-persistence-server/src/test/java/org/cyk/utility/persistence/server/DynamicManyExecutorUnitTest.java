package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.QueryManager;
import org.cyk.utility.persistence.server.query.executor.DynamicManyExecutor;
import org.junit.jupiter.api.Test;

public class DynamicManyExecutorUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void readDataType_queries(){
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(0);
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class)).isNotEmpty();
		
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class).stream().map(x -> x.getCode())).containsExactly("SG","INT","LG","ST");
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class).stream().map(x -> x.getName())).containsExactly("String","Integer","Long","Short");
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(0);
		
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class)).isNotEmpty();
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(0);
	}
	
	@Test
	public void readDataType_identifier_code_name(){
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class)).isNotEmpty();
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class).stream().map(x -> x.getCode())).containsExactly("SG","INT","LG","ST");
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class).stream().map(x -> x.getName())).containsExactly("String","Integer","Long","Short");
	}
	
	//@Test
	public void readDataType_identifier_code(){
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class)).isNotEmpty();
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class).stream().map(x -> x.getCode())).containsExactly("SG","INT","LG","ST");
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class).stream().map(x -> x.getName())).containsExactly(null,null,null,null);
	}
}