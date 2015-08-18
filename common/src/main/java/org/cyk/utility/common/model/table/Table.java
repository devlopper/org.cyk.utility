package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

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
	@Getter @Setter protected Boolean ignoreStaticField = Boolean.TRUE;
	
	@Getter protected Collection<TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE>> tableListeners = new ArrayList<>();
	
	private Boolean __building__ = Boolean.FALSE;
	private List<Field> fields = new ArrayList<>();
	private List<ROW_DATA> datas = new ArrayList<>();
	@Getter @Setter private Integer currentRowIndex = 0;
	
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
	
	public void build(){
		__building__ = Boolean.TRUE;
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.fields(fields);
		for(Field field : fields){
			Boolean ignored = Boolean.FALSE;
			for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
				Boolean v = listener.ignore(field);
				if(v!=null)
					ignored = v;
			}
			if(Boolean.FALSE.equals(ignored))
				addColumn(field);
		}
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.columns(columns);
		addRows(datas);
	}
	
	private void addColumn(COLUMN_DIMENSION column) {
		column.setIndex((long) columns.size());
		if(columns.add(column)){
			for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
				listener.columnAdded(column);
		}
	}
	
	public void addColumn(Field field) {
		if(!Boolean.TRUE.equals(__building__)){
			Boolean ignore = null;
			if(Modifier.isStatic(field.getModifiers()) && ignoreStaticField!=null && Boolean.TRUE.equals(ignoreStaticField) )
				return;
			for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
				Boolean r = listener.ignore(field);
				if(r!=null)
					ignore = r;
			}
			if(!Boolean.TRUE.equals(ignore))
				fields.add(field);
			return;
		}
		COLUMN_DIMENSION column = createColumn();
		column.setField(field);
		column.setTitle(field.getName());
		addColumn(column);
		return;
	}
	
	public void addColumn(String fieldName) {
		addColumn(commonUtils.getFieldFromClass(rowDataClass, fieldName));
	}

	public void addColumnFromDataClass(){
		Collection<Field> fields = new ArrayList<>();
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.addColumnFromDataClass(rowDataClass,fields);
		
		for(Field field :  fields /*commonUtils.getAllFields(rowDataClass)*/)
			addColumn(field);
			
	}
	
	private Boolean addRow(ROW_DIMENSION row) {
		row.setIndex((long) rows.size());
		if(Boolean.TRUE.equals(excludeFromCount(row))){
			row.setUiIndex(null);
		}else{
			for(ROW_DIMENSION r : rows)
				if(r.getUiIndex()!=null){
					row.setUiIndex(r.getUiIndex());
				}
			//row.setIndex((byte) rows.size());
			row.setUiIndex(row.getUiIndex()==null?0:row.getUiIndex()+1);
		}
		
		if(rows.add(row)){
			for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
				listener.rowAdded(row);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
		
	@SuppressWarnings("unchecked")
	public void addRow(ROW_DATA aRowData){
		if(aRowData==null)
			return;
		if(!Boolean.TRUE.equals(__building__)){
			datas.add(aRowData);
			return;
		}
		ROW_DIMENSION row = createRow();					
		row.setData(aRowData);
		row.setTitle(aRowData.toString());
		if(addRow(row)){
			for(COLUMN_DIMENSION column : columns){	
				CELL_TYPE cell = createCell();
				Object o = null;
				//o = commonUtils.readField(aRowData, column.getField(),!column.getField().getDeclaringClass().isAssignableFrom(rowDataClass),Boolean.FALSE);				
				for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
					Object r = listener.cellValue(row, column);
					if(r!=null)
						o = r;
				}	
				cell.setValue((CELL_VALUE) (o==null?nullValue:valueOf(o)));
				
				if(row.getCells().add(cell))
					for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
						listener.cellAdded(row, column, cell);
			}
			for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
				listener.cellsAdded(row);
		}
	}
	
	public void openRow(ROW_DIMENSION row) {
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.openRow(row);
	}
	
	@SuppressWarnings("unchecked")
	public void updateRow(ROW_DIMENSION row,ROW_DATA aRowData){
		int i=0;
		for(CELL_TYPE cell : row.getCells()){
			Object o = commonUtils.readField(aRowData, columns.get(i++).getField(), Boolean.FALSE);
			cell.setValue((CELL_VALUE) (o==null?nullValue:valueOf(o)));
		}
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.updateRow(row, aRowData);
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
	
	public Boolean excludeFromCount(ROW_DIMENSION row){
		Boolean value = null;
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
			Boolean v = listener.excludeFromCount(row);
			if(v!=null)
				value = v;
		}
		return Boolean.TRUE.equals(value==null?isSummary(row.getData()):value);
	}
	
	public Boolean isSummary(ROW_DATA data) {
		Boolean value = null;
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
			Boolean v = listener.isSummary(data);
			if(v!=null)
				value = v;
		}
		return value;
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
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.rowEditInitiated(row);
	}
	
	public void rowEditApplied(ROW_DIMENSION row){
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.rowEditApplied(row);
	}
	
	public void rowEditCanceled(ROW_DIMENSION row){
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.rowEditCanceled(row);
	}
	
	public void fetchData(Integer first, Integer pageSize,String sortField, Boolean ascendingOrder,Map<String, Object> filters,String globalFilter){
		clear();
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
			Collection<ROW_DATA> collection = listener.fetchData(first, pageSize, sortField, ascendingOrder, filters,globalFilter);
			if(collection!=null)
				addRows(collection);
		}
	}
	
	public Long count(String filter){
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
			Long count = listener.count(filter);
			if(count!=null)
				return count;
		}
		return 0l;
	}
	
	protected Boolean equals(ROW_DATA data1,ROW_DATA data2){
		Boolean eq = null;
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
			Boolean r = listener.rowDataEquals(data1, data2);
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
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
			COLUMN_DIMENSION r = listener.createColumn();
			if(r!=null)
				column = r;
		}
		if(column==null)
			column = newInstance(columnClass);
		return column;
	}
	
	private ROW_DIMENSION createRow(){
		ROW_DIMENSION row = null;
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
			ROW_DIMENSION r = listener.createRow();
			if(r!=null)
				row = r;
		}
		if(row==null)
			row = newInstance(rowClass);	
		return row;
	}
	
	private CELL_TYPE createCell(){
		CELL_TYPE cell = null;
		for(TableListener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners){
			CELL_TYPE r = listener.createCell();
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
	
}
