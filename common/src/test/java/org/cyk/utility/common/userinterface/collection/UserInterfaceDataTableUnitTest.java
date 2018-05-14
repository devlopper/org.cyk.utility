package org.cyk.utility.common.userinterface.collection;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.test.TestCase;
import org.cyk.utility.common.userinterface.output.OutputText;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceDataTableUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	static {
		StringHelper.ToStringMapping.Datasource.Adapter.Default.initialize();
	}
	
	/* Column */
	
	@Test
	public void buildColumnNothingSet(){
		TestCase testCase = new TestCase();
		Column.Builder builder = Column.instanciateOneBuilder();
		Column column = builder.execute();
		testCase.assertNotNull(column);
		testCase.assertNotNull(column.getPropertiesMap());
		testCase.assertNull(column.getPropertiesMap().getHeader());
	}
	
	@Test
	public void buildColumnWithLabelSetNull(){
		TestCase testCase = new TestCase();
		Column.Builder builder = Column.instanciateOneBuilder();
		builder.setPropertyHeaderPropertyValue(null);
		Column column = builder.execute();
		testCase.assertNotNull(column.getPropertiesMap().getHeader());
		testCase.assertEquals(OutputText.class, column.getPropertiesMap().getHeader().getClass());
		testCase.assertEquals(null, ((OutputText)column.getPropertiesMap().getHeader()).getPropertiesMap().getValue());
	}
	
	@Test
	public void buildColumnWithLabelSetCode(){
		TestCase testCase = new TestCase();
		Column.Builder builder = Column.instanciateOneBuilder();
		builder.setPropertyHeaderPropertyValue("code");
		Column column = builder.execute();
		testCase.assertNotNull(column.getPropertiesMap().getHeader());
		testCase.assertEquals(OutputText.class, column.getPropertiesMap().getHeader().getClass());
		testCase.assertEquals("code", ((OutputText)column.getPropertiesMap().getHeader()).getPropertiesMap().getValue());
	}
	
	@Test
	public void buildColumnFromFieldString(){
		TestCase testCase = new TestCase();
		Column.Builder builder = Column.instanciateOneBuilder();
		builder.setPropertyFieldName("code");
		builder.getPropertiesMap().setActionOnClass(MyClass.class);
		Column column = builder.execute();
		
		testCase.assertNotNull(column.getPropertiesMap().getHeader());
		testCase.assertEquals(OutputText.class, column.getPropertiesMap().getHeader().getClass());
		testCase.assertEquals("Code", ((OutputText)column.getPropertiesMap().getHeader()).getPropertiesMap().getValue());
		testCase.assertEquals(Column.CellValueSource.ROW_PROPERTY_VALUE, column.getCellValueSource());
		testCase.assertEquals(Cell.ValueType.TEXT, column.getCellValueType());
		/*
		DataTable dataTable = new DataTable();
		dataTable.getPropertiesMap().setActionOnClass(MyClass.class);
		dataTable.addColumnByFieldName("code");
		dataTable.prepare();
		dataTable.build();
		
		System.out.println(dataTable.getColumn("code").getCellValueSource());
		*/
	}
	
	@Test
	public void buildColumnFromFieldStringCode01(){
		TestCase testCase = new TestCase();
		Column.Builder builder = Column.instanciateOneBuilder();
		builder.setPropertyFieldName("code01");
		builder.getPropertiesMap().setActionOnClass(MyClass.class);
		Column column = builder.execute();
		
		testCase.assertNotNull(column.getPropertiesMap().getHeader());
		testCase.assertEquals(OutputText.class, column.getPropertiesMap().getHeader().getClass());
		testCase.assertEquals("Code", ((OutputText)column.getPropertiesMap().getHeader()).getPropertiesMap().getValue());
		testCase.assertEquals(Column.CellValueSource.ROW_PROPERTY_VALUE, column.getCellValueSource());
		testCase.assertEquals(Cell.ValueType.TEXT, column.getCellValueType());
	}
	
	@Test
	public void buildColumnFromFieldStringCode02(){
		TestCase testCase = new TestCase();
		Column.Builder builder = Column.instanciateOneBuilder();
		builder.setPropertyField(FieldHelper.getInstance().get(MyClass.class, "code02"));
		builder.getPropertiesMap().setActionOnClass(MyClass.class);
		Column column = builder.execute();
		
		testCase.assertNotNull(column.getPropertiesMap().getHeader());
		testCase.assertEquals(OutputText.class, column.getPropertiesMap().getHeader().getClass());
		testCase.assertEquals("CODE", ((OutputText)column.getPropertiesMap().getHeader()).getPropertiesMap().getValue());
		testCase.assertEquals(Column.CellValueSource.ROW_PROPERTY_VALUE, column.getCellValueSource());
		testCase.assertEquals(Cell.ValueType.TEXT, column.getCellValueType());
	}
	
	/* Row */
	
	@Test
	public void buildRow(){
		
	}
	
	/**/
	
	@Test
	public void buildDataTable(){
		TestCase testCase = new TestCase();
		DataTable dataTable = new DataTable();
		
		/*{
			private static final long serialVersionUID = 1L;

			@Override
			public DataTable load() {
				//addManyRow(Arrays.asList(new MyData()));
				return this;
			}
		}*/
		
		dataTable.getPropertiesMap().setActionOnClass(MyClass.class);
		dataTable.getPropertiesMap().setAction(Constant.Action.CREATE);
		dataTable.setOnPrepareCallLoad(Boolean.TRUE);
		
		dataTable.addColumn("code label","code");
		dataTable.addColumn("name label","name");
		
		dataTable.prepare();
		dataTable.build();
		
		testCase.assertNotNull(dataTable.getColumn("code"));
		
	}
	
	/**/
	
}
