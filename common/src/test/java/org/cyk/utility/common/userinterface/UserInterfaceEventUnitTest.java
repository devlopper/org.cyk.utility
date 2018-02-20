package org.cyk.utility.common.userinterface;

import java.io.Serializable;
import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.collection.DataTable.Row;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.event.Event;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceEventUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void build(){
		Form.Master master = new Form.Master();
		Form.Detail detail = master.getDetail();
		DataTable dataTable = new DataTable(){
			private static final long serialVersionUID = 1L;

			@Override
			public DataTable load() {
				addManyRow(Arrays.asList(new MyData()));
				return this;
			}
		};
		detail.addDataTable(dataTable);
		dataTable.getPropertiesMap().setActionOnClass(MyData.class);
		dataTable.getPropertiesMap().setAction(Constant.Action.CREATE);
		dataTable.getPropertiesMap().setFormDetail(detail);
		dataTable.setOnPrepareCallLoad(Boolean.TRUE);
		
		final Event[] events = new Event[1];
		
		dataTable.getPropertiesMap().setCellListener(new DataTable.Cell.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			public DataTable.Cell instanciateOne(DataTable.Column column, DataTable.Row row) {
				final DataTable.Cell cell = super.instanciateOne(column, row);
				if(ArrayUtils.contains(new String[]{"name"},column.getPropertiesMap().getFieldName())){
					events[0] = Event.instanciateOne(cell, new String[]{"name"},null);
					
				}
				return cell;
			}
		});
		
		dataTable.addColumn("name label", "name");
		
		dataTable.prepare();
		dataTable.build();
		
		Row row = (Row) ((CollectionHelper.Instance<?>)dataTable.getPropertiesMap().getRowsCollectionInstance()).getElements().iterator().next();
		dataTable.getColumn("name").getCell(row).getPropertiesMap();
		events[0].getListener().execute();
		
	}
	
	/**/
	
	@Getter @Setter
	public static class MyData implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private String name,value;
		
	}
	
}
