package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.util.ArrayList;

import org.cyk.utility.common.generator.AbstractGeneratable;
import org.cyk.utility.common.generator.RandomDataProvider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCrossedDataReport<T,CELL extends AbstractCrossedDataReport.AbstractCell<?>> extends AbstractGeneratable<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Class<CELL> cellClass;
	protected ArrayList<CELL> cells = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public AbstractCrossedDataReport() {
		cellClass = (Class<CELL>) commonUtils.getClassParameterAt(getClass(), 1);
	}
	
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
		for(String row : getGenerateRows())
			for(String column : getGenerateColumns())
				addCell(row, column, RandomDataProvider.getInstance().randomInt(1, 99)+"");
	}
	
	protected String[] getGenerateRows(){
		return new String[]{"row1","row2","row3","row4","row5"};
	}
	
	protected String[] getGenerateColumns(){
		return new String[]{"column1","column2","column3","column4","column5","column6","column7","column8"};
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

		/**/
		
		@Getter @Setter
		public static class Cell extends AbstractCrossedDataReport.AbstractCell<Cell> implements Serializable {

			private static final long serialVersionUID = 1L;
			

			
		}
		
	}
	
}