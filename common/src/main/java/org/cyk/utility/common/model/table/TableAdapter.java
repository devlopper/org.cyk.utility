package org.cyk.utility.common.model.table;

public class TableAdapter< ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>,
COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> implements TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> {

	@Override
	public Table<ROW_DIMENSION, COLUMN_DIMENSION, ROW_DATA, COLUMN_DATA, CELL_TYPE, CELL_VALUE> getTable() {
		return null;
	}

	@Override
	public void beforeBuild() {}

	@Override
	public void afterBuild() {}

	

}
