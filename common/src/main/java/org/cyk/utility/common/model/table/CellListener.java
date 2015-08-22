package org.cyk.utility.common.model.table;

public interface CellListener<ROW extends Row<ROW_DATA, CELL, VALUE>, COLUMN extends Column<COLUMN_DATA, CELL, VALUE>,ROW_DATA,COLUMN_DATA,CELL extends Cell<VALUE>,VALUE> {
	
	CELL create();
	void created(CELL cell);
	
	void add(ROW row,COLUMN column,CELL cell);
	void added(ROW row,COLUMN column,CELL cell);
	
	/**
	 * Cells will be added to the row
	 * @param row
	 */
	void add(ROW row);
	/**
	 * Cells have been added to the row
	 * @param row
	 */
	void added(ROW row);
	
	VALUE getValue(ROW row,COLUMN column);
	
}
