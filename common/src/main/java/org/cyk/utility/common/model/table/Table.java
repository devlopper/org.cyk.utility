package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.model.table.Dimension.DimensionType;

import lombok.Getter;
import lombok.Setter;

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
	
	@Getter protected Collection<Table.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE>> tableListeners = new ArrayList<>();
	@Getter protected Collection<Row.Listener<ROW_DIMENSION, ROW_DATA, CELL_TYPE, CELL_VALUE>> rowListeners = new ArrayList<>();
	@Getter protected Collection<Column.Listener<COLUMN_DIMENSION, COLUMN_DATA, CELL_TYPE, CELL_VALUE>> columnListeners = new ArrayList<>();
	@Getter protected Collection<Cell.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE>> cellListeners = new ArrayList<>();
	
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
		LoggingHelper.Message.Builder loggingMessageBuilder = new LoggingHelper.Message.Builder.Adapter.Default("build table");
		addLoggingMessageBuilderParameters(loggingMessageBuilder, "Row data class",rowDataClass==null?null:rowDataClass.getSimpleName());
		__building__ = Boolean.TRUE;
		for(Table.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.beforeBuild();
		
		// Build logic
		if(Boolean.TRUE.equals(useRowDataClassAttributeAsColumn)){
			fields.clear();
			useRowDataClassAttributeAsColumn();
		}
		
		if(!Boolean.TRUE.equals(listenerUtils.getBoolean(columnListeners, new ListenerUtils.BooleanMethod<Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE>>() {
			@Override
			public Boolean execute(Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener) {
				return listener.getSortable();
			}
		}))){
			final List<String> list = (List<String>) listenerUtils.getCollection(columnListeners, new ListenerUtils.CollectionMethod<Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE>, String>(){
				@Override
				public Collection<String> execute(Column.Listener<COLUMN_DIMENSION, COLUMN_DATA, CELL_TYPE, CELL_VALUE> listener) {
					return listener.getExpectedFieldNames();
				}
			});
			if(list!=null)
				Collections.sort(fields, new Comparator<Field>() {
					@Override
					public int compare(Field field1, Field field2) {
						return list.indexOf(field1.getName()) - list.indexOf(field2.getName());
					}
				});
		}
		
		for(Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.sort(fields);
		
		//logMessageBuilder.addParameters("Fields",commonUtils.getCollectionByMethodCall(String.class, fields, "getName"));
		
		addColumns();
		addRows(datas);
		
		for(Table.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : tableListeners)
			listener.afterBuild();
		
		logTrace(loggingMessageBuilder);
	}
	
	public void addColumn(COLUMN_DIMENSION column) {
		column.setIndex((long) columns.size());
		for(Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.add(column);
		if(columns.add(column)){
			for(Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
				listener.added(column);
		}
		//logTrace("Column added Field={} Title={}", column.getField()==null?null:column.getField().getName(),column.getTitle());
	}
	
	public void addColumns(Collection<Field> fields){
		for(Field field : fields){
			addColumn(field);
		}
	}
	
	public void addColumns(){
		addColumns(fields);
	}
	
	public void addColumn(Field field) {
		if(!Boolean.TRUE.equals(__building__)){
			Boolean isColumn = isColumn(field);
			//logTrace("Plan build for field {} ? {}",field.getName(), isColumn);
			if(Boolean.TRUE.equals(isColumn)){
				;//fields.add(field);
			}else{
				
			}
			return;
		}
		if(!Boolean.TRUE.equals(isColumn(field))){
			//logTrace("Building column <<{}>> has been skipped",field.getName());
			return;
		}
		COLUMN_DIMENSION column = createColumn();
		column.setField(field);
		column.setTitle(field.getName());
		for(Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.created(column);
		addColumn(column);
	}
	
	public void addColumn(String fieldName) {
		Collection<Field> fields = FieldHelper.getInstance().get(rowDataClass, fieldName, StringHelper.Location.EXAT);
		if(fields.size()==1)
			addColumn(fields.iterator().next());// commonUtils.getFieldFromClass(rowDataClass, fieldName));
	}
	
	public void addColumnTitled(String title) {
		COLUMN_DIMENSION column = createColumn();
		column.setTitle(title);
		for(Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.created(column);
		addColumn(column);
	}

	public Boolean isColumn(Field field){
		Boolean result = null;
		if(Modifier.isStatic(field.getModifiers()) && ignoreStaticField!=null && Boolean.TRUE.equals(ignoreStaticField) )
			return Boolean.FALSE;
		for(Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners){
			Boolean r = listener.isColumn(field);
			if(r!=null)
				result = r;
		}
		return result;
	}
	
	private void useRowDataClassAttributeAsColumn(){
		List<Field> fields = new ArrayList<>();
		for(Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners)
			listener.populateFromDataClass(rowDataClass,fields);
		
		Set<Field> nonDuplicateFields = new LinkedHashSet<>();
		
		for(Field field : fields)
			if(isColumn(field))
				nonDuplicateFields.add(field);
		
		this.fields.addAll(nonDuplicateFields);
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
		
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.add(row);
		if(rows.add(row)){
			for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
				listener.added(row);
			//logTrace("Row added {}",row);
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
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean v = listener.isOpenable(row);
			if(v!=null)
				row.setOpenable(v);
		}
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean v = listener.isUpdatable(row);
			if(v!=null)
				row.setUpdatable(v);
		}
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean v = listener.isDeletable(row);
			if(v!=null)
				row.setDeletable(v);
		}
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Boolean v = listener.isCountable(row);
			if(v!=null)
				row.setCountable(v);
		}
		
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.created(row);
		if(addRow(row)){
			for(Cell.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners)
				listener.add(row);
			for(COLUMN_DIMENSION column : columns){	
				CELL_TYPE cell = createCell();
				Object o = null;
				//o = commonUtils.readField(aRowData, column.getField(),!column.getField().getDeclaringClass().isAssignableFrom(rowDataClass),Boolean.FALSE);				
				for(Cell.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners){
					Object r = listener.getValue(row, column);
					if(r!=null)
						o = r;
				}	
				cell.setValue((CELL_VALUE) (o==null?nullValue:valueOf(o)));
				
				for(Cell.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners)
					listener.add(row, column, cell);
				if(row.getCells().add(cell))
					for(Cell.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners)
						listener.added(row, column, cell);
			}
			for(Cell.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners)
				listener.added(row);
		}
		return row;
	}
	
	public void openRow(ROW_DIMENSION row) {
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.open(row);
	}
	
	@SuppressWarnings("unchecked")
	public void updateRow(ROW_DIMENSION row,ROW_DATA aRowData){
		int i=0;
		for(CELL_TYPE cell : row.getCells()){
			Object o = commonUtils.readField(aRowData, columns.get(i++).getField(), Boolean.FALSE);
			cell.setValue((CELL_VALUE) (o==null?nullValue:valueOf(o)));
		}
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
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
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
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
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.editInitiated(row);
	}
	
	public void rowEditApplied(ROW_DIMENSION row){
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.editApplied(row);
	}
	
	public void rowEditCanceled(ROW_DIMENSION row){
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners)
			listener.editCanceled(row);
	}
	
	public void load(DataReadConfiguration configuration){
		clear();
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Collection<ROW_DATA> collection = listener.load(configuration);
			if(collection!=null)
				addRows(collection);
		}
	}
	
	public Long count(DataReadConfiguration configuration){
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
			Long count = listener.count(configuration);
			if(count!=null)
				return count;
		}
		return 0l;
	}
	
	protected Boolean equals(ROW_DATA data1,ROW_DATA data2){
		Boolean eq = null;
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
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
		for(Column.Listener<COLUMN_DIMENSION,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : columnListeners){
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
		for(Row.Listener<ROW_DIMENSION,ROW_DATA,CELL_TYPE,CELL_VALUE> listener : rowListeners){
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
		for(Cell.Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> listener : cellListeners){
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
	
	/**/
	
	public interface Listener< ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>, COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
	ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> {

		Table<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> getTable();
		
		void beforeBuild();
		
		void afterBuild();
		
		void sort(List<Field> fields);
		
		/**/
		
		public static class Adapter< ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>,
		COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
		ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> implements Listener<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE>,Serializable {

			private static final long serialVersionUID = 1L;

			@Override
			public Table<ROW_DIMENSION, COLUMN_DIMENSION, ROW_DATA, COLUMN_DATA, CELL_TYPE, CELL_VALUE> getTable() {
				return null;
			}

			@Override
			public void beforeBuild() {}

			@Override
			public void afterBuild() {}

			@Override
			public void sort(List<Field> fields) {}

			/**/
			
			public static class Default< ROW_DIMENSION extends Row<ROW_DATA, CELL_TYPE, CELL_VALUE>,COLUMN_DIMENSION extends Column<COLUMN_DATA, CELL_TYPE, CELL_VALUE>,
			ROW_DATA,COLUMN_DATA,CELL_TYPE extends Cell<CELL_VALUE>,CELL_VALUE> extends Listener.Adapter<ROW_DIMENSION,COLUMN_DIMENSION,ROW_DATA,COLUMN_DATA,CELL_TYPE,CELL_VALUE> implements Serializable {
				
				private static final long serialVersionUID = 1L;
				
			}
			
		}
	}

	/**/
	
	public static class Default<DATA> extends Table<DefaultRow<DATA>, DefaultColumn, DATA, String, DefaultCell, String> {

		//TODO DefaultRow.class throw inconvertible type at compile time
		@SuppressWarnings("unchecked")
		public Default(Class<DATA> rowDataClass) {
			super((Class<? extends DefaultRow<DATA>>) /*DefaultRow.class*/null, rowDataClass, DefaultColumn.class, DefaultCell.class);
			if(rowDataClass==null){
				
			}else{
				try {
					rowClass = (Class<DefaultRow<DATA>>) Class.forName(DefaultRow.class.getName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
		}

		private static final long serialVersionUID = 581883275700805955L;

		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			for(DefaultColumn column : columns)
				s.append(String.format(DefaultRow.FORMAT, column.getTitle()));
			
			s.append("\r\n"+StringUtils.repeat('#', s.length())+"\r\n");
			
			for(DefaultRow<DATA> row : rows)
				s.append(row.toString()+"\r\n");
			return s.toString();
		}
		
	}

}
