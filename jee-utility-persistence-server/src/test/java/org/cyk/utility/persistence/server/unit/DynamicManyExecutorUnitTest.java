package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryManager;
import org.cyk.utility.persistence.server.DataType;
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
	
	@Test
	public void readDataType_identifier_code(){
		QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
		queryExecutorArguments.addProjectionsFromStrings("identifier","code");
		Collection<DataType> collection = DynamicManyExecutor.getInstance().read(DataType.class,queryExecutorArguments);
		assertThat(collection).isNotEmpty();
		assertThat(collection.stream().map(x -> x.getCode())).containsExactly("SG","INT","LG","ST");
		assertThat(collection.stream().map(x -> x.getName())).containsExactly(null,null,null,null);
	}
	
	@Test
	public void readDataType_code(){
		QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
		queryExecutorArguments.addProjectionsFromStrings("code").setSortOrders(Map.of("code",SortOrder.ASCENDING));
		Collection<DataType> collection = DynamicManyExecutor.getInstance().read(DataType.class,queryExecutorArguments);
		assertThat(collection).isNotEmpty();
		assertThat(collection.stream().map(x -> x.getIdentifier())).containsExactly(null,null,null,null);
		assertThat(collection.stream().map(x -> x.getCode())).containsExactly("INT","LG","SG","ST");
		assertThat(collection.stream().map(x -> x.getName())).containsExactly(null,null,null,null);
	}
}