package org.cyk.utility.common.model.table;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface TableListener< ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>,
COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> {

	COLUMN_DIMENSION createColumn();
	
	ROW_DIMENSION createRow();
	
	CELL_TYPE createCell();
	
	void columnAdded(COLUMN_DIMENSION column);
	
	void fields(List<Field> fields);
	
	void columns(List<COLUMN_DIMENSION> columns);
	
	Boolean ignore(Field field);
	
	void rowAdded(ROW_DIMENSION row);
	
	void openRow(ROW_DIMENSION row);
	
	void updateRow(ROW_DIMENSION row,ROW_DATA data);
	
	void rowUpdated(ROW_DIMENSION row);
	
	void rowRemoved(ROW_DIMENSION row);
	
	CELL_VALUE cellValue(ROW_DIMENSION row,COLUMN_DIMENSION column);
	
	void cellAdded(ROW_DIMENSION row,COLUMN_DIMENSION column,CELL_TYPE cell);
	
	void cellsAdded(ROW_DIMENSION row);
	
	Boolean rowDataEquals(ROW_DATA data1,ROW_DATA data2);
	
	void rowEditInitiated(ROW_DIMENSION row);
	
	void rowEditApplied(ROW_DIMENSION row);
	
	void rowEditCanceled(ROW_DIMENSION row);
	
	Collection<ROW_DATA> fetchData(Integer first, Integer pageSize,String sortField, Boolean ascendingOrder,Map<String, Object> filters,String globalFilter);
	
	Long count(String filter);
	
	void addColumnFromDataClass(Class<?> aClass,Collection<Field> fields);
	
	Boolean excludeFromCount(ROW_DIMENSION row);
	
	Boolean isSummary(ROW_DATA data);
	
	//Collection<Class<? extends Annotation>> annotationClasses();
}
