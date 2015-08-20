package org.cyk.utility.common.model.table;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TableAdapter< ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>,
COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> implements TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> {

	@Override
	public COLUMN_DIMENSION createColumn() {
		return null;
	}

	@Override
	public ROW_DIMENSION createRow() {
		return null;
	}

	@Override
	public CELL_TYPE createCell() {
		return null;
	}

	@Override
	public void columnAdded(COLUMN_DIMENSION column) {
		
	}

	@Override
	public void rowAdded(ROW_DIMENSION row) {
		
	}

	@Override
	public void updateRow(ROW_DIMENSION row, ROW_DATA data) {
		
	}

	@Override
	public void rowUpdated(ROW_DIMENSION row) {
		
	}

	@Override
	public void rowRemoved(ROW_DIMENSION row) {
		
	}

	@Override
	public CELL_VALUE cellValue(ROW_DIMENSION row, COLUMN_DIMENSION column) {
		return null;
	}

	@Override
	public void cellAdded(ROW_DIMENSION row, COLUMN_DIMENSION column,CELL_TYPE cell) {
		
	}

	@Override
	public Boolean rowDataEquals(ROW_DATA data1, ROW_DATA data2) {
		return null;
	}

	@Override
	public void fields(List<Field> fields) {
		
	}

	@Override
	public Boolean ignore(Field field) {
		return null;
	}
	
	@Override
	public void columns(List<COLUMN_DIMENSION> columns) {
		
	}

	
	@Override
	public void rowEditInitiated(ROW_DIMENSION row) {
		
	}

	
	@Override
	public void rowEditApplied(ROW_DIMENSION row) {
		
	}

	
	@Override
	public void rowEditCanceled(ROW_DIMENSION row) {
		
	}

	@Override
	public void openRow(ROW_DIMENSION row) {
		
	}
	
	@Override
	public void cellsAdded(ROW_DIMENSION row) {
		
	}

	@Override
	public Collection<ROW_DATA> fetchData(Integer first, Integer pageSize,
			String sortField, Boolean ascendingOrder,
			Map<String, Object> filters,String globalFilter) {
		return null;
	}
	
	@Override
	public Long count(String filter) {
		return null;
	}

	@Override
	public void addColumnFromDataClass(Class<?> aClass, Collection<Field> fields) {
		
	}

	@Override
	public Boolean excludeFromCount(ROW_DIMENSION row) {
		return null;
	}

	@Override
	public ROW_DIMENSION rowOf(ROW_DATA data) {
		return null;
	}

}
