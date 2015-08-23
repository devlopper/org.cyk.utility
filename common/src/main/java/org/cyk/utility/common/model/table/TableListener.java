package org.cyk.utility.common.model.table;

public interface TableListener< 
ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>, 
COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> {

	Table<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> getTable();
	
	void beforeBuild();
	
	void afterBuild();
	
	
	
	
	
}
