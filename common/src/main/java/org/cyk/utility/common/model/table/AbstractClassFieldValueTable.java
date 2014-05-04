package org.cyk.utility.common.model.table;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.common.annotation.UIField;

public abstract class AbstractClassFieldValueTable<CLASS,ROW extends DefaultTableRow<CLASS>,COLUMN extends DefaultTableColumn,CELL extends DefaultCell> extends 
	AbstractDataTable<ROW,COLUMN,CLASS,String,DefaultCell,String> {

	private static final long serialVersionUID = -6350569403060865536L;
	
	protected Class<? extends ROW> rowClass;
	protected Class<? extends COLUMN> columnClass;
	protected Class<? extends CELL> cellClass;
	
	public void build(Class<CLASS> rowDataClass,Class<? extends ROW> rowClass,Class<? extends COLUMN> columnClass,Class<? extends CELL> cellClass) {
		this.rowClass = rowClass; 
		this.columnClass = columnClass;
		this.cellClass = cellClass;
		//rowClass = (Class<ROW>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		//columnClass = (Class<COLUMN>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
		
		try {
			Class<CLASS> clazz = (Class<CLASS>) rowDataClass;
			Collection<Field> fields = commonUtils.getAllFields(clazz);
			for(Field field : fields)
				if(showable(field)){
					COLUMN column = columnClass.newInstance();
					column.setTitle(field.getName());
					column.setFieldName(field.getName());
					addColumn(column);
				}		
		} catch (Exception e) {
			new RuntimeException(e);
		}
	}
	
	public boolean addRow(Collection<CLASS> datas) {
		for(CLASS data : datas)
			addRow(data);
		return true;
	}
	
	@Override
	public boolean addRow(CLASS aRowData) {
		try {
			ROW row = rowClass.newInstance();
			row.setData(aRowData);
			row.setTitle(aRowData.toString());
			if(addRow(row)){
				for(COLUMN column : columns){
					Object o = FieldUtils.readField(aRowData, column.getFieldName(),true);
					CELL cell = cellClass.newInstance();
					cell.setValue(o==null?nullValue():valueOf(o));
					addCell(row,column,cell);
				}
				return true;
			}
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return false;
	}
	
	protected Boolean showable(Field field){
		UIField formField = field.getAnnotation(UIField.class);
		return !Modifier.isStatic(field.getModifiers()) && formField!=null && !formField.tableColumnIgnore();
	}
	
	protected String nullValue(){
		return "---";
	}
	
	protected String valueOf(Object object){
		return object.toString();
	}

}
