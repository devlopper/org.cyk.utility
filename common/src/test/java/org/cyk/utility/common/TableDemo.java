package org.cyk.utility.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.model.table.DefaultCell;
import org.cyk.utility.common.model.table.DefaultColumn;
import org.cyk.utility.common.model.table.DefaultRow;
import org.cyk.utility.common.model.table.DefaultTable;
import org.cyk.utility.common.model.table.TableAdapter;

public class TableDemo {

	public static void main(String[] args) {
		DefaultTable<MyEntity> table = new DefaultTable<>(MyEntity.class);
		table.getTableListeners().add(new TableAdapter<DefaultRow<MyEntity>, DefaultColumn, MyEntity, String, DefaultCell, String>(){
			@Override
			public void columns(List<DefaultColumn> columns) {
				Collections.sort(columns, new ColumnComparator());
			}
		});
		
		table.addColumnFromDataClass();
		
		table.addRow(new MyEntity("Yao", "Jean", 24,true));
		table.addRow(new MyEntity("Zadi", "Marc", 37,true));
		table.addRow(new MyEntity("Kodom", "Ella", 33,false));
		
		table.build();
		
		System.out.println(table);

	}
	
	@Getter @Setter @AllArgsConstructor
	private static class MyEntity{
		
		private String firstName,lastName;
		private Integer age;
		private Boolean male;
	}
	
	private static class ColumnComparator implements Comparator<DefaultColumn>{

		@Override
		public int compare(DefaultColumn o1, DefaultColumn o2) {
			return o1.getTitle().compareTo(o2.getTitle());
		}
		
	}

}
