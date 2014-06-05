package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractDataTable<
	ROW_DIMENSION extends AbstractDimension<ROW_DATA, CELL_TYPE, CELL_VALUE>,
	COLUMN_DIMENSION extends AbstractDimension<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
	ROW_DATA,COLUMN_DATA,CELL_TYPE extends AbstractCell<CELL_VALUE>,CELL_VALUE> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 5164167344286124195L;
 
	protected List<ROW_DIMENSION> rows = new ArrayList<>();
	protected List<COLUMN_DIMENSION> columns = new ArrayList<>();
	protected CellAddStrategy cellAddStrategy = CellAddStrategy.ROW_ONLY;
	
	public boolean addColumn(COLUMN_DIMENSION column) {
		column.setIndex((byte) columns.size());
		return columns.add(column);
	}

	public boolean addRow(ROW_DIMENSION row) {
		row.setIndex((byte) rows.size());
		return rows.add(row);
	}
	
	public abstract boolean addRow(ROW_DATA aRowData);
	
	public abstract void updateRow(ROW_DIMENSION row,ROW_DATA aRowData);
	
	public abstract boolean addRow(Collection<ROW_DATA> theRowData);
	
	public boolean addCell(ROW_DIMENSION row,COLUMN_DIMENSION column,CELL_TYPE value){
		return row.addCell(value);
	}
	
	public CELL_TYPE cell(ROW_DIMENSION row,COLUMN_DIMENSION column){
		return rows.get(row.getIndex()).getCells().get(column.getIndex());
	}
	
	protected void clear(){
		rows.clear();
		
	}
	
	
}
