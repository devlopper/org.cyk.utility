package org.cyk.utility.common;

import java.lang.reflect.Field;
import java.util.List;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.model.table.ColumnAdapter;
import org.cyk.utility.common.model.table.DefaultCell;
import org.cyk.utility.common.model.table.DefaultColumn;
import org.cyk.utility.common.model.table.DefaultRow;
import org.cyk.utility.common.model.table.DefaultTable;
import org.cyk.utility.common.model.table.RowAdapter;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class TableUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void manual(){
		DefaultTable<String> table = new DefaultTable<>(String.class);
		table.build();
		table.addColumnTitled("C1");
		
		DefaultRow<String> dr = new DefaultRow<>();
		dr.getCells().add(new DefaultCell("Yao"));
		table.getRows().add(dr);
		
		Assert.assertEquals("Row 1","Yao",table.getRows().get(0).getCells().get(0).getValue());
	}
	
	//@Test
	public void simple(){
		DefaultTable<RowData> table = new DefaultTable<>(RowData.class);
		
		table.addRow(new RowData("Yao"));
		table.addRow(new RowData("Zadi"));
		table.addRow(new RowData("Kodom"));
		
		table.build();
		
		Assert.assertEquals("Row 1",0,table.getRows().get(0).getUiIndex().intValue());
		Assert.assertEquals("Row 2",1,table.getRows().get(1).getUiIndex().intValue());
		Assert.assertEquals("Row 3",2,table.getRows().get(2).getUiIndex().intValue());
	}
	
	//@Test
	public void complex1(){
		DefaultTable<RowData> table = new DefaultTable<>(RowData.class);
		table.getRowListeners().add(new RowAdapter<DefaultRow<RowData>, RowData, DefaultCell, String>(){
			@Override
			public Boolean countable(DefaultRow<RowData> row) {
				return !row.getData().getValue().equals("Total");
			}
			
		});
		
		table.addRow(new RowData("Yao"));
		table.addRow(new RowData("Zadi"));
		table.addRow(new RowData("Total"));
		table.addRow(new RowData("N1"));
		table.addRow(new RowData("N2"));
		table.addRow(new RowData("Kodom"));
		table.addRow(new RowData("Total"));
		
		table.build();
		
		Assert.assertEquals("Row 1",0,table.getRows().get(0).getUiIndex().intValue());
		Assert.assertEquals("Row 2",1,table.getRows().get(1).getUiIndex().intValue());
		Assert.assertNull("Row 3",table.getRows().get(2).getUiIndex());
		Assert.assertEquals("Row 4",2,table.getRows().get(3).getUiIndex().intValue());
		Assert.assertEquals("Row 5",3,table.getRows().get(4).getUiIndex().intValue());
		Assert.assertEquals("Row 6",4,table.getRows().get(5).getUiIndex().intValue());
		Assert.assertNull("Row 7",table.getRows().get(6).getUiIndex());
	}
	
	@Test
	public void columnOneListener(){
		DefaultTable<RowData> table = new DefaultTable<>(RowData.class);
		table.getColumnListeners().add(new ColumnAdapter<DefaultColumn, String, DefaultCell, String>(){
			@Override
			public void populateFromDataClass(Class<?> aClass,List<Field> fields) {
				fields.addAll(CommonUtils.getInstance().getAllFields(aClass, Input.class));
			}
			@Override
			public Boolean isColumn(Field field) {
				return Boolean.FALSE;
			}
			
		});
		table.build();
		
		Assert.assertNull(table.getColumn("value"));
	}
	
	@Test
	public void columnTwoListeners(){
		DefaultTable<RowData> table = new DefaultTable<>(RowData.class);
		table.getColumnListeners().add(new ColumnAdapter<DefaultColumn, String, DefaultCell, String>(){
			@Override
			public void populateFromDataClass(Class<?> aClass,List<Field> fields) {
				fields.addAll(CommonUtils.getInstance().getAllFields(aClass, Input.class));
			}
			@Override
			public Boolean isColumn(Field field) {
				return Boolean.FALSE;
			}
			
		});
		table.getColumnListeners().add(new ColumnAdapter<DefaultColumn, String, DefaultCell, String>(){
			@Override
			public void populateFromDataClass(Class<?> aClass,List<Field> fields) {
				fields.addAll(CommonUtils.getInstance().getAllFields(aClass, Input.class));
			}
			@Override
			public Boolean isColumn(Field field) {
				return Boolean.TRUE;
			}
			
		});
		table.build();
		
		Assert.assertNotNull(table.getColumn("value"));
	}
	
	/*
	@Test
	public void complex2(){
		DefaultTable<RowData> table = new DefaultTable<>(RowData.class);
		DefaultRow<RowData> row = null;
		table.addColumnFromDataClass();
		
		table.addRow(new RowData("Yao"));
		table.addRow(new RowData("Zadi"));
		row = table.addRow(new RowData("Total"));
		row.setType(DimensionType.SUMMARY);
		table.addRow(new RowData("N1"));
		table.addRow(new RowData("N2"));
		table.addRow(new RowData("Kodom"));
		row = table.addRow(new RowData("Total"));
		row.setType(DimensionType.SUMMARY);
			
		table.build();
		
		Assert.assertEquals("Row 1",0,table.getRows().get(0).getUiIndex().intValue());
		Assert.assertEquals("Row 2",1,table.getRows().get(1).getUiIndex().intValue());
		Assert.assertNull("Row 3",table.getRows().get(2).getUiIndex());
		Assert.assertEquals("Row 4",2,table.getRows().get(3).getUiIndex().intValue());
		Assert.assertEquals("Row 5",3,table.getRows().get(4).getUiIndex().intValue());
		Assert.assertEquals("Row 6",4,table.getRows().get(5).getUiIndex().intValue());
		Assert.assertNull("Row 7",table.getRows().get(6).getUiIndex());
	}*/
	
	@Getter @Setter @AllArgsConstructor
	private static class RowData{
		@Input @InputText private String value;
	}
	
}
