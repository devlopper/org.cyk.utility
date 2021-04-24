package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryExecutor;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.persistence.query.QueryManager;
import org.cyk.utility.persistence.query.QueryName;
import org.cyk.utility.persistence.server.DataType;
import org.cyk.utility.persistence.server.query.QueryExecutorImpl;
import org.cyk.utility.persistence.server.query.executor.DynamicManyExecutor;
import org.junit.jupiter.api.Test;

public class DynamicManyExecutorUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void readDataType_namedQuery(){
		QueryExecutorImpl.LOGGABLE = Boolean.TRUE;
		QueryExecutorImpl.LOG_LEVEL = Level.INFO;
		
		String queryIdentifier = QueryIdentifierBuilder.getInstance().build(DataType.class, QueryName.READ_DYNAMIC);
		//Collection<DataType> dataTypes = DynamicManyExecutor.getInstance().read(DataType.class, new QueryExecutorArguments().setQuery(new Query().setIdentifier(queryIdentifier)));
		Collection<DataType> dataTypes = DynamicManyExecutor.getInstance().read(DataType.class);
		//Collection<DataType> dataTypes = EntityReader.getInstance().readMany(DataType.class, new QueryExecutorArguments().setQuery(new Query().setIdentifier(queryIdentifier)));
		System.out.println("DynamicManyExecutorUnitTest.readDataType_namedQuery() ::: "+dataTypes);
	}
	
	@Test
	public void readDataType_queries(){
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(0);
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class)).isNotEmpty();
		
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class).stream().map(x -> x.getCode())).containsExactly("INT","LG","SG","ST");
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class).stream().map(x -> x.getName())).containsExactly("Integer","Long","String","Short");
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(0);
		
		assertThat(DynamicManyExecutor.getInstance().read(DataType.class)).isNotEmpty();
		assertThat(CollectionHelper.getSize(QueryManager.getInstance().getQueries())).isEqualTo(0);
	}
	
	@Test
	public void readDataType_defaults(){
		Collection<DataType> collection = DynamicManyExecutor.getInstance().read(DataType.class);
		assertThat(collection).isNotEmpty();		
		assertThat(collection.stream().map(x -> x.getIdentifier())).containsExactly("dt2","dt3","dt1","dt4");
		assertThat(collection.stream().map(x -> x.getCode())).containsExactly("INT","LG","SG","ST");
		assertThat(collection.stream().map(x -> x.getName())).containsExactly("Integer","Long","String","Short");
		assertThat(collection.stream().map(x -> x.getDescription())).containsExactly((String)null,(String)null,(String)null,(String)null);
		assertThat(collection.stream().map(x -> x.getCodeAndName())).containsExactly((String)null,(String)null,(String)null,(String)null);
	}
	
	@Test
	public void readDataType_specificFields_withoutTransient(){
		Collection<DataType> collection = DynamicManyExecutor.getInstance().read(DataType.class,new QueryExecutorArguments()
				.addProjectionsFromStrings(DataType.FIELD_IDENTIFIER,DataType.FIELD_NAME));
		assertThat(collection).isNotEmpty();		
		assertThat(collection.stream().map(x -> x.getIdentifier())).containsExactly("dt2","dt3","dt1","dt4");
		assertThat(collection.stream().map(x -> x.getCode())).containsExactly((String)null,(String)null,(String)null,(String)null);
		assertThat(collection.stream().map(x -> x.getName())).containsExactly("Integer","Long","String","Short");
		assertThat(collection.stream().map(x -> x.getDescription())).containsExactly((String)null,(String)null,(String)null,(String)null);
		assertThat(collection.stream().map(x -> x.getCodeAndName())).containsExactly((String)null,(String)null,(String)null,(String)null);
	}
	
	@Test
	public void readDataType_specificFields_withTransient(){
		Collection<DataType> collection = DynamicManyExecutor.getInstance().read(DataType.class,new QueryExecutorArguments()
				.addProjectionsFromStrings(DataType.FIELD_IDENTIFIER,DataType.FIELD_CODE_AND_NAME));
		assertThat(collection).isNotEmpty();		
		assertThat(collection.stream().map(x -> x.getIdentifier())).containsExactly("dt2","dt3","dt1","dt4");
		assertThat(collection.stream().map(x -> x.getCode())).containsExactly((String)null,(String)null,(String)null,(String)null);
		assertThat(collection.stream().map(x -> x.getName())).containsExactly((String)null,(String)null,(String)null,(String)null);
		assertThat(collection.stream().map(x -> x.getDescription())).containsExactly((String)null,(String)null,(String)null,(String)null);
		assertThat(collection.stream().map(x -> x.getCodeAndName())).containsExactly("INT Integer","LG Long","SG String","ST Short");
	}
	
	@Test
	public void readDataType_specificOrder_ascending(){
		Collection<DataType> collection = DynamicManyExecutor.getInstance().read(DataType.class,new QueryExecutorArguments().setSortOrders(Map.of(DataType.FIELD_CODE,SortOrder.ASCENDING)));
		assertThat(collection).isNotEmpty();		
		assertThat(collection.stream().map(x -> x.getIdentifier())).containsExactly("dt2","dt3","dt1","dt4");
		assertThat(collection.stream().map(x -> x.getCode())).containsExactly("INT","LG","SG","ST");
		assertThat(collection.stream().map(x -> x.getName())).containsExactly("Integer","Long","String","Short");
		assertThat(collection.stream().map(x -> x.getDescription())).containsExactly((String)null,(String)null,(String)null,(String)null);
		assertThat(collection.stream().map(x -> x.getCodeAndName())).containsExactly((String)null,(String)null,(String)null,(String)null);
	}
	
	@Test
	public void readDataType_specificOrder_descending(){
		Collection<DataType> collection = DynamicManyExecutor.getInstance().read(DataType.class,new QueryExecutorArguments().setSortOrders(Map.of(DataType.FIELD_CODE,SortOrder.DESCENDING)));
		assertThat(collection).isNotEmpty();		
		assertThat(collection.stream().map(x -> x.getIdentifier())).containsExactly("dt4","dt1","dt3","dt2");
		assertThat(collection.stream().map(x -> x.getCode())).containsExactly("ST","SG","LG","INT");
		assertThat(collection.stream().map(x -> x.getName())).containsExactly("Short","String","Long","Integer");
		assertThat(collection.stream().map(x -> x.getDescription())).containsExactly((String)null,(String)null,(String)null,(String)null);
		assertThat(collection.stream().map(x -> x.getCodeAndName())).containsExactly((String)null,(String)null,(String)null,(String)null);
	}
	
	@Test
	public void dataType_filter_nameContains(){
		assertDataTypeReadFilter("o", 2l
			, new String[] {"dt3","dt4"}
			, new String[] {"Long","Short"}
		);
		
		assertDataTypeReadFilter("I", 2l
				, new String[] {"dt2","dt1"}
				, new String[] {"Integer","String"}
			);
		
		assertDataTypeReadFilter("i", 2l
				, new String[] {"dt2","dt1"}
				, new String[] {"Integer","String"}
			);
		
		assertDataTypeReadFilter("ri", 1l
				, new String[] {"dt1"}
				, new String[] {"String"}
			);
	}
	
	/**/
	
	private void assertDataTypeReadFilter(String name,Long expectedCount,String[] expectedIdentifiers,String[] expectedNames){
		Collection<DataType> collection = DynamicManyExecutor.getInstance().read(DataType.class,new QueryExecutorArguments().addFilterField(DataType.FIELD_NAME, name));
		assertThat(collection).isNotEmpty();
		assertThat(collection.stream().map(x -> x.getIdentifier())).containsExactly(expectedIdentifiers);
		assertThat(collection.stream().map(x -> x.getName())).containsExactly(expectedNames);
		
		assertThat(DynamicManyExecutor.getInstance().count(DataType.class,new QueryExecutorArguments()
				.addFilterFieldsValues(DataType.FIELD_NAME,name))).isEqualTo(expectedCount);
	}
}