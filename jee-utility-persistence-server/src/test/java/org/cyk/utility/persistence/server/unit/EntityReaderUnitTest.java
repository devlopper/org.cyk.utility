package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutor;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryManager;
import org.cyk.utility.persistence.server.DataType;
import org.cyk.utility.persistence.query.EntityReader;
import org.junit.jupiter.api.Test;

public class EntityReaderUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void readMany() { 	
    	assertThat(EntityReader.getInstance().readMany(DataType.class).stream().map(x -> x.getCode()).collect(Collectors.toList()))
    	.containsExactlyInAnyOrder("SG","LG","ST","INT");
	}
    
    @Test
	public void readOneBySystemIdentifier() { 	
    	assertThat(EntityReader.getInstance().readOneBySystemIdentifier(DataType.class,"dt1").getCode()).isEqualTo("SG");
	}
    
    @Test
	public void readOneByBusinessIdentifier() { 	
    	assertThat(EntityReader.getInstance().readOneByBusinessIdentifier(DataType.class,"SG").getName()).isEqualTo("String");
	}
    
    @Test
	public void readOneWhereFieldEquals() {
    	assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(0);
    	assertThat(EntityReader.getInstance().readOneWhereFieldEquals(DataType.class,"name","Long").getCode()).isEqualTo("LG");
    	assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(1);
    	assertThat(EntityReader.getInstance().readOneWhereFieldEquals(DataType.class,"name","String").getCode()).isEqualTo("SG");
    	assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(1);
    	assertThat(EntityReader.getInstance().readOneWhereFieldEquals(DataType.class,"code","LG").getName()).isEqualTo("Long");
    	assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(2);
	}
	
	@Test
	public void readDataType_registerNamedQuery_auto(){
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(0);
		assertThat(EntityReader.getInstance().readMany(DataType.class)).isNotEmpty();
		assertThat(EntityReader.getInstance().readMany(DataType.class).stream().map(x -> x.getCode())).containsExactly("SG","INT","LG","ST");
		assertThat(EntityReader.getInstance().readMany(DataType.class).stream().map(x -> x.getName())).containsExactly("String","Integer","Long","Short");
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(1);
		assertThat(EntityReader.getInstance().readMany(DataType.class)).isNotEmpty();
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(1);
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
		QueryManager.getInstance().register(new Query().setIdentifier("q1").setValue("SELECT t FROM DataType t ORDER BY t.code ASC").setResultClass(DataType.class));
		QueryExecutorArguments arguments = new QueryExecutorArguments().setQuery(new Query().setIdentifier("q1")).setSortOrders(Map.of("code",SortOrder.DESCENDING));
		
		assertThat(QueryExecutor.getInstance().executeReadMany(DataType.class, arguments).stream().map(x -> x.getCode())).containsExactly("ST","SG","LG","INT");		
	}
}