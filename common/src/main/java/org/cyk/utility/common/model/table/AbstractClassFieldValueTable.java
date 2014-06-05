package org.cyk.utility.common.model.table;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.common.annotation.UIField;

@Log
public abstract class AbstractClassFieldValueTable<CLASS,ROW extends DefaultTableRow<CLASS>,COLUMN extends DefaultTableColumn,CELL extends DefaultCell> extends 
	AbstractDataTable<ROW,COLUMN,CLASS,String,DefaultCell,String> {

	private static final long serialVersionUID = -6350569403060865536L;
	
	protected Class<? extends ROW> rowClass;
	protected Class<? extends CLASS> rowDataClass;
	protected Class<? extends COLUMN> columnClass;
	protected Class<? extends CELL> cellClass;
	protected String nullValue;
	
	public void build(Class<CLASS> rowDataClass,Class<? extends ROW> rowClass,Class<? extends COLUMN> columnClass,Class<? extends CELL> cellClass) {
		this.rowClass = rowClass; 
		this.columnClass = columnClass;
		this.cellClass = cellClass;
		this.rowDataClass=rowDataClass;
		//rowClass = (Class<ROW>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		//columnClass = (Class<COLUMN>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
		
		try {
			Collection<Field> fields = commonUtils.getAllFields(rowDataClass);
			for(Field field : fields)
				if(showable(field,rowDataClass)){
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
					cell.setValue(o==null?nullValue:valueOf(o));
					addCell(row,column,cell);
				}
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}
	
	@Override
	public void updateRow(ROW row,CLASS aRowData) {
		try {
			row.setData(aRowData);
			if(addRow(row)){
				for(COLUMN column : columns){
					Object o = FieldUtils.readField(aRowData, column.getFieldName(),true);
					row.getCells().get(column.getIndex()).setValue(o==null?nullValue:valueOf(o));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Boolean equals(CLASS anObject1,CLASS anObject2){
		return anObject1.equals(anObject2);
	}
	
	public Integer rowIndex(CLASS anObject){
		for(int i=0;i<rows.size();i++)
			if(equals(rows.get(i).getData(), anObject))
				return i;
		return null;
	}
	
	public void deleteRow(Integer index){
		rows.remove(index.intValue());	
		for(int i=index;i<rows.size();i++)
			rows.get(i).setIndex((byte) (rows.get(i).getIndex()-1));
	}
	
	public void deleteRow(CLASS anObject){
		Integer index = rowIndex(anObject);
		if(index==null)
			log.warning("Cannot delete object because no row has been found. <<"+anObject+">>");
		else
			deleteRow(index);
	}
	
	protected Boolean showable(Field field,Class<CLASS> clazz){
		if(Modifier.isStatic(field.getModifiers()))
			return false;
		UIField formField = uiField(field,clazz);
		return formField!=null && !formField.tableColumnIgnore();
	}
	
	protected UIField uiField(Field field,Class<CLASS> clazz){
		return field.getAnnotation(UIField.class);
	}
	
	protected String valueOf(Object object){
		return object.toString();
	}

}
