package org.cyk.utility.common.userinterface;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.userinterface.collection.Cell;
import org.cyk.utility.common.userinterface.collection.Column;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.collection.Row;
import org.cyk.utility.common.userinterface.container.form.Form;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.event.Event;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;

public class UserInterfaceEventUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void build(){
		Form master = new Form();
		FormDetail detail = master.getDetail();
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
		
		dataTable.getPropertiesMap().setCellListener(new Cell.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			public Cell instanciateOne(Column column, Row row) {
				final Cell cell = super.instanciateOne(column, row);
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
