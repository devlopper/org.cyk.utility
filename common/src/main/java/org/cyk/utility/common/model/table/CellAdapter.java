package org.cyk.utility.common.model.table;

public class CellAdapter< ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>,
COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> implements CellListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> {

	@Override
	public CELL_TYPE create() {
		return null;
	}

	@Override
	public void created(CELL_TYPE cell) {}

	@Override
	public void add(ROW_DIMENSION row, COLUMN_DIMENSION column, CELL_TYPE cell) {}

	@Override
	public void added(ROW_DIMENSION row, COLUMN_DIMENSION column, CELL_TYPE cell) {}

	@Override
	public void add(ROW_DIMENSION row) {}

	@Override
	public void added(ROW_DIMENSION row) {}

	@Override
	public CELL_VALUE getValue(ROW_DIMENSION row, COLUMN_DIMENSION column) {
		return null;
	}

	

	

}
