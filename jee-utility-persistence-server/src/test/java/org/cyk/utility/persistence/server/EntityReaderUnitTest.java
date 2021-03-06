package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutor;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
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
	
	@Test
	public void readDataType_dynamicQuery_orderByCode_asc(){
		QueryExecutorArguments arguments = new QueryExecutorArguments().setQuery(new Query().setValue("SELECT t FROM DataType t"))
				.setSortOrders(Map.of("t.code",SortOrder.ASCENDING));
		assertThat(QueryExecutor.getInstance().executeReadMany(DataType.class, arguments).stream().map(x -> x.getCode())).containsExactly("INT","LG","SG","ST");		
	}
	
	@Test
	public void readDataType_dynamicQuery_orderByCode_desc(){
		QueryExecutorArguments arguments = new QueryExecutorArguments().setQuery(new Query().setValue("SELECT t FROM DataType t"))
				.setSortOrders(Map.of("t.code",SortOrder.DESCENDING));
		assertThat(QueryExecutor.getInstance().executeReadMany(DataType.class, arguments).stream().map(x -> x.getCode())).containsExactly("ST","SG","LG","INT");	
	}
	
	@Test
	public void readDataType_dynamicQuery_orderByCode_asc_UI(){
		QueryHelper.addQueries(new Query().setIdentifier("q1").setValue("SELECT t FROM DataType t ORDER BY t.code ASC").setResultClass(DataType.class));
		QueryExecutorArguments arguments = new QueryExecutorArguments().setQuery(new Query().setIdentifier("q1")).setSortOrders(Map.of("code",SortOrder.DESCENDING));
		
		assertThat(QueryExecutor.getInstance().executeReadMany(DataType.class, arguments).stream().map(x -> x.getCode())).containsExactly("ST","SG","LG","INT");		
	}
}