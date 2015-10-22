package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.model.table.Dimension.DimensionType;

public class Table<
	ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>,
	COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
	ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 5164167344286124195L;
 
	@Getter protected List<ROW_DIMENSION> rows = new ArrayList<ROW_DIMENSION>();
	@Getter protected List<COLUMN_DIMENSION> columns = new ArrayList<>();
	@Getter @Setter protected CellAddStrategy cellAddStrategy = CellAddStrategy.ROW_ONLY;
	
	protected Class<ROW_DIMENSION> rowClass;
	@Getter @Setter protected Class<ROW_DATA> rowDataClass;
	protected Class<COLUMN_DIMENSION> columnClass;
	protected Class<CELL_TYPE> cellClass;
	
	@Getter @Setter protected String nullValue;
	@Getter @Setter protected Boolean ignoreStaticField = Boolean.TRUE,useRowDataClassAttributeAsColumn=Boolean.TRUE;
	
	@Getter protected Collection<TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE>> tableListeners = new ArrayList<>();
	@Getter protected Collection<RowListener<ROW_DIMENSION, ROW_DATA, CELL_TYPE, CELL_VALUE>> rowListeners = new ArrayList<>();
	@Getter protected Collection<ColumnListener<COLUMN_DIMENSION, COLUMN_DATA, CELL_TYPE, CELL_VALUE>> columnListeners = new ArrayList<>();
	@Getter protected Collection<CellListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE>> cellListeners = new ArrayList<>();
	
	private Boolean __building__ = Boolean.FALSE;
	private List<Field> fields = new ArrayList<>();
	private List<ROW_DATA> datas = new ArrayList<>();
	@Getter @Setter private Integer currentRowIndex = 0;
	@Setter private Integer numberOfNullUiIndex;
	
	/*
	@SuppressWarnings("unchecked")
	public Table() {
		rowClass = (Class<ROW_DIMENSION>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	*/
	
	@SuppressWarnings("unchecked")
	public Table(Class<? extends ROW_DIMENSION> rowClass, Class<ROW_DATA> rowDataClass,
			Class<COLUMN_DIMENSION> columnClass, Class<CELL_TYPE> cellClass) {
		super();
		this.rowClass = (Class<ROW_DIMENSION>) rowClass;
		this.rowDataClass = rowDataClass;
		this.columnClass = columnClass;
		this.cellClass = cellClass;
	}	
	
	/**
	 * Create columns , rows cell and cell's value
	 */
	public void build(){
		logTrace("Table build starts RowDataClass={}",rowDataClass==null?null:rowDataClass.getSimpleName());
		__building__ = Boolean.TRUE;
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.beforeBuild();
		
		// Build logic
		if(Boolean.TRUE.equals(useRowDataClassAttributeAsColumn))
			useRowDataClassAttributeAsColumn();
		
		for(ColumnListener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.sort(fields);
		
		logTrace("Sorted attributes {}", fields);
		
		addColumns(fields);
		addRows(datas);
		
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.afterBuild();
		
		logTrace("Table build ends");
	}
	
	public void addColumn(COLUMN_DIMENSION column) {
		column.setIndex((long) columns.size());
		for(ColumnListener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.add(column);
		if(columns.add(column)){
			for(ColumnListener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
				listener.added(column);
		}
		logTrace("Column added Field={} Title={}", column.getField()==null?null:column.getField().getName(),column.getTitle());
	}
	
	public void addColumns(Collection<Field> fields){
		for(Field field : fields){
			addColumn(field);
		}
	}
	
	public void addColumn(Field field) {
		if(!Boolean.TRUE.equals(__building__)){
			Boolean isColumn = isColumn(field);
			logTrace("Plan build for field {} ? {}",field.getName(), isColumn);
			if(Boolean.TRUE.equals(isColumn)){
				fields.add(field);
			}else{
				
			}
			return;
		}
		if(!Boolean.TRUE.equals(isColumn(field))){
			logTrace("Building column <<{}>> has been skipped",field.getName());
			return;
		}
		COLUMN_DIMENSION column = createColumn();
		column.setField(field);
		column.setTitle(field.getName());
		for(ColumnListener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.created(column);
		addColumn(column);
	}
	
	public void addColumn(String fieldName) {
		addColumn(commonUtils.getFieldFromClass(rowDataClass, fieldName));
	}
	
	/*
	public void addColumnTitled(String title) {
		if(!Boolean.TRUE.equals(__building__)){
			Boolean isColumn = isColumn(field);
			logTrace("Plan build for field {} ? {}",field.getName(), isColumn);
			if(Boolean.TRUE.equals(isColumn)){
				fields.add(field);
			}else{
				
			}
			return;
		}
		if(!Boolean.TRUE.equals(isColumn(field))){
			logTrace("Building column <<{}>> has been skipped",field.getName());
			return;
		}
		COLUMN_DIMENSION column = createColumn();
		column.setField(field);
		column.setTitle(field.getName());
		for(ColumnListener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.created(column);
		addColumn(column);
	}*/

	public Boolean isColumn(Field field){
		Boolean result = null;
		if(Modifier.isStatic(field.getModifiers()) && ignoreStaticField!=null && Boolean.TRUE.equals(ignoreStaticField) )
			return Boolean.FALSE;
		for(ColumnListener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners){
			Boolean r = listener.isColumn(field);
			if(r!=null)
				result = r;
		}
		return result;
	}
	
	private void useRowDataClassAttributeAsColumn(){
		List<Field> fields = new ArrayList<>();
		for(ColumnListener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.populateFromDataClass(rowDataClass,fields);
		
		Set<Field> nonDuplicateFields = new LinkedHashSet<>();
		for(Field field : fields)
			nonDuplicateFields.add(field);
		addColumns(nonDuplicateFields);
		logTrace("Data class attribute as columns {}",nonDuplicateFields);	
	}
	
	private Boolean addRow(ROW_DIMENSION row) {
		row.setIndex((long) rows.size());
		if(Boolean.TRUE.equals(isCountable(row))){
			for(ROW_DIMENSION r : rows)
				if(r.getUiIndex()!=null){
					row.setUiIndex(r.getUiIndex());
				}
			row.setUiIndex(row.getUiIndex()==null?0:row.getUiIndex()+1);
		}else{
			row.setUiIndex(null);
		}
		
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.add(row);
		if(rows.add(row)){
			for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
				listener.added(row);
			logTrace("Row added {}",row);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
		
	@SuppressWarnings("unchecked")
	public ROW_DIMENSION addRow(ROW_DATA aRowData){
		if(aRowData==null)
			return null;
		if(!Boolean.TRUE.equals(__building__)){
			datas.add(aRowData);
			return null;
		}
		ROW_DIMENSION row = createRow();					
		row.setData(aRowData);
		row.setTitle(aRowData.toString());
		// TODO factor that in a method
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean v = listener.isOpenable(row);
			if(v!=null)
				row.setOpenable(v);
		}
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean v = listener.isUpdatable(row);
			if(v!=null)
				row.setUpdatable(v);
		}
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean v = listener.isDeletable(row);
			if(v!=null)
				row.setDeletable(v);
		}
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean v = listener.isCountable(row);
			if(v!=null)
				row.setCountable(v);
		}
		
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.created(row);
		if(addRow(row)){
			for(CellListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners)
				listener.add(row);
			for(COLUMN_DIMENSION column : columns){	
				CELL_TYPE cell = createCell();
				Object o = null;
				//o = commonUtils.readField(aRowData, column.getField(),!column.getField().getDeclaringClass().isAssignableFrom(rowDataClass),Boolean.FALSE);				
				for(CellListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners){
					Object r = listener.getValue(row, column);
					if(r!=null)
						o = r;
				}	
				cell.setValue((CELL_VALUE) (o==null?nullValue:valueOf(o)));
				
				for(CellListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners)
					listener.add(row, column, cell);
				if(row.getCells().add(cell))
					for(CellListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners)
						listener.added(row, column, cell);
			}
			for(CellListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners)
				listener.added(row);
		}
		return row;
	}
	
	public void openRow(ROW_DIMENSION row) {
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.open(row);
	}
	
	@SuppressWarnings("unchecked")
	public void updateRow(ROW_DIMENSION row,ROW_DATA aRowData){
		int i=0;
		for(CELL_TYPE cell : row.getCells()){
			Object o = commonUtils.readField(aRowData, columns.get(i++).getField(), Boolean.FALSE);
			cell.setValue((CELL_VALUE) (o==null?nullValue:valueOf(o)));
		}
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.update(row, aRowData);
	}
	
	public void addRows(Collection<ROW_DATA> theRowData){
		if(theRowData==null)
			return;
		for(ROW_DATA data : theRowData)
			addRow(data);
	}
	
	public void deleteRowAt(Integer index){
		rows.remove(index.intValue());	
		for(int i=index;i<rows.size();i++)
			rows.get(i).setIndex((long) (rows.get(i).getIndex()-1));
	}
	
	public Integer rowIndex(ROW_DATA anObject){
		for(int i=0;i<rows.size();i++){
			if(equals(rows.get(i).getData(), anObject)){
				return i;
			}
		}
		return null;
	}
	
	public Boolean isCountable(ROW_DIMENSION row){
		Boolean value = null;
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean v = listener.countable(row);
			if(v!=null)
				value = v;
		}
		DimensionType dimensionType = row.getType();
		dimensionType = dimensionType == null ? DimensionType.DETAIL:dimensionType;
		return value == null ? row.getIsDetail() : (value==null || Boolean.TRUE.equals(value));
	}
	
	public ROW_DIMENSION rowOf(ROW_DATA data) {
		for(int i=0;i<rows.size();i++){
			if(equals(rows.get(i).getData(), data)){
				return rows.get(i);
			}
		}
		return null;
	}
	
	public CELL_TYPE cell(ROW_DIMENSION row,COLUMN_DIMENSION column){
		if(row==null || column==null || row.getIndex()>=rows.size() || column.getIndex()>=columns.size()){
			return null;
		}
		
		return rows.get(row.getIndex().intValue()).getCells().get(column.getIndex().intValue());
	}
	
	public void clear(){
		if(rows==null)
			return;
		int l = rows.size();
		for(int i=0;i<l;i++)
			deleteRowAt(0);
	}
	
	public void rowEditInitiated(ROW_DIMENSION row){
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.editInitiated(row);
	}
	
	public void rowEditApplied(ROW_DIMENSION row){
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.editApplied(row);
	}
	
	public void rowEditCanceled(ROW_DIMENSION row){
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.editCanceled(row);
	}
	
	public void load(DataReadConfiguration configuration){
		clear();
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Collection<ROW_DATA> collection = listener.load(configuration);
			if(collection!=null)
				addRows(collection);
		}
	}
	
	public Long count(DataReadConfiguration configuration){
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Long count = listener.count(configuration);
			if(count!=null)
				return count;
		}
		return 0l;
	}
	
	protected Boolean equals(ROW_DATA data1,ROW_DATA data2){
		Boolean eq = null;
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean r = listener.equals(data1, data2);
			if(r!=null)
				eq = r;
		}
		return eq!=null && Boolean.TRUE.equals(eq);
	}
	
	@SuppressWarnings("unchecked")
	protected CELL_VALUE valueOf(Object object){
		return (CELL_VALUE) object.toString();
	}
	
	/* */
	
	private COLUMN_DIMENSION createColumn(){
		COLUMN_DIMENSION column = null;
		for(ColumnListener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners){
			COLUMN_DIMENSION r = listener.create();
			if(r!=null)
				column = r;
		}
		if(column==null)
			column = newInstance(columnClass);
		return column;
	}
	
	private ROW_DIMENSION createRow(){
		ROW_DIMENSION row = null;
		for(RowListener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			ROW_DIMENSION r = listener.create();
			if(r!=null)
				row = r;
		}
		if(row==null)
			row = newInstance(rowClass);	
		return row;
	}
	
	private CELL_TYPE createCell(){
		CELL_TYPE cell = null;
		for(CellListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners){
			CELL_TYPE r = listener.create();
			if(r!=null)
				cell = r;
		}
		if(cell==null)
			cell = newInstance(cellClass);
		return cell;
	}

	public COLUMN_DIMENSION getColumn(String fieldName){
		for(COLUMN_DIMENSION column : columns)
			if(column.getField().getName().equals(fieldName))
				return column;
		return null;
	}
	
	public Integer getNumberOfNullUiIndex(){
		if(numberOfNullUiIndex==null){
			numberOfNullUiIndex = 0;
			for(ROW_DIMENSION row : rows)
				if(row.getUiIndex()==null)
					numberOfNullUiIndex++;
				
		}
		return numberOfNullUiIndex;
	}
	
	
}
