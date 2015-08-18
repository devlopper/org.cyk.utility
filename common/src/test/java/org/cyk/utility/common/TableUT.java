package org.cyk.utility.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.model.table.DefaultCell;
import org.cyk.utility.common.model.table.DefaultColumn;
import org.cyk.utility.common.model.table.DefaultRow;
import org.cyk.utility.common.model.table.DefaultTable;
import org.cyk.utility.common.model.table.TableAdapter;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Test;


public class TableUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void simple(){
		DefaultTable<RowData> table = new DefaultTable<>(RowData.class);
		table.getTableListeners().add(new TableAdapter<DefaultRow<RowData>, DefaultColumn, RowData, String, DefaultCell, String>(){
			
		});
		
		table.addColumnFromDataClass();
		
		table.addRow(new RowData("Yao"));
		table.addRow(new RowData("Zadi"));
		table.addRow(new RowData("Kodom"));
		
		table.build();
		
		Assert.assertEquals("Row 1",table.getRows().get(0).getIndex().intValue(), 0);
		Assert.assertEquals("Row 2",table.getRows().get(1).getIndex().intValue(), 1);
		Assert.assertEquals("Row 3",table.getRows().get(2).getIndex().intValue(), 2);
	}
	
	@Test
	public void complex(){
		DefaultTable<RowData> table = new DefaultTable<>(RowData.class);
		table.getTableListeners().add(new TableAdapter<DefaultRow<RowData>, DefaultColumn, RowData, String, DefaultCell, String>(){
			@Override
			public Boolean excludeFromCount(DefaultRow<RowData> row) {
				return row.getData().getValue().equals("Total");
			}
		});
		table.addColumnFromDataClass();
		
		table.addRow(new RowData("Yao"));
		table.addRow(new RowData("Zadi"));
		table.addRow(new RowData("Total"));
		table.addRow(new RowData("N1"));
		table.addRow(new RowData("N2"));
		table.addRow(new RowData("Kodom"));
		table.addRow(new RowData("Total"));
		
		table.build();
		
		Assert.assertEquals("Row 1",table.getRows().get(0).getIndex().intValue(), 0);
		Assert.assertEquals("Row 2",table.getRows().get(1).getIndex().intValue(), 1);
		Assert.assertNull("Row 3",table.getRows().get(2).getIndex());
		Assert.assertEquals("Row 4",table.getRows().get(3).getIndex().intValue(), 2);
		Assert.assertEquals("Row 5",table.getRows().get(4).getIndex().intValue(), 3);
		Assert.assertEquals("Row 6",table.getRows().get(5).getIndex().intValue(), 4);
		Assert.assertNull("Row 7",table.getRows().get(6).getIndex());
	}
	
	@Getter @Setter @AllArgsConstructor
	private static class RowData{
		private String value;
	}
	
}
