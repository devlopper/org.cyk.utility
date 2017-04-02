package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.util.ArrayList;

import org.cyk.utility.common.generator.AbstractGeneratable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCrossedDataReport<T,CELL extends AbstractCrossedDataReport.AbstractCell<?>> extends AbstractGeneratable<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected ArrayList<CELL> cells = new ArrayList<>();
	
	protected abstract Class<CELL> getCellClass();
	
	public AbstractCrossedDataReport<T,CELL> addCell(String row,String column,String value){
		CELL cell = newInstance(getCellClass()); 
		cell.setRow(row);
		cell.setColumn(column);
		cell.setValue(value);
		cells.add(cell);
		return this;
	}
	
	@Override
	public void generate() {
		for(int i = 0 ; i < 4 ; i++){
			String row = "row"+i;
			for(int j = 0 ; j < 7 ; j++){
				String column = "column"+j;
				addCell(row, column, "value"+i+j);
			}
		}
	}
	
	/**/
	
	@Getter @Setter @NoArgsConstructor
	public static abstract class AbstractCell<T> extends AbstractGeneratable<T> implements Serializable {

		private static final long serialVersionUID = 1L;
		
		protected String row,column,value;
		
		public AbstractCell(String row, String column, String value) {
			super();
			this.row = row;
			this.column = column;
			this.value = value;
		}

		@Override
		public void generate() {
			
		}
		
	}
	
	/**/
	
	@Getter @Setter
	public static class Extends extends AbstractCrossedDataReport<Extends,Extends.Cell> implements Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		protected Class<Cell> getCellClass() {
			return Cell.class;
		}
		
		/**/
		
		@Getter @Setter
		public static class Cell extends AbstractCrossedDataReport.AbstractCell<Cell> implements Serializable {

			private static final long serialVersionUID = 1L;
			

			
		}
		
	}
	
}